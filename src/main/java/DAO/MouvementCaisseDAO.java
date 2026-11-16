package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import Modele.MouvementCaisse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Acces a la table MOUVEMENT_CAISSE : lire, ajouter, modifier, supprimer. */
public class MouvementCaisseDAO {

    private static final String COLONNES = "IDMOUVEMENT, ID_UTILISATEUR, TYPEMOUVEMENT, MONTANT, DATEMOUVEMENT, MOTIF";

    // Transforme la ligne courante du ResultSet en objet Java
    private MouvementCaisse convertir(ResultSet rs) throws SQLException {
        return new MouvementCaisse(
                rs.getInt("IDMOUVEMENT"),
                rs.getInt("ID_UTILISATEUR"),
                rs.getString("TYPEMOUVEMENT"),
                rs.getDouble("MONTANT"),
                OutilsSQL.toLocalDateTime(rs.getTimestamp("DATEMOUVEMENT")),
                rs.getString("MOTIF")
        );
    }

    public List<MouvementCaisse> trouverTous() throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM MOUVEMENT_CAISSE ORDER BY IDMOUVEMENT";
        List<MouvementCaisse> liste = new ArrayList<>();
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(convertir(rs));
            }
        }
        return liste;
    }

    public MouvementCaisse trouverParId(int id) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM MOUVEMENT_CAISSE WHERE IDMOUVEMENT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        }
    }

    public List<MouvementCaisse> trouverParIdUtilisateur(int valeur) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM MOUVEMENT_CAISSE WHERE ID_UTILISATEUR = ? ORDER BY IDMOUVEMENT";
        List<MouvementCaisse> liste = new ArrayList<>();
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
    public int ajouter(MouvementCaisse mouvementCaisse) throws SQLException {
        String sql = "INSERT INTO MOUVEMENT_CAISSE (ID_UTILISATEUR, TYPEMOUVEMENT, MONTANT, DATEMOUVEMENT, MOTIF) VALUES (?, ?, ?, ?, ?)";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql, new String[]{"IDMOUVEMENT"})) {
            ps.setInt(1, mouvementCaisse.getIdUtilisateur());
            ps.setString(2, mouvementCaisse.getTypeMouvement());
            ps.setDouble(3, mouvementCaisse.getMontant());
            ps.setTimestamp(4, OutilsSQL.toTimestamp(mouvementCaisse.getDateMouvement()));
            ps.setString(5, mouvementCaisse.getMotif());
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    mouvementCaisse.setIdMouvement(cles.getInt(1));
                }
            }
            return mouvementCaisse.getIdMouvement();
        }
    }

    public boolean modifier(MouvementCaisse mouvementCaisse) throws SQLException {
        String sql = "UPDATE MOUVEMENT_CAISSE SET ID_UTILISATEUR = ?, TYPEMOUVEMENT = ?, MONTANT = ?, DATEMOUVEMENT = ?, MOTIF = ? WHERE IDMOUVEMENT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, mouvementCaisse.getIdUtilisateur());
            ps.setString(2, mouvementCaisse.getTypeMouvement());
            ps.setDouble(3, mouvementCaisse.getMontant());
            ps.setTimestamp(4, OutilsSQL.toTimestamp(mouvementCaisse.getDateMouvement()));
            ps.setString(5, mouvementCaisse.getMotif());
            ps.setInt(6, mouvementCaisse.getIdMouvement());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean supprimer(int id) throws SQLException {
        String sql = "DELETE FROM MOUVEMENT_CAISSE WHERE IDMOUVEMENT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
