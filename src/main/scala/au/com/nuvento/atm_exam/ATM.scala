package au.com.nuvento.atm_exam

import au.com.nuvento.atm_exam.Database

import scala.io.StdIn
/**
 * @author $Hamzah_Badri
 */
object ATM {

  def getUserAccounts(id: String, accountList: List[Account]): List[Account] = {
    if (accountList.length < 1) List()
    else if (accountList.head.ownerID == id) accountList.head :: getUserAccounts(id, accountList.tail)
    else getUserAccounts(id, accountList.tail)
  }
  
  def main(args : Array[String]) = {

    val database = Database("data/UserInfo.txt", "data/OpeningAccountsData.txt")

    val userList = database.userList
    val accountList = database.accountList

    var quit = false
    while (!quit){
      println("Please enter your user ID")
      val id = StdIn.readLine()
      val userIDList = userList.map(user => user.userID)
      if (!userIDList.contains(id)) println(s"Error: User ID ($id) not recognised")
      else {
        val userIndex = userIDList.indexOf(id)
        val activeUser = userList(userIndex)
        println(s"Welcome, ${activeUser.firstName} ${activeUser.lastName}. Please choose the action you would like to perform:")
        println("1 for Deposit")
        println("2 for Withdraw")
        println("3 for Balance")
        println("q for Quit")
        val action = StdIn.readChar()
        action match {
          case '1' =>
            val userAccounts = getUserAccounts(id, accountList)
            println("Please choose an account to check its balance:")
            for (index <- 1 to userAccounts.length) println(s"${index} for ${userAccounts(index - 1).accountNum} (${userAccounts(index - 1).accountType})")
            val activeAccountNumber = StdIn.readInt()
            if (activeAccountNumber > userAccounts.length) println(s"Error: This account ($activeAccountNumber) does not exist")
            else{
              val activeAccount = userAccounts(activeAccountNumber - 1)
              println("Please choose an amount of money to deposit.")
              println(s"Current balance: $$${activeAccount.balance}")
              val deposit = StdIn.readFloat()
              activeAccount.updateBalance(activeAccount.balance + deposit)
              println(s"Transaction successful. Current balance: $$${activeAccount.balance}")
            }
          case '2' =>
            val userAccounts = getUserAccounts(id, accountList)
            println("Please choose an account to check its balance:")
            for (index <- 1 to userAccounts.length) println(s"${index} for ${userAccounts(index - 1).accountNum} (${userAccounts(index - 1).accountType})")
            val activeAccountNumber = StdIn.readInt()
            if (activeAccountNumber > userAccounts.length) println(s"Error: This account ($activeAccountNumber) does not exist")
            else {
              val activeAccount = userAccounts(activeAccountNumber - 1)
              println("Please choose an amount of money to withdraw.")
              println(s"Current balance: $$${activeAccount.balance}")
              val withdraw = StdIn.readFloat()
              if (withdraw > activeAccount.balance) println(s"Error: Account does not have sufficient funds to withdraw requested amount ($$$withdraw)")
              else {
                activeAccount.updateBalance(activeAccount.balance - withdraw)
                println(s"Transaction successful. Current balance: $$${activeAccount.balance}")
              }
            }
          case '3' =>
            val userAccounts = getUserAccounts(id, accountList)
            println("Please choose an account to check its balance:")
            for (index <- 1 to userAccounts.length) println(s"${index} for ${userAccounts(index-1).accountNum} (${userAccounts(index-1).accountType})")
            val activeAccountNumber = StdIn.readInt()
            if (activeAccountNumber > userAccounts.length) println(s"Error: This account ($activeAccountNumber) does not exist")
            else{
              val activeAccount = userAccounts(activeAccountNumber - 1)
              println(s"Current balance of account ${activeAccount.accountNum}: $$${activeAccount.balance}")
            }
          case 'q' => quit = true
          case _ => println(s"Error: This action ($action) is not recognised")
        }
      }
    }
    database.quit()

  }

}
