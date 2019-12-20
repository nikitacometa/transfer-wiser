package fun.wackloner.transferwiser.repository;

import fun.wackloner.transferwiser.exception.InvalidAccountIdException;
import fun.wackloner.transferwiser.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AccountRepository {
    private static final Logger log = LoggerFactory.getLogger(AccountRepository.class);

    // TODO: use guice
    private static final AccountRepository INSTANCE = new AccountRepository();

    private final AtomicLong nextId = new AtomicLong(1);
    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();

    public Account createAccount(String name) {
        var newAccount = Account.of(nextId.getAndIncrement(), name);
        accounts.put(newAccount.getId(), newAccount);

        log.info("Account with id '{}' was created.", newAccount.getId());
        return newAccount;
    }

    public Account getAccount(long id) {
        var account = accounts.get(id);
        if (account == null) {
            throw new InvalidAccountIdException(id);
        }
        return account;
    }

    public void removeAccount(long id) {
        if (accounts.remove(id) == null) {
            throw new InvalidAccountIdException(id);
        }
        log.info("Account with id '{}' was removed.", id);
    }

    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }

    private AccountRepository() {}

    public static AccountRepository getInstance() {
        return INSTANCE;
    }
}
