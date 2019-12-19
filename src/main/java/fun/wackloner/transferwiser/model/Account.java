package fun.wackloner.transferwiser.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import fun.wackloner.transferwiser.exception.InvalidAmountException;
import fun.wackloner.transferwiser.exception.NotSufficientFundsException;

import java.math.BigDecimal;

public class Account {
    private final long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String name;
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public synchronized void withdraw(BigDecimal amount) {
        checkAmountIsPositive(amount);
        if (balance.compareTo(amount) < 0) {
            throw new NotSufficientFundsException(id);
        }

        balance = balance.subtract(amount);
    }

    public synchronized void deposit(BigDecimal amount) {
        checkAmountIsPositive(amount);

        balance = balance.add(amount);
    }

    private static void checkAmountIsPositive(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidAmountException();
        }
    }
}
