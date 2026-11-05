package bar.fenetreConnexion;

public class Utilisateur { private int id;
    private String nomUtilisateur;
    private String role;

    public Utilisateur(int id, String nomUtilisateur, String role) {
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public String getRole() {
        return role;
    }

    public boolean estAdministrateur() {
        return "ADMIN".equalsIgnoreCase(role);
    }
}
