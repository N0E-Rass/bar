package bar.fenetreConnexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionOracle {


    public static Connection obtenirConnexion() throws SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver Oracle introuvable : ojdbc.jar doit être dans WEB-INF/lib", e);
        }
        String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
        String UTILISATEUR = "BARUSER";
        String MOT_DE_PASSE = "BARUSER";
        return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
    }

    public static void main (String[] args) {
      try (Connection connection = obtenirConnexion()) {
          System.out.println("Connexion a oracle reussie");
      } catch (SQLException e) {
          System.out.println("Erreur de connexion :" + e.getMessage());
      }
   } 
}