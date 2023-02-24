package atmtests

import au.com.nuvento.atm_exam.Database
import au.com.nuvento.atm_exam.User
import org.junit.*
import org.junit.Assert.*

import java.io.{File, FileWriter}

@Test
class DatabaseTest {

  var database: Database = _
  val userPath = "src/test/resources/UserInfoTest.txt"
  val accountPath = "src/test/resources/OpeningAccountsDataTest.txt"

  @Before
  def init(): Unit = {
    val fileWriter = new FileWriter(new File("src/test/resources/OpeningAccountsDataTest.txt"))
    fileWriter.write("AccountOwnerID|||AccountNumber|||AccountType|||OpeningBalance\n")
    fileWriter.write("001|||9264945|||Cheque|||500.90\n")
    fileWriter.write("002|||9676422|||Saving|||1200.00\n")
    fileWriter.write("001|||7814135|||Saving|||200.09\n")
    fileWriter.close()
    database = Database(userPath, accountPath)
  }

  @Test
  def readTextFileUserTest() = {
    val userTextLines = database.readTextFile(userPath)
    val numberOfUsersPlusHeaders = 4
    assertEquals(userTextLines.length, numberOfUsersPlusHeaders)
    for (line <- userTextLines) {
      val splitLine = line.split(",")
      val parametersPerLine = 4
      assertEquals(splitLine.length, parametersPerLine)
    }
  }

  @Test
  def readTextFileAccountTest() = {
    val accountTextLines = database.readTextFile(accountPath)
    val numberOfAccountsPlusHeaders = 4
    assertEquals(accountTextLines.length, numberOfAccountsPlusHeaders)
    for (line <- accountTextLines) {
      val splitLine = line.split("\\|\\|\\|")
      val parametersPerLine = 4
      assertEquals(splitLine.length, parametersPerLine)
    }
  }

  @Test
  def getUserFromIDTest() = {
    val user001 = database.getUserFromID("001")
    val user002 = database.getUserFromID("002")
    val user003 = database.getUserFromID("003")
    val user004 = database.getUserFromID("004")
    assertNotNull(user001)
    assertNotNull(user002)
    assertNotNull(user003)
    assertNull(user004)
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
      account.deposit(10)
    }
    database.quit()
    val bufferedSourceAfter = scala.io.Source.fromFile("src/test/resources/OpeningAccountsDataTest.txt")
    val accountLinesAfter = bufferedSourceAfter.getLines().toList
    bufferedSourceAfter.close()
    assertTrue(accountLinesBefore != accountLinesAfter)
  }

}


