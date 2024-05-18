package health.hub.exceptions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        final Map<String, String> constraintViolations = new HashMap<>();

        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            String path = cv.getPropertyPath().toString().split("\\.")[2];
            constraintViolations.put(path, cv.getMessage());
        }

        return Response.status(Response.Status.PRECONDITION_FAILED)
                .entity(constraintViolations)
                .build();}
}
