package Modele;

import java.time.LocalDateTime;

/** Represente une ligne de la table MOUVEMENT_CAISSE. */
public class MouvementCaisse {

    private int idMouvement;
    private int idUtilisateur;
    private String typeMouvement;
    private double montant;
    private LocalDateTime dateMouvement;
    private String motif;

    public MouvementCaisse() {}

    public MouvementCaisse(int idMouvement, int idUtilisateur, String typeMouvement, double montant, LocalDateTime dateMouvement, String motif) {
        this.idMouvement = idMouvement;
        this.idUtilisateur = idUtilisateur;
        this.typeMouvement = typeMouvement;
        this.montant = montant;
        this.dateMouvement = dateMouvement;
        this.motif = motif;
    }

    public int getIdMouvement() { return idMouvement; }
    public void setIdMouvement(int idMouvement) { this.idMouvement = idMouvement; }

    public int getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(int idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public String getTypeMouvement() { return typeMouvement; }
    public void setTypeMouvement(String typeMouvement) { this.typeMouvement = typeMouvement; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public LocalDateTime getDateMouvement() { return dateMouvement; }
    public void setDateMouvement(LocalDateTime dateMouvement) { this.dateMouvement = dateMouvement; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

}
