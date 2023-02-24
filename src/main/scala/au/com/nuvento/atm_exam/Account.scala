package au.com.nuvento.atm_exam

class Account (val ownerID: String, val accountNum: Int, val accountType: String, var balance: Float) {

  def displayBalance() = {
    println(s"Account $accountNum balance: $$" + "%.2f".format(balance))
  }

  def deposit(amount: Float) = {
    if (amount < 0) println(s"Error: requested amount ($$${"%.2f".format(amount)}) is less than 0")
    else {
      balance = balance + amount
      println("Transaction successful")
      displayBalance()
    }
  }

  def withdraw(amount: Float) = {
    if (amount < 0) println(s"Error: requested amount ($$${"%.2f".format(amount)}) is less than 0")
    else if (amount > balance) println(s"Error: " +
      "Account does not have sufficient funds to withdraw requested amount ($" + "%.2f".format(amount) + ")")
    else {
      balance = balance - amount
      println("Transaction successful")
      displayBalance()
    }
  }
}