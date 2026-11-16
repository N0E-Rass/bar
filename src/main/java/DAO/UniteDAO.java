package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import Modele.Unite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Acces a la table UNITE : lire, ajouter, modifier, supprimer. */
public class UniteDAO {

    private static final String COLONNES = "IDUNITE, NOMUNITE";

    // Transforme la ligne courante du ResultSet en objet Java
    private Unite convertir(ResultSet rs) throws SQLException {
        return new Unite(
                rs.getInt("IDUNITE"),
                rs.getString("NOMUNITE")
        );
    }

    public List<Unite> trouverTous() throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM UNITE ORDER BY IDUNITE";
        List<Unite> liste = new ArrayList<>();
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(convertir(rs));
            }
        }
        return liste;
    }

    public Unite trouverParId(int id) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM UNITE WHERE IDUNITE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        }
    }

    /** Ajoute la ligne, recupere l'identifiant genere par Oracle et le met dans l'objet. */
    public int ajouter(Unite unite) throws SQLException {
        String sql = "INSERT INTO UNITE (NOMUNITE) VALUES (?)";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql, new String[]{"IDUNITE"})) {
            ps.setString(1, unite.getNomUnite());
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    unite.setIdUnite(cles.getInt(1));
                }
            }
            return unite.getIdUnite();
        }
    }

    public boolean modifier(Unite unite) throws SQLException {
        String sql = "UPDATE UNITE SET NOMUNITE = ? WHERE IDUNITE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setString(1, unite.getNomUnite());
            ps.setInt(2, unite.getIdUnite());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean supprimer(int id) throws SQLException {
        String sql = "DELETE FROM UNITE WHERE IDUNITE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

}
