package fun.wackloner.transferwiser.model;

import fun.wackloner.transferwiser.exception.InvalidAmountException;
import fun.wackloner.transferwiser.exception.InsufficientFundsException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    @Test
    public void testDeposit() {
        Account testAccount = Account.of(1);

        testAccount.deposit(BigDecimal.valueOf(20));
        assertEquals(BigDecimal.valueOf(20), testAccount.getBalance());

        testAccount.deposit(new BigDecimal("0.00000000000000000000000000000000001"));
        assertEquals(new BigDecimal("20.00000000000000000000000000000000001"), testAccount.getBalance());

        assertThrows(InvalidAmountException.class, () -> testAccount.deposit(BigDecimal.valueOf(-123)));
    }

    @Test
    public void testWithdraw() {
        Account testAccount = Account.of(1, BigDecimal.TEN);

        testAccount.withdraw(BigDecimal.ONE);
        assertEquals(BigDecimal.valueOf(9), testAccount.getBalance());

        testAccount.withdraw(BigDecimal.valueOf(9));
        assertEquals(BigDecimal.ZERO, testAccount.getBalance());

        assertThrows(InvalidAmountException.class, () -> testAccount.withdraw(BigDecimal.ZERO));

        assertThrows(InsufficientFundsException.class, () -> testAccount.withdraw(BigDecimal.ONE));
    }
}
