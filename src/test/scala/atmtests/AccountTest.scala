package atmtests

import org.junit._
import Assert._
import au.com.nuvento.atm_exam.Account

@Test
class AccountTest {

    @Test
    def testUpdateBalance() = {
        val testAccount: Account = Account("000", 1234578, "Cheque", 123.45)
        val balanceBefore = testAccount.balance
        testAccount.updateBalance(100)
        val balanceAfter = testAccount.balance
        assertTrue(balanceBefore != balanceAfter)
    }

}


