package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import Modele.DetailVente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Acces a la table DETAIL_VENTE : lire, ajouter, modifier, supprimer. */
public class DetailVenteDAO {

    private static final String COLONNES = "IDDETAILVENTE, IDVENTE, IDPRODUIT, QUANTITE, PRIXUNITAIRE";

    // Transforme la ligne courante du ResultSet en objet Java
    private DetailVente convertir(ResultSet rs) throws SQLException {
        return new DetailVente(
                rs.getInt("IDDETAILVENTE"),
                rs.getInt("IDVENTE"),
                rs.getInt("IDPRODUIT"),
                rs.getInt("QUANTITE"),
                rs.getDouble("PRIXUNITAIRE")
        );
    }

    public List<DetailVente> trouverTous() throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DETAIL_VENTE ORDER BY IDDETAILVENTE";
        List<DetailVente> liste = new ArrayList<>();
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(convertir(rs));
            }
        }
        return liste;
    }

    public DetailVente trouverParId(int id) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DETAIL_VENTE WHERE IDDETAILVENTE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        }
    }

    public List<DetailVente> trouverParIdVente(int valeur) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DETAIL_VENTE WHERE IDVENTE = ? ORDER BY IDDETAILVENTE";
        List<DetailVente> liste = new ArrayList<>();
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

    public List<DetailVente> trouverParIdProduit(int valeur) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DETAIL_VENTE WHERE IDPRODUIT = ? ORDER BY IDDETAILVENTE";
        List<DetailVente> liste = new ArrayList<>();
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
    public int ajouter(DetailVente detailVente) throws SQLException {
        String sql = "INSERT INTO DETAIL_VENTE (IDVENTE, IDPRODUIT, QUANTITE, PRIXUNITAIRE) VALUES (?, ?, ?, ?)";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql, new String[]{"IDDETAILVENTE"})) {
            ps.setInt(1, detailVente.getIdVente());
            ps.setInt(2, detailVente.getIdProduit());
            ps.setInt(3, detailVente.getQuantite());
            ps.setDouble(4, detailVente.getPrixUnitaire());
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    detailVente.setIdDetailVente(cles.getInt(1));
                }
            }
            return detailVente.getIdDetailVente();
        }
    }

    public boolean modifier(DetailVente detailVente) throws SQLException {
        String sql = "UPDATE DETAIL_VENTE SET IDVENTE = ?, IDPRODUIT = ?, QUANTITE = ?, PRIXUNITAIRE = ? WHERE IDDETAILVENTE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, detailVente.getIdVente());
            ps.setInt(2, detailVente.getIdProduit());
            ps.setInt(3, detailVente.getQuantite());
            ps.setDouble(4, detailVente.getPrixUnitaire());
            ps.setInt(5, detailVente.getIdDetailVente());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean supprimer(int id) throws SQLException {
        String sql = "DELETE FROM DETAIL_VENTE WHERE IDDETAILVENTE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
