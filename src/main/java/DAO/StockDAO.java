package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import Modele.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Acces a la table STOCK : lire, ajouter, modifier, supprimer. */
public class StockDAO {

    private static final String COLONNES = "IDSTOCK, IDPRODUIT, TYPEMOUVEMENT, QUANTITE, DATEMOUVEMENT, MOTIF";

    // Transforme la ligne courante du ResultSet en objet Java
    private Stock convertir(ResultSet rs) throws SQLException {
        return new Stock(
                rs.getInt("IDSTOCK"),
                rs.getInt("IDPRODUIT"),
                rs.getString("TYPEMOUVEMENT"),
                rs.getInt("QUANTITE"),
                OutilsSQL.toLocalDateTime(rs.getTimestamp("DATEMOUVEMENT")),
                rs.getString("MOTIF")
        );
    }

    public List<Stock> trouverTous() throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM STOCK ORDER BY IDSTOCK";
        List<Stock> liste = new ArrayList<>();
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(convertir(rs));
            }
        }
        return liste;
    }

    public Stock trouverParId(int id) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM STOCK WHERE IDSTOCK = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        }
    }

    public List<Stock> trouverParIdProduit(int valeur) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM STOCK WHERE IDPRODUIT = ? ORDER BY IDSTOCK";
        List<Stock> liste = new ArrayList<>();
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
    public int ajouter(Stock stock) throws SQLException {
        String sql = "INSERT INTO STOCK (IDPRODUIT, TYPEMOUVEMENT, QUANTITE, DATEMOUVEMENT, MOTIF) VALUES (?, ?, ?, ?, ?)";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql, new String[]{"IDSTOCK"})) {
            ps.setInt(1, stock.getIdProduit());
            ps.setString(2, stock.getTypeMouvement());
            ps.setInt(3, stock.getQuantite());
            ps.setTimestamp(4, OutilsSQL.toTimestamp(stock.getDateMouvement()));
            ps.setString(5, stock.getMotif());
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    stock.setIdStock(cles.getInt(1));
                }
            }
            return stock.getIdStock();
        }
    }

    public boolean modifier(Stock stock) throws SQLException {
        String sql = "UPDATE STOCK SET IDPRODUIT = ?, TYPEMOUVEMENT = ?, QUANTITE = ?, DATEMOUVEMENT = ?, MOTIF = ? WHERE IDSTOCK = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, stock.getIdProduit());
            ps.setString(2, stock.getTypeMouvement());
            ps.setInt(3, stock.getQuantite());
            ps.setTimestamp(4, OutilsSQL.toTimestamp(stock.getDateMouvement()));
            ps.setString(5, stock.getMotif());
            ps.setInt(6, stock.getIdStock());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean supprimer(int id) throws SQLException {
        String sql = "DELETE FROM STOCK WHERE IDSTOCK = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ATTENTION : adapte ces deux valeurs a ce que tu as reellement mis dans la colonne TYPEMOUVEMENT
    private static final String TYPE_ENTREE = "ENTREE";
    private static final String TYPE_SORTIE = "SORTIE";

    /** Quantite actuelle d'un produit = total des entrees - total des sorties. */
    public int quantiteEnStock(int idProduit) throws SQLException {
        String sql = "SELECT NVL(SUM(CASE WHEN UPPER(TYPEMOUVEMENT) = ? THEN QUANTITE " +
                "WHEN UPPER(TYPEMOUVEMENT) = ? THEN -QUANTITE ELSE 0 END), 0) " +
                "FROM STOCK WHERE IDPRODUIT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setString(1, TYPE_ENTREE);
            ps.setString(2, TYPE_SORTIE);
            ps.setInt(3, idProduit);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
}
