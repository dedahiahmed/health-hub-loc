package health.hub.servlets;

import health.hub.entities.Role;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/loginS")
public class LoginServlet extends HttpServlet {
    @Inject
    UserRepository userRepository;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        boolean isAuthenticated = userRepository.checkCredentials(username, password);
        System.out.println("isAuthenticated" + isAuthenticated);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Role role = userRepository.getRoleByUsername(username);
        System.out.println("role" + role);
        HttpSession session = request.getSession();
        session.setAttribute("userRole", role);
        if (isAuthenticated && role == Role.ADMIN) {

            out.println("<html><body>");
            out.println("<h2>Login Successful!</h2>");
            out.println("<p>Welcome, " + username + "!</p>");
            out.println("</body></html>");
        } else {
            out.println("<html><body>");
            out.println("<h2>Login Failed!</h2>");
            out.println("<p>Invalid username or password.</p>");
            out.println("</body></html>");
        }
    }
}
