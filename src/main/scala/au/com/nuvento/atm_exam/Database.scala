package au.com.nuvento.atm_exam

import au.com.nuvento.atm_exam.User
import au.com.nuvento.atm_exam.Account

import java.io.{File, FileWriter}

class Database (val userPath: String, val accountPath: String){
  private def readTextFile(path: String): List[String] = {
    val bufferedSource = scala.io.Source.fromFile(path)
    val listOfLines = bufferedSource.getLines().toList
    bufferedSource.close()
    listOfLines
  }

  private def createUserList(userText: List[String]): List[User] = {
    if (userText.length <= 0) List()
    else {
      val userParameters = userText.head.split(",")
      val newUser = User(userParameters(0),userParameters(1),userParameters(2),userParameters(3))
      newUser :: createUserList(userText.tail)
    }
  }

  private def createAccountList(accountText: List[String]): List[Account] = {
    if (accountText.length <= 0) List()
    else {
      val accountParameters = accountText.head.split("\\|\\|\\|")
      val newAccount = Account(accountParameters(0), accountParameters(1).toInt, accountParameters(2), accountParameters(3).toFloat)
      newAccount :: createAccountList(accountText.tail)
    }
  }
  private val listOfUserLines = readTextFile(userPath)
  val userList = createUserList(listOfUserLines.tail)

  private val listOfAccountLines = readTextFile(accountPath)
  val accountList = createAccountList(listOfAccountLines.tail)

  def quit() = {
    println("Account balance summary:")
    for (account <- accountList) println(s"${account.accountNum}: $$${account.balance}")
    val fileWriter = new FileWriter(new File(accountPath))
    fileWriter.write("AccountOwnerID|||AccountNumber|||AccountType|||OpeningBalance\n")
    for (account <- accountList){
      val accountLine = s"${account.ownerID}|||${account.accountNum}|||${account.accountType}|||${account.balance}"
      fileWriter.write(accountLine)
      if (accountList.indexOf(account) < accountList.length - 1) fileWriter.write("\n")
    }
    fileWriter.close()
  }
}
