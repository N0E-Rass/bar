package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import Modele.DetailAchat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Acces a la table DETAIL_ACHAT : lire, ajouter, modifier, supprimer. */
public class DetailAchatDAO {

    private static final String COLONNES = "IDDETAILACHAT, IDACHAT, IDPRODUIT, QUANTITE, PRIXUNITAIRE";

    // Transforme la ligne courante du ResultSet en objet Java
    private DetailAchat convertir(ResultSet rs) throws SQLException {
        return new DetailAchat(
                rs.getInt("IDDETAILACHAT"),
                rs.getInt("IDACHAT"),
                rs.getInt("IDPRODUIT"),
                rs.getInt("QUANTITE"),
                rs.getDouble("PRIXUNITAIRE")
        );
    }

    public List<DetailAchat> trouverTous() throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DETAIL_ACHAT ORDER BY IDDETAILACHAT";
        List<DetailAchat> liste = new ArrayList<>();
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(convertir(rs));
            }
        }
        return liste;
    }

    public DetailAchat trouverParId(int id) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DETAIL_ACHAT WHERE IDDETAILACHAT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        }
    }

    public List<DetailAchat> trouverParIdAchat(int valeur) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DETAIL_ACHAT WHERE IDACHAT = ? ORDER BY IDDETAILACHAT";
        List<DetailAchat> liste = new ArrayList<>();
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

    public List<DetailAchat> trouverParIdProduit(int valeur) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DETAIL_ACHAT WHERE IDPRODUIT = ? ORDER BY IDDETAILACHAT";
        List<DetailAchat> liste = new ArrayList<>();
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
    public int ajouter(DetailAchat detailAchat) throws SQLException {
        String sql = "INSERT INTO DETAIL_ACHAT (IDACHAT, IDPRODUIT, QUANTITE, PRIXUNITAIRE) VALUES (?, ?, ?, ?)";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql, new String[]{"IDDETAILACHAT"})) {
            ps.setInt(1, detailAchat.getIdAchat());
            ps.setInt(2, detailAchat.getIdProduit());
            ps.setInt(3, detailAchat.getQuantite());
            ps.setDouble(4, detailAchat.getPrixUnitaire());
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    detailAchat.setIdDetailAchat(cles.getInt(1));
                }
            }
            return detailAchat.getIdDetailAchat();
        }
    }

    public boolean modifier(DetailAchat detailAchat) throws SQLException {
        String sql = "UPDATE DETAIL_ACHAT SET IDACHAT = ?, IDPRODUIT = ?, QUANTITE = ?, PRIXUNITAIRE = ? WHERE IDDETAILACHAT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, detailAchat.getIdAchat());
            ps.setInt(2, detailAchat.getIdProduit());
            ps.setInt(3, detailAchat.getQuantite());
            ps.setDouble(4, detailAchat.getPrixUnitaire());
            ps.setInt(5, detailAchat.getIdDetailAchat());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean supprimer(int id) throws SQLException {
        String sql = "DELETE FROM DETAIL_ACHAT WHERE IDDETAILACHAT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
