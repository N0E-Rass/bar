package bar.fenetreConnexion;
import org.mindrot.jbcrypt.BCrypt;
public class paasword {

    public class PasswordUtil {

        public static String hasher(String motDePasse) {
            return BCrypt.hashpw(
                    motDePasse,
                    BCrypt.gensalt(12)
            );
        }

        public static boolean verifier(
                String motDePasse,
                String hash
        ) {
            if (motDePasse == null || hash == null) {
                return false;
            }

            try {
                return BCrypt.checkpw(motDePasse, hash);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }
}