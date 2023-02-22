package atmtests

import au.com.nuvento.atm_exam.Database
import org.junit.*
import org.junit.Assert.*

import java.io.{File, FileWriter}

@Test
class DatabaseTest {

  var database: Database = _

  @Before
  def init(): Unit = {
    val fileWriter = new FileWriter(new File("src/test/resources/OpeningAccountsDataTest.txt"))
    fileWriter.write("AccountOwnerID|||AccountNumber|||AccountType|||OpeningBalance\n")
    fileWriter.write("001|||9264945|||Cheque|||500.90\n")
    fileWriter.write("002|||9676422|||Saving|||1200.00\n")
    fileWriter.write("001|||7814135|||Saving|||200.09\n")
    fileWriter.close()
    database = Database("src/test/resources/UserInfoTest.txt",
      "src/test/resources/OpeningAccountsDataTest.txt")
  }

  @Test
  def getUserAccountsTest() = {
    val user001Accounts = database.getUserAccounts("001")
    val user002Accounts = database.getUserAccounts("002")
    val user003Accounts = database.getUserAccounts("003")
    assertEquals(user001Accounts.length, 2)
    assertEquals(user002Accounts.length, 1)
    assertEquals(user003Accounts.length, 0)
  }

  @Test
  def quitWithNoChangeTest() = {
    val bufferedSourceBefore = scala.io.Source.fromFile("src/test/resources/OpeningAccountsDataTest.txt")
    val accountLinesBefore = bufferedSourceBefore.getLines().toList
    bufferedSourceBefore.close()
    database.quit()
    val bufferedSourceAfter = scala.io.Source.fromFile("src/test/resources/OpeningAccountsDataTest.txt")
    val accountLinesAfter = bufferedSourceAfter.getLines().toList
    bufferedSourceAfter.close()
    assertEquals(accountLinesBefore, accountLinesAfter)
  }

  @Test
  def quitWithChangeTest() = {
    val bufferedSourceBefore = scala.io.Source.fromFile("src/test/resources/OpeningAccountsDataTest.txt")
    val accountLinesBefore = bufferedSourceBefore.getLines().toList
    bufferedSourceBefore.close()
    for (account <- database.accountList) {
      val newBalance = account.balance + 10
      account.updateBalance(newBalance)
    }
    database.quit()
    val bufferedSourceAfter = scala.io.Source.fromFile("src/test/resources/OpeningAccountsDataTest.txt")
    val accountLinesAfter = bufferedSourceAfter.getLines().toList
    bufferedSourceAfter.close()
    assertTrue(accountLinesBefore != accountLinesAfter)
  }

}


