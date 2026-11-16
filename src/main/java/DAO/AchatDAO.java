package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import Modele.Achat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Acces a la table ACHAT : lire, ajouter, modifier, supprimer. */
public class AchatDAO {

    private static final String COLONNES = "IDACHAT, DATEACHAT, ID_UTILISATEUR, MONTANTTOTAL";

    // Transforme la ligne courante du ResultSet en objet Java
    private Achat convertir(ResultSet rs) throws SQLException {
        return new Achat(
                rs.getInt("IDACHAT"),
                OutilsSQL.toLocalDateTime(rs.getTimestamp("DATEACHAT")),
                rs.getInt("ID_UTILISATEUR"),
                rs.getDouble("MONTANTTOTAL")
        );
    }

    public List<Achat> trouverTous() throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM ACHAT ORDER BY IDACHAT";
        List<Achat> liste = new ArrayList<>();
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(convertir(rs));
            }
        }
        return liste;
    }

    public Achat trouverParId(int id) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM ACHAT WHERE IDACHAT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        }
    }

    public List<Achat> trouverParIdUtilisateur(int valeur) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM ACHAT WHERE ID_UTILISATEUR = ? ORDER BY IDACHAT";
        List<Achat> liste = new ArrayList<>();
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
    public int ajouter(Achat achat) throws SQLException {
        String sql = "INSERT INTO ACHAT (DATEACHAT, ID_UTILISATEUR, MONTANTTOTAL) VALUES (?, ?, ?)";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql, new String[]{"IDACHAT"})) {
            ps.setTimestamp(1, OutilsSQL.toTimestamp(achat.getDateAchat()));
            ps.setInt(2, achat.getIdUtilisateur());
            ps.setDouble(3, achat.getMontantTotal());
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    achat.setIdAchat(cles.getInt(1));
                }
            }
            return achat.getIdAchat();
        }
    }

    public boolean modifier(Achat achat) throws SQLException {
        String sql = "UPDATE ACHAT SET DATEACHAT = ?, ID_UTILISATEUR = ?, MONTANTTOTAL = ? WHERE IDACHAT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setTimestamp(1, OutilsSQL.toTimestamp(achat.getDateAchat()));
            ps.setInt(2, achat.getIdUtilisateur());
            ps.setDouble(3, achat.getMontantTotal());
            ps.setInt(4, achat.getIdAchat());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean supprimer(int id) throws SQLException {
        String sql = "DELETE FROM ACHAT WHERE IDACHAT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
