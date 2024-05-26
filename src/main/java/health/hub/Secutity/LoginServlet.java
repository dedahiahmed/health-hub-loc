package health.hub.Secutity;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

@WebServlet(name = "LoginServlet",urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Inject
    AuthService authService;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lecture du corps de la requête
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        // Convertir le corps de la requête en objet JSON
        JsonObject jsonObject = Json.createReader(new StringReader(requestBody.toString())).readObject();
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        String token = authService.authenticate(username, password);

        if (token != null) {
            // L'authentification a réussi, renvoie le token dans la réponse
            response.setStatus(HttpServletResponse.SC_OK);
            // Créez un objet JSON contenant le token
            JsonObject jsonResponse = Json.createObjectBuilder()
                    .add("token", token)
                    .build();
            // Configurez la réponse avec le JSON et le type de contenu
            response.setContentType(MediaType.APPLICATION_JSON);
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonResponse.toString());
            out.flush();
        } else {
            // L'authentification a échoué, renvoie le statut 403 Forbidden
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
