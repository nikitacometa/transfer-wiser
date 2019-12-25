package fun.wackloner.transferwiser.repository;

import fun.wackloner.transferwiser.model.Account;

import java.util.Collection;

/**
 * An interface for creating and managing accounts.
 */
public interface AccountRepository {
    Account createAccount(String name);

    Account getAccount(long id);

    void removeAccount(long id);

    Collection<Account> getAllAccounts();
}
