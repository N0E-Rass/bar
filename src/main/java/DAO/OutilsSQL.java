package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

/** Petites methodes utilitaires partagees par tous les DAO. */
public class OutilsSQL {

    public static LocalDateTime toLocalDateTime(Timestamp t) {
        return t == null ? null : t.toLocalDateTime();
    }

    public static Timestamp toTimestamp(LocalDateTime d) {
        return d == null ? null : Timestamp.valueOf(d);
    }

    // Lit un nombre qui peut etre NULL dans la base (retourne null au lieu de 0)
    public static Integer getIntegerOrNull(ResultSet rs, String colonne) throws SQLException {
        int valeur = rs.getInt(colonne);
        return rs.wasNull() ? null : valeur;
    }

    // Ecrit un nombre qui peut etre NULL dans la base
    public static void setIntegerOrNull(PreparedStatement ps, int index, Integer valeur) throws SQLException {
        if (valeur == null) {
            ps.setNull(index, Types.NUMERIC);
        } else {
            ps.setInt(index, valeur);
        }
    }
}
