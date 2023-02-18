package au.com.nuvento.atm_exam

import au.com.nuvento.atm_exam.User
import au.com.nuvento.atm_exam.Account

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

    val userList = List(
      User("John","Smith","0403715629".toLong, "001"),
      User("Leanne","Smith","0403185031".toLong,"002"),
      User("Kim","Kash","0404993021".toLong,"003"),
    )
    val accountList = List(
      Account("001", 9264945, "Cheque", 500.90),
      Account("001", 7814135, "Saving", 200.090),
      Account("002", 9676422, "Saving", 1200.00),
      Account("002", 7524155, "Cheque", 50.00),
      Account("003", 9042221, "Saving", 4000.20)
    )

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
            if (activeAccountNumber > userAccounts.length) println(s"Error: This account ($action) does not exist")
            else{
              val activeAccount = userAccounts(activeAccountNumber - 1)
              println("Please choose an amount of money to deposit.")
              println(s"Current balance: $$${activeAccount.balance}")
              val deposit = StdIn.readFloat()
              activeAccount.updateBalance(activeAccount.balance + deposit)
            }
          case '2' =>
            val userAccounts = getUserAccounts(id, accountList)
            println("Please choose an account to check its balance:")
            for (index <- 1 to userAccounts.length) println(s"${index} for ${userAccounts(index - 1).accountNum} (${userAccounts(index - 1).accountType})")
            val activeAccountNumber = StdIn.readInt()
            if (activeAccountNumber > userAccounts.length) println(s"Error: This account ($action) does not exist")
            else {
              val activeAccount = userAccounts(activeAccountNumber - 1)
              println("Please choose an amount of money to withdraw.")
              println(s"Current balance: $$${activeAccount.balance}")
              val withdraw = StdIn.readFloat()
              if (withdraw > activeAccount.balance) println(s"Error: Account does not have sufficient funds to withdraw requested amount ($$$withdraw)")
              else activeAccount.updateBalance(activeAccount.balance - withdraw)
            }
          case '3' =>
            val userAccounts = getUserAccounts(id, accountList)
            println("Please choose an account to check its balance:")
            for (index <- 1 to userAccounts.length) println(s"${index} for ${userAccounts(index-1).accountNum} (${userAccounts(index-1).accountType})")
            val activeAccountNumber = StdIn.readInt()
            if (activeAccountNumber > userAccounts.length) println(s"Error: This account ($action) does not exist")
            else{
              val activeAccount = userAccounts(activeAccountNumber - 1)
              println(s"Current balance of account ${activeAccount.accountNum}: $$${activeAccount.balance}")
            }
          case 'q' => quit = true
          case _ => println(s"Error: This action ($action) is not recognised")
        }
      }
    }

  }

}
