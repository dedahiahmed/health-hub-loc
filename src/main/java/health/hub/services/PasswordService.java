package health.hub.services;


import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordService {
    private static final int BCRYPT_LOG_ROUNDS = 12;

    public static String hashPassword(String plainTextPassword) {
        // Utiliser BCrypt pour générer un hash de mot de passe sécurisé
        return BCrypt.withDefaults().hashToString(BCRYPT_LOG_ROUNDS, plainTextPassword.toCharArray());
    }

    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        // Utiliser BCrypt pour vérifier si le mot de passe correspond au hash
        BCrypt.Result result = BCrypt.verifyer().verify(plainTextPassword.toCharArray(), hashedPassword);
        return result.verified;
    }
}
