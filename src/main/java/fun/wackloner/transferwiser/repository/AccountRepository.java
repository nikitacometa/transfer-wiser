package fun.wackloner.transferwiser.repository;

import fun.wackloner.transferwiser.model.Account;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {
    private long nextId = 1; // TODO: atomic
    private Map<Long, Account> accounts = new ConcurrentHashMap<>();

    public Account createAccount() {
        var newAccount = new Account(nextId++);
        accounts.put(newAccount.getId(), newAccount);
        return newAccount;
    }

    public Collection<Account> getAll() {
        return accounts.values();
    }

    public Account getAccount(long id) {
        return accounts.get(id);
    }

    private static AccountRepository instance;
    private AccountRepository() {}
    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }
}
