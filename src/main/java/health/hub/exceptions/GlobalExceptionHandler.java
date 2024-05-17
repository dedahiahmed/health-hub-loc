package health.hub.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        // Handle different types of exceptions and map them to appropriate HTTP responses
        if (exception instanceof IllegalArgumentException) {
            // Handle IllegalArgumentException
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(exception.getMessage())
                    .build();
        } else if (exception instanceof ConstraintViolationException) {
            // Handle ConstraintViolationException
            return handleConstraintViolationException((ConstraintViolationException) exception);
        } else {
            // Handle other types of exceptions (generic error handling)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An unexpected error occurred")
                    .build();
        }
    }

    private Response handleConstraintViolationException(ConstraintViolationException exception) {
        final Map<String, String> constraintViolations = new HashMap<>();

        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            String path = cv.getPropertyPath().toString().split("\\.")[2];
            constraintViolations.put(path, cv.getMessage());
        }

        return Response.status(Response.Status.PRECONDITION_FAILED)
                .entity(constraintViolations)
                .build();
    }
}
