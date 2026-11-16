
package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import Modele.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO {

    // Requete qui recupere les produits et leurs mouvements de stock
    private static final String SQL_SELECT =
            "SELECT p.IDPRODUIT, p.NOMPRODUIT, p.CATEGORIE, " +
                    "p.PRIXVENTE, p.PRIXACHAT, p.SEUILALERTE, p.IDUNITE, " +
                    "NVL(s.QUANTITEENTREE, 0) AS QUANTITEENTREE, " +
                    "NVL(s.QUANTITESORTIE, 0) AS QUANTITESORTIE, " +
                    "(NVL(s.QUANTITEENTREE, 0) - NVL(s.QUANTITESORTIE, 0)) AS QUANTITEDISPONIBLE " +
                    "FROM PRODUIT p " +
                    "LEFT JOIN ( " +
                    "   SELECT IDPRODUIT, " +
                    "   SUM(CASE WHEN UPPER(TRIM(TYPEMOUVEMENT)) IN ('ENTRER', 'ENTREE') " +
                    "       THEN QUANTITE ELSE 0 END) AS QUANTITEENTREE, " +
                    "   SUM(CASE WHEN UPPER(TRIM(TYPEMOUVEMENT)) IN ('SORTIR', 'SORTIE') " +
                    "       THEN QUANTITE ELSE 0 END) AS QUANTITESORTIE " +
                    "   FROM STOCK " +
                    "   GROUP BY IDPRODUIT " +
                    ") s ON p.IDPRODUIT = s.IDPRODUIT ";

    // Convertir une ligne SQL en objet Produit
    private Produit convertir(ResultSet rs) throws SQLException {

        Produit produit = new Produit(
                rs.getInt("IDPRODUIT"),
                rs.getString("NOMPRODUIT"),
                rs.getString("CATEGORIE"),
                rs.getBigDecimal("PRIXVENTE"),
                rs.getBigDecimal("PRIXACHAT"),
                rs.getInt("SEUILALERTE"),
                OutilsSQL.getIntegerOrNull(rs, "IDUNITE")
        );

        // Recuperer les quantites calculees
        produit.setQuantiteEntree(rs.getInt("QUANTITEENTREE"));
        produit.setQuantiteSortie(rs.getInt("QUANTITESORTIE"));
        produit.setQuantiteDisponible(rs.getInt("QUANTITEDISPONIBLE"));

        return produit;
    }

    // Recuperer tous les produits avec leurs quantites
    public List<Produit> trouverTous() throws SQLException {

        String sql = SQL_SELECT + " ORDER BY p.IDPRODUIT";

        List<Produit> liste = new ArrayList<>();

        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(convertir(rs));
            }
        }

        return liste;
    }

    // Recuperer un produit par son identifiant
    public Produit trouverParId(int id) throws SQLException {

        String sql = SQL_SELECT + " WHERE p.IDPRODUIT = ?";

        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        }
    }

    public int ajouter(Produit produit) throws SQLException {

        String sql = "INSERT INTO PRODUIT " +
                "(NOMPRODUIT, CATEGORIE, PRIXVENTE, PRIXACHAT, SEUILALERTE, IDUNITE) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql, new String[]{"IDPRODUIT"})) {

            ps.setString(1, produit.getNomProduit());
            ps.setString(2, produit.getCategorie());
            ps.setBigDecimal(3, produit.getPrixVente());
            ps.setBigDecimal(4, produit.getPrixAchat());
            ps.setInt(5, produit.getSeuilAlerte());
            OutilsSQL.setIntegerOrNull(ps, 6, produit.getIdUnite());

            ps.executeUpdate();

            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    produit.setIdProduit(cles.getInt(1));
                }
            }

            return produit.getIdProduit();
        }
    }

    public boolean modifier(Produit produit) throws SQLException {

        String sql = "UPDATE PRODUIT SET NOMPRODUIT = ?, CATEGORIE = ?, " +
                "PRIXVENTE = ?, PRIXACHAT = ?, SEUILALERTE = ?, IDUNITE = ? " +
                "WHERE IDPRODUIT = ?";

        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setString(1, produit.getNomProduit());
            ps.setString(2, produit.getCategorie());
            ps.setBigDecimal(3, produit.getPrixVente());
            ps.setBigDecimal(4, produit.getPrixAchat());
            ps.setInt(5, produit.getSeuilAlerte());
            OutilsSQL.setIntegerOrNull(ps, 6, produit.getIdUnite());
            ps.setInt(7, produit.getIdProduit());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean supprimer(int id) throws SQLException {

        String sql = "DELETE FROM PRODUIT WHERE IDPRODUIT = ?";

        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    public static void main(String[] args) {

        try {
            ProduitDAO dao = new ProduitDAO();

            List<Produit> liste = dao.trouverTous();

            System.out.println("Nombre de produits : " + liste.size());

            for (Produit p : liste) {
                System.out.println(
                        p.getNomProduit()
                                + " | Entree : " + p.getQuantiteEntree()
                                + " | Sortie : " + p.getQuantiteSortie()
                                + " | Disponible : " + p.getQuantiteDisponible()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
