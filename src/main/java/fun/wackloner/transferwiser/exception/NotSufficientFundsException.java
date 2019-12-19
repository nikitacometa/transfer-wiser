package fun.wackloner.transferwiser.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotSufficientFundsException extends RuntimeException implements ExceptionMapper<NotSufficientFundsException> {
    public NotSufficientFundsException() {
        super();
    }

    public NotSufficientFundsException(long id) {
        super(String.format("Account with id %d doesn't have sufficient funds!", id));
    }

    @Override
    public Response toResponse(NotSufficientFundsException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .build();
    }
}
