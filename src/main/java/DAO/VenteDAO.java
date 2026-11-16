package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import Modele.Vente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Acces a la table VENTE : lire, ajouter, modifier, supprimer. */
public class VenteDAO {

    private static final String COLONNES = "IDVENTE, DATEVENTE, IDCLIENT, ID_UTILISATEUR, MONTANTTOTAL, MONTANTPAYE";

    // Transforme la ligne courante du ResultSet en objet Java
    private Vente convertir(ResultSet rs) throws SQLException {
        return new Vente(
                rs.getInt("IDVENTE"),
                OutilsSQL.toLocalDateTime(rs.getTimestamp("DATEVENTE")),
                OutilsSQL.getIntegerOrNull(rs, "IDCLIENT"),
                rs.getInt("ID_UTILISATEUR"),
                rs.getDouble("MONTANTTOTAL"),
                rs.getDouble("MONTANTPAYE")
        );
    }

    public List<Vente> trouverTous() throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM VENTE ORDER BY IDVENTE";
        List<Vente> liste = new ArrayList<>();
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(convertir(rs));
            }
        }
        return liste;
    }

    public Vente trouverParId(int id) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM VENTE WHERE IDVENTE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        }
    }

    public List<Vente> trouverParIdUtilisateur(int valeur) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM VENTE WHERE ID_UTILISATEUR = ? ORDER BY IDVENTE";
        List<Vente> liste = new ArrayList<>();
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
    public int ajouter(Vente vente) throws SQLException {
        String sql = "INSERT INTO VENTE (DATEVENTE, IDCLIENT, ID_UTILISATEUR, MONTANTTOTAL, MONTANTPAYE) VALUES (?, ?, ?, ?, ?)";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql, new String[]{"IDVENTE"})) {
            ps.setTimestamp(1, OutilsSQL.toTimestamp(vente.getDateVente()));
            OutilsSQL.setIntegerOrNull(ps, 2, vente.getIdClient());
            ps.setInt(3, vente.getIdUtilisateur());
            ps.setDouble(4, vente.getMontantTotal());
            ps.setDouble(5, vente.getMontantPaye());
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    vente.setIdVente(cles.getInt(1));
                }
            }
            return vente.getIdVente();
        }
    }

    public boolean modifier(Vente vente) throws SQLException {
        String sql = "UPDATE VENTE SET DATEVENTE = ?, IDCLIENT = ?, ID_UTILISATEUR = ?, MONTANTTOTAL = ?, MONTANTPAYE = ? WHERE IDVENTE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setTimestamp(1, OutilsSQL.toTimestamp(vente.getDateVente()));
            OutilsSQL.setIntegerOrNull(ps, 2, vente.getIdClient());
            ps.setInt(3, vente.getIdUtilisateur());
            ps.setDouble(4, vente.getMontantTotal());
            ps.setDouble(5, vente.getMontantPaye());
            ps.setInt(6, vente.getIdVente());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean supprimer(int id) throws SQLException {
        String sql = "DELETE FROM VENTE WHERE IDVENTE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
