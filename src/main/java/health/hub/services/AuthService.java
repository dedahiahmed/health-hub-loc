package health.hub.services;


import health.hub.entities.User;
import health.hub.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;

import java.security.Key;
import java.util.Date;

public class AuthService {

    public static final String SECRET_KEY = "6E3272357538782F413F4428472B4B6250655367566B59703373367639792442";
    private static final long TOKEN_EXPIRATION_TIME = 3600000; // 1 heure en millisecondes
    @Inject
    UserRepository userRepository;

    public String generateToken(User user) {
        Date expirationDate = new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME);
        System.out.println("Token");
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .claim("role", user.getRole())
                .setExpiration(expirationDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public static boolean isValidToken(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        System.out.println("Token");
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Date expirationDate = claims.getExpiration();

            return expirationDate != null && !expirationDate.before(new Date());
        } catch (Exception e) {
            System.out.println("No valide token");
            return false;
        }
    }


    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && PasswordService.verifyPassword(password, user.getPassword())) {
            return generateToken(user);
        }

        return null;
    }

    public static Claims getClaimsFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println("Invalid token");
            return null;
        }
    }
}
