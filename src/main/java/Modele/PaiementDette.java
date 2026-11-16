package Modele;

import java.time.LocalDateTime;

/** Represente une ligne de la table PAIEMENT_DETTE. */
public class PaiementDette {

    private int idPaiement;
    private int idDette;
    private int idUtilisateur;
    private LocalDateTime datePaiement;
    private double montant;

    public PaiementDette() {}

    public PaiementDette(int idPaiement, int idDette, int idUtilisateur, LocalDateTime datePaiement, double montant) {
        this.idPaiement = idPaiement;
        this.idDette = idDette;
        this.idUtilisateur = idUtilisateur;
        this.datePaiement = datePaiement;
        this.montant = montant;
    }

    public int getIdPaiement() { return idPaiement; }
    public void setIdPaiement(int idPaiement) { this.idPaiement = idPaiement; }

    public int getIdDette() { return idDette; }
    public void setIdDette(int idDette) { this.idDette = idDette; }

    public int getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(int idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

}
