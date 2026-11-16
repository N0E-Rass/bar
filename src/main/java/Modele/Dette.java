
package Modele;

/** Represente une ligne de la table DETTE. */
public class Dette {

    private int idDette;
    private int idVente;
    private int idClient;
    private int idAdmin;
    private double montantInitial;
    private double montantRestant;

    public Dette() {}

    public Dette(int idDette, int idVente, int idClient, int idAdmin, double montantInitial, double montantRestant) {
        this.idDette = idDette;
        this.idVente = idVente;
        this.idClient = idClient;
        this.idAdmin = idAdmin;
        this.montantInitial = montantInitial;
        this.montantRestant = montantRestant;
    }

    public int getIdDette() { return idDette; }
    public void setIdDette(int idDette) { this.idDette = idDette; }

    public int getIdVente() { return idVente; }
    public void setIdVente(int idVente) { this.idVente = idVente; }

    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }

    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }

    public double getMontantInitial() { return montantInitial; }
    public void setMontantInitial(double montantInitial) { this.montantInitial = montantInitial; }

    public double getMontantRestant() { return montantRestant; }
    public void setMontantRestant(double montantRestant) { this.montantRestant = montantRestant; }

}

