package DAO;

import bar.fenetreConnexion.ConnexionOracle;
import Modele.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Acces a la table CLIENT : lire, ajouter, modifier, supprimer. */
public class ClientDAO {

    private static final String COLONNES = "IDCLIENT, NOMCLIENT, TELEPHONE";

    // Transforme la ligne courante du ResultSet en objet Java
    private Client convertir(ResultSet rs) throws SQLException {
        return new Client(
                rs.getInt("IDCLIENT"),
                rs.getString("NOMCLIENT"),
                rs.getString("TELEPHONE")
        );
    }

    public List<Client> trouverTous() throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM CLIENT ORDER BY IDCLIENT";
        List<Client> liste = new ArrayList<>();
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(convertir(rs));
            }
        }
        return liste;
    }

    public Client trouverParId(int id) throws SQLException {
        String sql = "SELECT " + COLONNES + " FROM CLIENT WHERE IDCLIENT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? convertir(rs) : null;
            }
        }
    }

    /** Ajoute la ligne, recupere l'identifiant genere par Oracle et le met dans l'objet. */
    public int ajouter(Client client) throws SQLException {
        String sql = "INSERT INTO CLIENT (NOMCLIENT, TELEPHONE) VALUES (?, ?)";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql, new String[]{"IDCLIENT"})) {
            ps.setString(1, client.getNomClient());
            ps.setString(2, client.getTelephone());
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    client.setIdClient(cles.getInt(1));
                }
            }
            return client.getIdClient();
        }
    }

    public boolean modifier(Client client) throws SQLException {
        String sql = "UPDATE CLIENT SET NOMCLIENT = ?, TELEPHONE = ? WHERE IDCLIENT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setString(1, client.getNomClient());
            ps.setString(2, client.getTelephone());
            ps.setInt(3, client.getIdClient());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean supprimer(int id) throws SQLException {
        String sql = "DELETE FROM CLIENT WHERE IDCLIENT = ?";
        try (Connection cx = ConnexionOracle.obtenirConnexion();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}

