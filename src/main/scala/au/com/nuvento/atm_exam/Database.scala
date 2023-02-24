package au.com.nuvento.atm_exam

import au.com.nuvento.atm_exam.User
import au.com.nuvento.atm_exam.Account

import java.io.{File, FileWriter}

class Database (val userPath: String, val accountPath: String){
  def readTextFile(path: String): List[String] = {
    val bufferedSource = scala.io.Source.fromFile(path)
    val listOfLines = bufferedSource.getLines().toList
    bufferedSource.close()
    listOfLines
  }

  def createUserList(userText: List[String]): List[User] = {
    if (userText.length <= 0) List()
    else {
      val userParameters = userText.head.split(",")
      val firstName = userParameters(0)
      val lastName = userParameters(1)
      val mobile = userParameters(2)
      val userID = userParameters(3)
      val newUser = User(firstName, lastName, mobile, userID)
      newUser :: createUserList(userText.tail)
    }
  }

  def createAccountList(accountText: List[String]): List[Account] = {
    if (accountText.length <= 0) List()
    else {
      val accountParameters = accountText.head.split("\\|\\|\\|")
      val ownerID = accountParameters(0)
      val accountNum = accountParameters(1).toInt
      val accountType = accountParameters(2)
      val balance = accountParameters(3).toFloat
      val newAccount = Account(ownerID, accountNum, accountType, balance)
      newAccount :: createAccountList(accountText.tail)
    }
  }
  private val userLines = readTextFile(userPath)
  private val userLinesNoHeaders = userLines.tail
  val userList = createUserList(userLinesNoHeaders)

  private val accountLines = readTextFile(accountPath)
  private val accountLinesNoHeaders = accountLines.tail
  val accountList = createAccountList(accountLinesNoHeaders)
  
  def getUserFromID(id: String): User = {
    val userIDList = userList.map(user => user.userID)
    if (!userIDList.contains(id)) null
    else {
      val userIndex = userIDList.indexOf(id)
      userList(userIndex)
    }
  }

  def getUserAccounts(id: String): List[Account] = {
    def getUserAccountsRecursive(iteratedAccountList: List[Account]): List[Account] = {
      if (iteratedAccountList.length < 1) List ()
      else if (iteratedAccountList.head.ownerID == id)
        iteratedAccountList.head :: getUserAccountsRecursive(iteratedAccountList.tail)
      else getUserAccountsRecursive (iteratedAccountList.tail)
    }
    getUserAccountsRecursive(accountList)
  }

  def quit() = {
    println("Account balance summary:")
    for (account <- accountList) println(s"${account.accountNum}: $$${"%.2f".format(account.balance)}")
    val fileWriter = new FileWriter(new File(accountPath))
    fileWriter.write("AccountOwnerID|||AccountNumber|||AccountType|||OpeningBalance\n")
    for (index <- accountList.indices){
      val account = accountList(index)
      val balanceRounded = "%.2f".format(account.balance)
      val accountLine = s"${account.ownerID}|||${account.accountNum}|||${account.accountType}|||${balanceRounded}"
      fileWriter.write(accountLine)
      if (index < accountList.length - 1) fileWriter.write("\n")
    }
    fileWriter.close()
  }
}
