package Modele;

import java.time.LocalDateTime;

/** Represente une ligne de la table STOCK. */
public class Stock {

    private int idStock;
    private int idProduit;
    private String typeMouvement;
    private int quantite;
    private LocalDateTime dateMouvement;
    private String motif;

    public Stock() {}

    public Stock(int idStock, int idProduit, String typeMouvement, int quantite, LocalDateTime dateMouvement, String motif) {
        this.idStock = idStock;
        this.idProduit = idProduit;
        this.typeMouvement = typeMouvement;
        this.quantite = quantite;
        this.dateMouvement = dateMouvement;
        this.motif = motif;
    }

    public int getIdStock() { return idStock; }
    public void setIdStock(int idStock) { this.idStock = idStock; }

    public int getIdProduit() { return idProduit; }
    public void setIdProduit(int idProduit) { this.idProduit = idProduit; }

    public String getTypeMouvement() { return typeMouvement; }
    public void setTypeMouvement(String typeMouvement) { this.typeMouvement = typeMouvement; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public LocalDateTime getDateMouvement() { return dateMouvement; }
    public void setDateMouvement(LocalDateTime dateMouvement) { this.dateMouvement = dateMouvement; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

}
