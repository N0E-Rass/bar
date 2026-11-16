package Modele;


import java.time.LocalDateTime;

/** Represente une ligne de la table ACHAT. */
public class Achat {

    private int idAchat;
    private LocalDateTime dateAchat;
    private int idUtilisateur;
    private double montantTotal;

    public Achat() {}

    public Achat(int idAchat, LocalDateTime dateAchat, int idUtilisateur, double montantTotal) {
        this.idAchat = idAchat;
        this.dateAchat = dateAchat;
        this.idUtilisateur = idUtilisateur;
        this.montantTotal = montantTotal;
    }

    public int getIdAchat() { return idAchat; }
    public void setIdAchat(int idAchat) { this.idAchat = idAchat; }

    public LocalDateTime getDateAchat() { return dateAchat; }
    public void setDateAchat(LocalDateTime dateAchat) { this.dateAchat = dateAchat; }

    public int getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(int idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }

}
