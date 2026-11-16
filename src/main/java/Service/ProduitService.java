package Service;

import DAO.OutilsSQL;
import Modele.Produit;
import bar.fenetreConnexion.ConnexionOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ProduitService {

    // A ADAPTER : doit etre un mot qui existe deja dans la colonne TYPEMOUVEMENT de ta table STOCK
    private static final String TYPE_ENTREE = "ENTREE";

    /** Cree le produit ET son stock de depart. Tout reussit, ou rien n'est enregistre. */
    public int ajouterProduitAvecStock(Produit produit, int quantite) throws SQLException {

        String sqlProduit = "INSERT INTO PRODUIT (NOMPRODUIT, CATEGORIE, PRIXVENTE, PRIXACHAT, SEUILALERTE, IDUNITE) "+ "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlStock = "INSERT INTO STOCK (IDPRODUIT, TYPEMOUVEMENT, QUANTITE, DATEMOUVEMENT, MOTIF) " + "VALUES (?, ?, ?, ?, ?)";

        try (Connection cx = ConnexionOracle.obtenirConnexion()) {
            cx.setAutoCommit(false);          // on ne valide rien tant qu'on n'a pas tout reussi
            try {
                int idProduit;
                // 1. creer le produit
                try (PreparedStatement ps = cx.prepareStatement(sqlProduit, new String[]{"IDPRODUIT"})) {
                    ps.setString(1, produit.getNomProduit());
                    ps.setString(2, produit.getCategorie());
                    ps.setBigDecimal(3, produit.getPrixVente());
                    ps.setBigDecimal(4, produit.getPrixAchat());
                    ps.setInt(5, produit.getSeuilAlerte());
                    OutilsSQL.setIntegerOrNull(ps, 6, produit.getIdUnite());
                    ps.executeUpdate();

                    try (ResultSet cles = ps.getGeneratedKeys()) {
                        cles.next();
                        idProduit = cles.getInt(1);
                    }
                }

                // 2. enregistrer la quantite de depart
                if (quantite > 0) {
                    try (PreparedStatement ps = cx.prepareStatement(sqlStock)) {
                        ps.setInt(1, idProduit);
                        ps.setString(2, TYPE_ENTREE);
                        ps.setInt(3, quantite);
                        ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                        ps.setString(5, "Stock initial");
                        ps.executeUpdate();
                    }
                }

                cx.commit();                  // tout a marche : on valide
                return idProduit;

            } catch (SQLException e) {
                cx.rollback();                // un probleme : on annule tout
                throw e;
            }
        }
    }
}