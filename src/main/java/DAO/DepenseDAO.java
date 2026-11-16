package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import Modele.Depense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Acces a la table DEPENSE : lire, ajouter, modifier, supprimer. */
public class DepenseDAO {

    private static final String COLONNES = "IDDEPENSE, ID_UTILISATEUR, LIBELLE, MONTANT, DATEDEPENSE";

    // Transforme la ligne courante du ResultSet en objet Java
    private Depense convertir(ResultSet rs) throws SQLException {
        return new Depense(
                rs.getInt("IDDEPENSE"),
                rs.getInt("ID_UTILISATEUR"),
                rs.getString("LIBELLE"),
                rs.getDouble("MONTANT"),
                OutilsSQL.toLocalDateTime(rs.getTimestamp("DATEDEPENSE"))
        );
    }

    public List<Depense> trouverTous() throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DEPENSE ORDER BY IDDEPENSE";
        List<Depense> liste = new ArrayList<>();
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(convertir(rs));
            }
        }
        return liste;
    }

    public Depense trouverParId(int id) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DEPENSE WHERE IDDEPENSE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        }
    }

    public List<Depense> trouverParIdUtilisateur(int valeur) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DEPENSE WHERE ID_UTILISATEUR = ? ORDER BY IDDEPENSE";
        List<Depense> liste = new ArrayList<>();
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, valeur);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(convertir(rs));
                }
            }
        }
        return liste;
    }

    /** Ajoute la ligne, recupere l'identifiant genere par Oracle et le met dans l'objet. */
    public int ajouter(Depense depense) throws SQLException {
        String sql = "INSERT INTO DEPENSE (ID_UTILISATEUR, LIBELLE, MONTANT, DATEDEPENSE) VALUES (?, ?, ?, ?)";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql, new String[]{"IDDEPENSE"})) {
            ps.setInt(1, depense.getIdUtilisateur());
            ps.setString(2, depense.getLibelle());
            ps.setDouble(3, depense.getMontant());
            ps.setTimestamp(4, OutilsSQL.toTimestamp(depense.getDateDepense()));
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    depense.setIdDepense(cles.getInt(1));
                }
            }
            return depense.getIdDepense();
        }
    }

    public boolean modifier(Depense depense) throws SQLException {
        String sql = "UPDATE DEPENSE SET ID_UTILISATEUR = ?, LIBELLE = ?, MONTANT = ?, DATEDEPENSE = ? WHERE IDDEPENSE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, depense.getIdUtilisateur());
            ps.setString(2, depense.getLibelle());
            ps.setDouble(3, depense.getMontant());
            ps.setTimestamp(4, OutilsSQL.toTimestamp(depense.getDateDepense()));
            ps.setInt(5, depense.getIdDepense());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean supprimer(int id) throws SQLException {
        String sql = "DELETE FROM DEPENSE WHERE IDDEPENSE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
