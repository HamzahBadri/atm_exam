package au.com.nuvento.atm_exam

class Account (val ownerIDC: String, val accountNumC: Int, val accountTypeC: String, val balanceC: Float) {
  val ownerID = ownerIDC
  val accountNum = accountNumC
  val accountType = accountTypeC
  val balance = balanceC
}