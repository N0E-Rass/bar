package Modele;

import java.time.LocalDateTime;

/** Represente une ligne de la table VENTE. */
public class Vente {

    private int idVente;
    private LocalDateTime dateVente;
    private Integer idClient;
    private int idUtilisateur;
    private double montantTotal;
    private double montantPaye;

    public Vente() {}

    public Vente(int idVente, LocalDateTime dateVente, Integer idClient, int idUtilisateur, double montantTotal, double montantPaye) {
        this.idVente = idVente;
        this.dateVente = dateVente;
        this.idClient = idClient;
        this.idUtilisateur = idUtilisateur;
        this.montantTotal = montantTotal;
        this.montantPaye = montantPaye;
    }

    public int getIdVente() { return idVente; }
    public void setIdVente(int idVente) { this.idVente = idVente; }

    public LocalDateTime getDateVente() { return dateVente; }
    public void setDateVente(LocalDateTime dateVente) { this.dateVente = dateVente; }

    public Integer getIdClient() { return idClient; }
    public void setIdClient(Integer idClient) { this.idClient = idClient; }

    public int getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(int idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }

    public double getMontantPaye() { return montantPaye; }
    public void setMontantPaye(double montantPaye) { this.montantPaye = montantPaye; }

}
