package Modele;

import java.time.LocalDateTime;

/** Represente une ligne de la table DEPENSE. */
public class Depense {

    private int idDepense;
    private int idUtilisateur;
    private String libelle;
    private double montant;
    private LocalDateTime dateDepense;

    public Depense() {}

    public Depense(int idDepense, int idUtilisateur, String libelle, double montant, LocalDateTime dateDepense) {
        this.idDepense = idDepense;
        this.idUtilisateur = idUtilisateur;
        this.libelle = libelle;
        this.montant = montant;
        this.dateDepense = dateDepense;
    }

    public int getIdDepense() { return idDepense; }
    public void setIdDepense(int idDepense) { this.idDepense = idDepense; }

    public int getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(int idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public LocalDateTime getDateDepense() { return dateDepense; }
    public void setDateDepense(LocalDateTime dateDepense) { this.dateDepense = dateDepense; }

}
