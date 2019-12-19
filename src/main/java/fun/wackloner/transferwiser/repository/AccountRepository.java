package fun.wackloner.transferwiser.repository;

import fun.wackloner.transferwiser.exception.InvalidAccountIdException;
import fun.wackloner.transferwiser.model.Account;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AccountRepository {
    // TODO: use guice
    private static AccountRepository INSTANCE = new AccountRepository();

    private AtomicLong nextId = new AtomicLong(1);
    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();

    public Account createAccount(String name) {
        var newAccount = new Account(nextId.getAndIncrement(), name);
        accounts.put(newAccount.getId(), newAccount);
        return newAccount;
    }

    public Account getAccount(long id) {
        var account = accounts.get(id);
        if (account == null) {
            throw new InvalidAccountIdException(id);
        }
        return account;
    }

    public boolean removeAccount(long id) {
        return accounts.remove(id) != null;
    }

    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }

    private AccountRepository() {}

    public static AccountRepository getInstance() {
        return INSTANCE;
    }
}
