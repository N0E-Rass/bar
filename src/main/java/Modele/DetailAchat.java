package Modele;

/** Represente une ligne de la table DETAIL_ACHAT. */
public class DetailAchat {

    private int idDetailAchat;
    private int idAchat;
    private int idProduit;
    private int quantite;
    private double prixUnitaire;

    public DetailAchat() {}

    public DetailAchat(int idDetailAchat, int idAchat, int idProduit, int quantite, double prixUnitaire) {
        this.idDetailAchat = idDetailAchat;
        this.idAchat = idAchat;
        this.idProduit = idProduit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public int getIdDetailAchat() { return idDetailAchat; }
    public void setIdDetailAchat(int idDetailAchat) { this.idDetailAchat = idDetailAchat; }

    public int getIdAchat() { return idAchat; }
    public void setIdAchat(int idAchat) { this.idAchat = idAchat; }

    public int getIdProduit() { return idProduit; }
    public void setIdProduit(int idProduit) { this.idProduit = idProduit; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

}
