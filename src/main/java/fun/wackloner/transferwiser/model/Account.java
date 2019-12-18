package fun.wackloner.transferwiser.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Account {
    private long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    private double balance; // TODO: BigDecimal

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
        // TODO: exception
        balance -= value;
    }

    public synchronized void deposit(double value) {
        balance += value;
    }
}
