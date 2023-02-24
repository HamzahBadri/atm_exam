package atmtests

import org.junit._
import Assert._
import au.com.nuvento.atm_exam.Account

@Test
class AccountTest {

    @Test
    def testDepositFail() = {
        val testAccount: Account = Account("000", 1234578, "Cheque", 123.45)
        val balanceBefore = testAccount.balance
        testAccount.deposit(-100)
        val balanceAfter = testAccount.balance
        assertTrue(balanceBefore == balanceAfter)
    }

    @Test
    def testDepositSuccess() = {
        val testAccount: Account = Account("000", 1234578, "Cheque", 123.45)
        val balanceBefore = testAccount.balance
        testAccount.deposit(100)
        val balanceAfter = testAccount.balance
        assertTrue(balanceBefore < balanceAfter)
    }

    @Test
    def testWithdrawFailNegative() = {
        val testAccount: Account = Account("000", 1234578, "Cheque", 123.45)
        val balanceBefore = testAccount.balance
        testAccount.withdraw(-100)
        val balanceAfter = testAccount.balance
        assertTrue(balanceBefore == balanceAfter)
    }

    @Test
    def testWithdrawFailExcess() = {
        val testAccount: Account = Account("000", 1234578, "Cheque", 123.45)
        val balanceBefore = testAccount.balance
        testAccount.withdraw(200)
        val balanceAfter = testAccount.balance
        assertTrue(balanceBefore == balanceAfter)
    }

    @Test
    def testWithdrawSuccess() = {
        val testAccount: Account = Account("000", 1234578, "Cheque", 123.45)
        val balanceBefore = testAccount.balance
        testAccount.withdraw(100)
        val balanceAfter = testAccount.balance
        assertTrue(balanceBefore > balanceAfter)
    }

}


