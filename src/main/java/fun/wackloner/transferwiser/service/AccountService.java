package fun.wackloner.transferwiser.service;

import fun.wackloner.transferwiser.model.Account;
import fun.wackloner.transferwiser.repository.AccountRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountService {
    private static final Object TRANSFER_LOCK = new Object();

    private AccountRepository accountRepository = AccountRepository.getInstance();

    @GET
    public List<Account> getAllAccounts() {
        List<Account> accounts;
        synchronized (TRANSFER_LOCK) {
            // TODO: pagination
            accounts = new ArrayList<>(accountRepository.getAllAccounts());
        }
        return accounts;
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
    public Response depositAccount(@PathParam("id") long id, @QueryParam("amount") double amount) {
        accountRepository.getAccount(id).deposit(amount);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/withdraw")
    public Response withdrawAccount(@PathParam("id") long id, @QueryParam("amount") double amount) {
        accountRepository.getAccount(id).withdraw(amount);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/transfer")
    public Response transfer(@PathParam("id") long id, @QueryParam("to") long to, @QueryParam("amount") double amount) {
        // TODO: check input validity

        synchronized (TRANSFER_LOCK) {
            var sender = accountRepository.getAccount(id);
            var receiver = accountRepository.getAccount(to);

            sender.withdraw(amount);
            receiver.deposit(amount);
        }

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeAccount(@PathParam("id") long id) {
        // TODO: rework
        boolean isRemoved = accountRepository.removeAccount(id);
        return isRemoved ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
