package fun.wackloner.transferwiser.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Signals that invalid amount of money was provided by a request.
 */
@Provider
public class InvalidAmountException extends RuntimeException implements ExceptionMapper<InvalidAmountException> {
    public InvalidAmountException() {
        super("Amount has to be positive!");
    }

    @Override
    public Response toResponse(InvalidAmountException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .build();
    }
}
