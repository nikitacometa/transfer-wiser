package fun.wackloner.transferwiser.repository;

import fun.wackloner.transferwiser.model.Account;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {
    // TODO: use guice
    private static AccountRepository INSTANCE = new AccountRepository();

    private long nextId = 1; // TODO: atomic
    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();

    public Account createAccount(String name) {
        var newAccount = new Account(nextId++, name);
        accounts.put(newAccount.getId(), newAccount);
        return newAccount;
    }

    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }

    public Account getAccount(long id) {
        return accounts.get(id); // TODO: handle absence
    }

    public boolean removeAccount(long id) {
        return accounts.remove(id) != null;
    }

    private AccountRepository() {}

    public static AccountRepository getInstance() {
        return INSTANCE;
    }
}
