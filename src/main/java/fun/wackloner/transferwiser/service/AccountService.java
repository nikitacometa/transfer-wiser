package fun.wackloner.transferwiser.service;

import fun.wackloner.transferwiser.model.Account;
import fun.wackloner.transferwiser.repository.AccountRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountService {
    private AccountRepository repository = AccountRepository.getInstance();

    @GET
    @Path("/create")
    public Account createAccount() {
        return repository.createAccount();
    }

    @GET
    @Path("/{id}")
    public Account getAccount(@PathParam("id") String id) {
        var account = repository.getAccount(Integer.getInteger(id));
        System.out.println(account.getId());
        return account;
    }

    @GET
    public List<Account> getAll() {
        return new ArrayList<>(repository.getAll());
    }
}
