package health.hub.exceptions;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.NotSupportedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof IllegalArgumentException) {
            return handleIllegalArgumentException((IllegalArgumentException) exception);
        } else if (exception instanceof NotFoundException) {
            return handleNotFoundException((NotFoundException) exception);
        } else if (exception instanceof NotSupportedException) {
            return handleNotSupportedException((NotSupportedException) exception);
        } else if (exception instanceof InvalidEnumValueException) {
            return handleInvalidEnumValueException((InvalidEnumValueException) exception);
        } else {
            return handleOtherExceptions(exception);
        }
    }

    private Response handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }

    private Response handleNotFoundException(NotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }

    private Response handleNotSupportedException(NotSupportedException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(error).build();
    }

    private Response handleInvalidEnumValueException(InvalidEnumValueException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }

    private Response handleOtherExceptions(Throwable ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
}
