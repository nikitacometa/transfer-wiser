package fun.wackloner.transferwiser.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Signals that an account doesn't have enough money for withdrawal.
 */
@Provider
public class InsufficientFundsException extends RuntimeException implements ExceptionMapper<InsufficientFundsException> {
    public InsufficientFundsException() {
        super();
    }

    public InsufficientFundsException(long id) {
        super(String.format("Account with id %d doesn't have sufficient funds!", id));
    }

    @Override
    public Response toResponse(InsufficientFundsException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .build();
    }
}
