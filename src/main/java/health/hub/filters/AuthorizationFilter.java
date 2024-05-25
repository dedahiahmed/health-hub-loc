package health.hub.filters;

import health.hub.annotations.RoleRequired;
import health.hub.entities.Role;
import jakarta.annotation.Priority;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.lang.reflect.Method;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method resourceMethod = resourceInfo.getResourceMethod();

        if (resourceMethod.isAnnotationPresent(RoleRequired.class)) {
            RoleRequired roleRequired = resourceMethod.getAnnotation(RoleRequired.class);
            Role requiredRole = Role.valueOf(roleRequired.value());

            Role userRole = (Role) request.getSession().getAttribute("userRole");
            System.out.println("userRole" + userRole);
            if (userRole == null || userRole != requiredRole) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("You don't have permission to access this resource")
                        .build());
            }
        }
    }
}
