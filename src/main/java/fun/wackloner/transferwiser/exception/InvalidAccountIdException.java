package fun.wackloner.transferwiser.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidAccountIdException extends RuntimeException implements ExceptionMapper<InvalidAccountIdException> {
    public InvalidAccountIdException() {
        super();
    }

    public InvalidAccountIdException(long id) {
        super(String.format("Account with id %d doesn't exist!", id));
    }

    @Override
    public Response toResponse(InvalidAccountIdException exception) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(exception.getMessage())
                .build();
    }
}
