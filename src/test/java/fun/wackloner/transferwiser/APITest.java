package fun.wackloner.transferwiser;

import com.fasterxml.jackson.databind.ObjectMapper;
import fun.wackloner.transferwiser.model.Account;
import fun.wackloner.transferwiser.repository.AccountRepository;
import fun.wackloner.transferwiser.repository.InMemoryAccountRepository;
import fun.wackloner.transferwiser.server.ApplicationServer;
import org.eclipse.jetty.server.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class APITest {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static ApplicationServer server;

    private static AccountRepository getTestRepository() {
        var repository = new InMemoryAccountRepository();
        IntStream.range(0, 7).forEach((index) -> repository.createAccount(null));
        repository.getAccount(4).deposit(BigDecimal.TEN);
        repository.getAccount(6).deposit(BigDecimal.TEN);
        return repository;
    }

    @BeforeAll
    public static void setUp() {
        server = ApplicationServer.newInstance(getTestRepository());
        server.start(false);
    }

    @AfterAll
    public static void tearDown() {
        server.stop();
    }

    @Test
    public void testCreateAccount() throws IOException, InterruptedException {
        var response = sendRequest("POST", "/accounts/create");
        var res = buildObjectFromResponse(response, Account.class);
        assertEquals(Account.of(8), res);

        response = sendRequest("POST", "/accounts/create?name=Tester");
        res = buildObjectFromResponse(response, Account.class);
        assertEquals(Account.of(9, "Tester"), res);
    }

    @Test
    public void testGetAccount() throws IOException, InterruptedException {
        var response = sendRequest("GET", "/accounts/2");
        var res = buildObjectFromResponse(response, Account.class);
        assertEquals(Account.of(2), res);

        response = sendRequest("GET", "/accounts/1337");
        assertEquals(Response.SC_NOT_FOUND, response.statusCode());
    }

    @Test
    public void testGetAccounts() throws IOException, InterruptedException {
        var response = sendRequest("GET", "/accounts?fromId=2&limit=2");
        var res = Arrays.asList(buildObjectFromResponse(response, Account[].class));

        assertEquals(List.of(Account.of(2), Account.of(3)), res);
    }

    @Test
    public void testWithdraw() throws IOException, InterruptedException {
        var response = sendRequest("POST", "/accounts/4/withdraw?amount=-1");
        assertEquals(Response.SC_BAD_REQUEST, response.statusCode());

        response = sendRequest("POST", "/accounts/4/withdraw?amount=10");
        assertEquals(Response.SC_NO_CONTENT, response.statusCode());

        response = sendRequest("POST", "/accounts/4/withdraw?amount=10");
        assertEquals(Response.SC_BAD_REQUEST, response.statusCode());
    }

    @Test
    public void testDeposit() throws IOException, InterruptedException {
        var response = sendRequest("POST", "/accounts/5/deposit?amount=-1");
        assertEquals(Response.SC_BAD_REQUEST, response.statusCode());

        response = sendRequest("POST", "/accounts/5/deposit?amount=10");
        assertEquals(Response.SC_NO_CONTENT, response.statusCode());

        response = sendRequest("GET", "/accounts/5");
        var account = buildObjectFromResponse(response, Account.class);
        assertEquals(BigDecimal.TEN, account.getBalance());
    }

    @Test
    public void testTransfer() throws IOException, InterruptedException {
        var response = sendRequest("POST", "/accounts/6/transfer?to=7&amount=10");
        assertEquals(Response.SC_NO_CONTENT, response.statusCode());

        response = sendRequest("GET", "/accounts?fromId=6&limit=2");
        var accounts = Arrays.asList(buildObjectFromResponse(response, Account[].class));
        assertEquals(List.of(Account.of(6), Account.of(7, BigDecimal.TEN)), accounts);

        response = sendRequest("POST", "/accounts/6/transfer?to=7&amount=0.0001");
        assertEquals(Response.SC_BAD_REQUEST, response.statusCode());
    }

    @Test
    public void testRemoveAccount() throws IOException, InterruptedException {
        var response = sendRequest("DELETE", "/accounts/1");
        assertEquals(Response.SC_NO_CONTENT, response.statusCode());

        response = sendRequest("DELETE", "/accounts/1");
        assertEquals(Response.SC_NOT_FOUND, response.statusCode());
    }

    private static <T> T buildObjectFromResponse(HttpResponse<String> response, Class<T> clazz)
            throws IOException {
        return mapper.readValue(response.body(), clazz);
    }

    private static HttpResponse<String> sendRequest(String method, String endpoint) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(ApplicationServer.BASE_URL + endpoint))
                .method(method, HttpRequest.BodyPublishers.noBody())
                .build();
        return HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
    }
}
