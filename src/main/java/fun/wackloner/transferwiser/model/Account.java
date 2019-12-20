package fun.wackloner.transferwiser.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import fun.wackloner.transferwiser.exception.InvalidAmountException;
import fun.wackloner.transferwiser.exception.InsufficientFundsException;

import java.math.BigDecimal;

public class Account {
    private final long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String name;
    private BigDecimal balance;

    public static Account of(long id) {
        return new Account(id, null, BigDecimal.ZERO);
    }

    public static Account of(long id, String name) {
        return new Account(id, name, BigDecimal.ZERO);
    }

    public static Account of(long id, BigDecimal balance) {
        return new Account(id, null, balance);
    }

    private Account(long id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
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
            throw new InsufficientFundsException(id);
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
