package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import bar.fenetreConnexion.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDAO {

    // Noms des colonnes : à modifier ICI seulement si ta table est différente
    private static final String COL_ID   = "ID_UTILISATEUR";
    private static final String COL_NOM  = "NOM_UTILISATEUR";
    private static final String COL_MDP  = "MOT_DEPASSE";
    private static final String COL_ROLE = "ROLE";

    public Utilisateur authentifier(String nomUtilisateur, String motDePasse) throws SQLException {

        String sql = "SELECT " + COL_ID + ", " + COL_NOM + ", " + COL_MDP + ", " + COL_ROLE +
                " FROM UTILISATEUR" +
                " WHERE UPPER(TRIM(" + COL_NOM + ")) = UPPER(?)";

        try (Connection connexion = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = connexion.prepareStatement(sql)) {

            ps.setString(1, nomUtilisateur);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && verifierMotDePasse(motDePasse, rs.getString(COL_MDP))) {
                    return new Utilisateur(rs.getInt(COL_ID),
                            rs.getString(COL_NOM),
                            rs.getString(COL_ROLE));
                }
                return null;
            }
        }
    }

    // Version temporaire pour les tests : à remplacer par BCrypt plus tard
    private boolean verifierMotDePasse(String motDePasseSaisi, String motDePasseEnregistre) {
        return motDePasseSaisi != null && motDePasseSaisi.equals(motDePasseEnregistre);
    }
}