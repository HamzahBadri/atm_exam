package au.com.nuvento.atm_exam

import au.com.nuvento.atm_exam.Database
import com.typesafe.config.ConfigFactory

import scala.io.StdIn.*

/**
 * @author $Hamzah_Badri
 */

object ATM {

  def getUserPath(): String = {
    val config = ConfigFactory.load("application.conf").getConfig("au.com.nuvento.atm_exam")
    config.getString("userPath")
  }

  def getAccountPath(): String = {
    val config = ConfigFactory.load("application.conf").getConfig("au.com.nuvento.atm_exam")
    config.getString("accountPath")
  }

  def main(args : Array[String]) = {

    val userPath = getUserPath()
    val accountPath = getAccountPath()
    val database = Database(userPath,accountPath)

    def chooseAccount(id: String): Account = {
      val userAccounts = database.getUserAccounts(id)
      if (userAccounts.length <= 0) {
        println(s"Error: User $id has no accounts")
        return null
      }
      val userAccountsRange = 1 to userAccounts.length
      for (index <- userAccountsRange) {
        val account = userAccounts(index - 1)
        println(s"$index for ${account.accountNum} (${account.accountType})")
      }
      val activeAccountNumber = readInt()
      if (!userAccountsRange.contains(activeAccountNumber)) {
        println(s"Error: This account ($activeAccountNumber) does not exist")
        null
      } else userAccounts(activeAccountNumber - 1)
    }

    var quit = false
    while (!quit){
      try {
        println("Please enter your user ID")
        val activeUserID = readLine()
        val activeUser = database.getUserFromID(activeUserID)
        if (activeUser == null) println(s"Error: User ID ($activeUserID) not recognised")
        else {
          println(s"Welcome, ${activeUser.firstName} ${activeUser.lastName}. " +
            "Please choose the action you would like to perform:")
          println("1 for Deposit")
          println("2 for Withdraw")
          println("3 for Balance")
          println("q for Quit")
          val action = readChar()
          action match {
            case '1' =>
              println("Please choose an account to deposit into:")
              val activeAccount = chooseAccount(activeUserID)
              if (activeAccount != null) {
                println("Please choose an amount of money to deposit")
                activeAccount.displayBalance()
                val amount = readFloat()
                activeAccount.deposit(amount)
              }
            case '2' =>
              println("Please choose an account to withdraw from:")
              val activeAccount = chooseAccount(activeUserID)
              if (activeAccount != null) {
                println("Please choose an amount of money to withdraw")
                activeAccount.displayBalance()
                val amount = readFloat()
                activeAccount.withdraw(amount)
              }
            case '3' =>
              println("Please choose an account to check its balance:")
              val activeAccount = chooseAccount(activeUserID)
              if (activeAccount != null) {
                activeAccount.displayBalance()
              }
            case 'q' => quit = true
            case _ => println(s"Error: This action ($action) is not recognised")
          }
        }
      } catch {
        case _: StringIndexOutOfBoundsException => println("Error: No input was entered")
        case _: NumberFormatException => println("Error: Input is not the correct number format")
        case exception: Exception => println(s"Error: $exception")
      }
    }
    database.quit()
  }

}
