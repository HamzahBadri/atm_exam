package au.com.nuvento.atm_exam

class Account (val ownerID: String, val accountNum: Int, val accountType: String, var balance: Float) {

  def updateBalance(newBalance: Float) = balance = newBalance
}