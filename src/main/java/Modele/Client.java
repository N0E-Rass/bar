package Modele;

/** Represente une ligne de la table CLIENT. */
public class Client {

    private int idClient;
    private String nomClient;
    private String telephone;

    public Client() {}

    public Client(int idClient, String nomClient, String telephone) {
        this.idClient = idClient;
        this.nomClient = nomClient;
        this.telephone = telephone;
    }

    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }

    public String getNomClient() { return nomClient; }
    public void setNomClient(String nomClient) { this.nomClient = nomClient; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

}