package atmtests

import org.junit._
import Assert._
import au.com.nuvento.atm_exam.ATM

@Test
class ATMTest {

  @Test
  def getUserPathTest() = {
    val userPath = ATM.getUserPath()
    assertEquals(userPath, "data/UserInfo.txt")
  }

  @Test
  def getAccountPathTest() = {
    val accountPath = ATM.getAccountPath()
    assertEquals(accountPath, "data/OpeningAccountsData.txt")
  }

}
