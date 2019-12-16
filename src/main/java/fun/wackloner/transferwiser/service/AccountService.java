package fun.wackloner.transferwiser.service;

import fun.wackloner.transferwiser.model.Account;
import fun.wackloner.transferwiser.repository.AccountRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountService {
    private AccountRepository accountRepository = AccountRepository.getInstance();

    @GET
    public Collection<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    // TODO: POST
    @GET
    @Path("/create")
    public Account createAccount(@QueryParam("name") String name) {
        return accountRepository.createAccount(name);
    }

    @GET
    @Path("/{id}")
    public Account getAccount(@PathParam("id") long id) {
        return accountRepository.getAccount(id);
    }

    @POST
    @Path("/{id}/deposit")
    public Account depositAccount(@PathParam("id") long id, @QueryParam("amount") double amount) {
        var account = accountRepository.getAccount(id);
        account.deposit(amount);
        return account;
    }

    @POST
    @Path("/{id}/withdraw")
    public Account withdrawAccount(@PathParam("id") long id, @QueryParam("amount") double amount) {
        var account = accountRepository.getAccount(id);
        account.withdraw(amount);
        return account;
    }

    @POST
    @Path("/{id}/transfer")
    public Response transfer(@PathParam("id") long id, @QueryParam("to") long to, @QueryParam("amount") double amount) {
        // TODO: check input validity

        accountRepository.getAccount(id).withdraw(amount);
        accountRepository.getAccount(to).deposit(amount);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeAccount(@PathParam("id") long id) {
        boolean isRemoved = accountRepository.removeAccount(id);
        return isRemoved ? Response.noContent().build() : Response.status(404).build();
    }
}
