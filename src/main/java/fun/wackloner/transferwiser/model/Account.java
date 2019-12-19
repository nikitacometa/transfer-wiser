package fun.wackloner.transferwiser.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import fun.wackloner.transferwiser.exception.NotSufficientFundsException;

public class Account {
    private final long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String name;
    private double balance; // TODO: BigDecimal, final

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

    public double getBalance() {
        return balance;
    }

    public synchronized void withdraw(double value) {
        if (balance < value) {
            throw new NotSufficientFundsException(id);
        }
        balance -= value;
    }

    public synchronized void deposit(double value) {
        balance += value;
    }
}
