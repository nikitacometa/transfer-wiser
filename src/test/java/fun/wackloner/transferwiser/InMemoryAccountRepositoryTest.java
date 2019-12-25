package fun.wackloner.transferwiser;

import fun.wackloner.transferwiser.exception.InvalidAccountIdException;
import fun.wackloner.transferwiser.model.Account;
import fun.wackloner.transferwiser.repository.InMemoryAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryAccountRepositoryTest {
    private InMemoryAccountRepository accountRepository;

    @BeforeEach
    public void before() {
        accountRepository = new InMemoryAccountRepository();
    }

    @Test
    public void testCreateAccount() {
        var account = accountRepository.createAccount(null);
        assertEquals(Account.of(1), account);

        account = accountRepository.createAccount("Test");
        assertEquals(Account.of(2, "Test"), account);
    }

    @Test
    public void testGetAccount() {
        accountRepository.createAccount("Test");
        var account = accountRepository.getAccount(1);
        assertEquals(Account.of(1, "Test"), account);

        assertThrows(InvalidAccountIdException.class, () -> accountRepository.getAccount(1337));
    }

    @Test
    public void testRemoveAccount() {
        accountRepository.createAccount("Test");
        accountRepository.removeAccount(1);

        assertThrows(InvalidAccountIdException.class, () -> accountRepository.getAccount(1));
        assertThrows(InvalidAccountIdException.class, () -> accountRepository.removeAccount(1));
    }

    @Test
    public void testGetAllAccounts() {
        accountRepository.createAccount(null);
        accountRepository.createAccount("Test");
        accountRepository.createAccount("Test420");

        var expected = List.of(Account.of(1), Account.of(2, "Test"), Account.of(3, "Test420"));
        var actual = List.copyOf(accountRepository.getAllAccounts());
        assertEquals(expected, actual);
    }
}
