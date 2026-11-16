package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import Modele.Dette;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Acces a la table DETTE : lire, ajouter, modifier, supprimer. */
public class DetteDAO {

    private static final String COLONNES = "IDDETTE, IDVENTE, IDCLIENT, IDADMIN, MONTANTINITIAL, MONTANTRESATNT";

    // Transforme la ligne courante du ResultSet en objet Java
    private Dette convertir(ResultSet rs) throws SQLException {
        return new Dette(
                rs.getInt("IDDETTE"),
                rs.getInt("IDVENTE"),
                rs.getInt("IDCLIENT"),
                rs.getInt("IDADMIN"),
                rs.getDouble("MONTANTINITIAL"),
                rs.getDouble("MONTANTRESATNT")
        );
    }

    public List<Dette> trouverTous() throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DETTE ORDER BY IDDETTE";
        List<Dette> liste = new ArrayList<>();
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(convertir(rs));
            }
        }
        return liste;
    }

    public Dette trouverParId(int id) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DETTE WHERE IDDETTE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        }
    }

    public List<Dette> trouverParIdClient(int valeur) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DETTE WHERE IDCLIENT = ? ORDER BY IDDETTE";
        List<Dette> liste = new ArrayList<>();
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

    public List<Dette> trouverParIdVente(int valeur) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM DETTE WHERE IDVENTE = ? ORDER BY IDDETTE";
        List<Dette> liste = new ArrayList<>();
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
    public int ajouter(Dette dette) throws SQLException {
        String sql = "INSERT INTO DETTE (IDVENTE, IDCLIENT, IDADMIN, MONTANTINITIAL, MONTANTRESATNT) VALUES (?, ?, ?, ?, ?)";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql, new String[]{"IDDETTE"})) {
            ps.setInt(1, dette.getIdVente());
            ps.setInt(2, dette.getIdClient());
            ps.setInt(3, dette.getIdAdmin());
            ps.setDouble(4, dette.getMontantInitial());
            ps.setDouble(5, dette.getMontantRestant());
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    dette.setIdDette(cles.getInt(1));
                }
            }
            return dette.getIdDette();
        }
    }

    public boolean modifier(Dette dette) throws SQLException {
        String sql = "UPDATE DETTE SET IDVENTE = ?, IDCLIENT = ?, IDADMIN = ?, MONTANTINITIAL = ?, MONTANTRESATNT = ? WHERE IDDETTE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, dette.getIdVente());
            ps.setInt(2, dette.getIdClient());
            ps.setInt(3, dette.getIdAdmin());
            ps.setDouble(4, dette.getMontantInitial());
            ps.setDouble(5, dette.getMontantRestant());
            ps.setInt(6, dette.getIdDette());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean supprimer(int id) throws SQLException {
        String sql = "DELETE FROM DETTE WHERE IDDETTE = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
