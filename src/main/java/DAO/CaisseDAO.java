
package DAO;

import bar.fenetreConnexion.ConnexionOracle;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CaisseDAO {

    // Calculer le montant disponible dans la caisse
    public BigDecimal calculerSolde() throws SQLException {

        String sql = """
            SELECT NVL(
                 SUM(
                     CASE
                        WHEN UPPER(TRIM(TYPEMOUVEMENT)) IN ('ENTREE', 'ENTRER')
                            THEN MONTANT
                        WHEN UPPER(TRIM(TYPEMOUVEMENT)) IN ('SORTIE', 'SORTIR')
                            THEN -MONTANT
                        ELSE 0
                    END
                ), 0
            ) AS SOLDE
            FROM MOUVEMENT_CAISSE
            """;

        try (
                Connection cx = ConnexionOracle.obtenirConnexion();
                PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getBigDecimal("SOLDE");
            }
        }

        return BigDecimal.ZERO;
    }
}
