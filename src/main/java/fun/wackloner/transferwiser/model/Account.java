package fun.wackloner.transferwiser.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import fun.wackloner.transferwiser.exception.InvalidAmountException;
import fun.wackloner.transferwiser.exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    private long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    private BigDecimal balance = BigDecimal.ZERO;

    public Account() {}

    public static Account of(long id) {
        return new Account(id, null, BigDecimal.ZERO);
    }

    public static Account of(long id, String name) {
        return new Account(id, name, BigDecimal.ZERO);
    }

    public static Account of(long id, BigDecimal balance) {
        return new Account(id, null, balance);
    }

    public static Account copy(Account account) {
        return new Account(account.getId(), account.getName(), account.getBalance());
    }

    private Account(long id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = Objects.requireNonNull(balance);
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        var that = (Account) o;
        var equalNames = (that.name == null && name == null) || (that.name != null && that.name.equals(name));
        return that.id == id && equalNames && that.balance.equals(balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance);
    }

    @Override
    public String toString() {
        var nameString = (name == null) ? "" : String.format("name=%s, ", name);
        return String.format("Account{id=%d, %sbalance=%s}", id, nameString, balance.toString());
    }
}
