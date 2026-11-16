
package Modele;
import java.math.BigDecimal;

public class Produit {

    private int idProduit;
    private String nomProduit;
    private String categorie;
    private BigDecimal prixVente;
    private BigDecimal prixAchat;
    private int seuilAlerte;
    private Integer idUnite;
    private int quantiteEntree;
    private int quantiteSortie;
    private int quantiteDisponible;


    // Constructeur vide
    public Produit() {
    }

    // Constructeur avec tous les attributs
    public Produit(int idProduit, String nomProduit,
                   String categorie, BigDecimal prixVente,
                   BigDecimal prixAchat, int seuilAlerte,
                   Integer idUnite) {

        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.categorie = categorie;
        this.prixVente = prixVente;
        this.prixAchat = prixAchat;
        this.seuilAlerte = seuilAlerte;
        this.idUnite = idUnite;


    }

    // Getters
    public int getIdProduit() {
        return idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public String getCategorie() {
        return categorie;
    }

    public BigDecimal getPrixVente() {
        return prixVente;
    }

    public BigDecimal getPrixAchat() {
        return prixAchat;
    }

    public int getSeuilAlerte() {
        return seuilAlerte;
    }

    public Integer getIdUnite() {
        return idUnite;
    }

    // Setters
    public void setIdProduit(int idProduit) {

        this.idProduit = idProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public void setCategorie(String categorie) {

        this.categorie = categorie;
    }

    public void setPrixVente(BigDecimal prixVente) {

        this.prixVente = prixVente;
    }

    public void setPrixAchat(BigDecimal prixAchat) {

        this.prixAchat = prixAchat;
    }

    public void setSeuilAlerte(int seuilAlerte) {

        this.seuilAlerte = seuilAlerte;
    }

    public void setIdUnite(Integer idUnite) {

        this.idUnite = idUnite;
    }
    public int getQuantiteEntree() {
        return quantiteEntree;
    }

    public void setQuantiteEntree(int quantiteEntree) {
        this.quantiteEntree = quantiteEntree;
    }

    public int getQuantiteSortie() {
        return quantiteSortie;
    }

    public void setQuantiteSortie(int quantiteSortie) {
        this.quantiteSortie = quantiteSortie;
    }

    public int getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public void setQuantiteDisponible(int quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }
}
