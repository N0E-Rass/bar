package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import Modele.PaiementDette;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Acces a la table PAIEMENT_DETTE : lire, ajouter, modifier, supprimer. */
public class PaiementDetteDAO {

    private static final String COLONNES = "IDPAIEMENT, IDDETTE, ID_UTILISATEUR, DATEPAIEMENT, MONTAT";

    // Transforme la ligne courante du ResultSet en objet Java
    private PaiementDette convertir(ResultSet rs) throws SQLException {
        return new PaiementDette(
                rs.getInt("IDPAIEMENT"),
                rs.getInt("IDDETTE"),
                rs.getInt("ID_UTILISATEUR"),
                OutilsSQL.toLocalDateTime(rs.getTimestamp("DATEPAIEMENT")),
                rs.getDouble("MONTAT")
        );
    }

    public List<PaiementDette> trouverTous() throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM PAIEMENT_DETTE ORDER BY IDPAIEMENT";
        List<PaiementDette> liste = new ArrayList<>();
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(convertir(rs));
            }
        }
        return liste;
    }

    public PaiementDette trouverParId(int id) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM PAIEMENT_DETTE WHERE IDPAIEMENT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        }
    }

    public List<PaiementDette> trouverParIdDette(int valeur) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM PAIEMENT_DETTE WHERE IDDETTE = ? ORDER BY IDPAIEMENT";
        List<PaiementDette> liste = new ArrayList<>();
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
    public int ajouter(PaiementDette paiementDette) throws SQLException {
        String sql = "INSERT INTO PAIEMENT_DETTE (IDDETTE, ID_UTILISATEUR, DATEPAIEMENT, MONTAT) VALUES (?, ?, ?, ?)";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql, new String[]{"IDPAIEMENT"})) {
            ps.setInt(1, paiementDette.getIdDette());
            ps.setInt(2, paiementDette.getIdUtilisateur());
            ps.setTimestamp(3, OutilsSQL.toTimestamp(paiementDette.getDatePaiement()));
            ps.setDouble(4, paiementDette.getMontant());
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    paiementDette.setIdPaiement(cles.getInt(1));
                }
            }
            return paiementDette.getIdPaiement();
        }
    }

    public boolean modifier(PaiementDette paiementDette) throws SQLException {
        String sql = "UPDATE PAIEMENT_DETTE SET IDDETTE = ?, ID_UTILISATEUR = ?, DATEPAIEMENT = ?, MONTAT = ? WHERE IDPAIEMENT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, paiementDette.getIdDette());
            ps.setInt(2, paiementDette.getIdUtilisateur());
            ps.setTimestamp(3, OutilsSQL.toTimestamp(paiementDette.getDatePaiement()));
            ps.setDouble(4, paiementDette.getMontant());
            ps.setInt(5, paiementDette.getIdPaiement());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean supprimer(int id) throws SQLException {
        String sql = "DELETE FROM PAIEMENT_DETTE WHERE IDPAIEMENT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
