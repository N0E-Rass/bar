package Test;


import DAO.*;

/**
 * Lance ce fichier avec "Run" (sans Tomcat) pour verifier que chaque table se lit correctement.
 * [OK]     = les noms de colonnes sont bons
 * [ERREUR] = le message dit quelle colonne ou quelle table pose probleme
 */
public class TestDAO {

    interface Compteur { int compter() throws Exception; }

    private static void tester(String table, Compteur compteur) {
        try {
            System.out.println("[OK]     " + table + " : " + compteur.compter() + " ligne(s)");
        } catch (Exception e) {
            System.out.println("[ERREUR] " + table + " : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        tester("ACHAT", () -> new AchatDAO().trouverTous().size());
        tester("CLIENT", () -> new ClientDAO().trouverTous().size());
        tester("DEPENSE", () -> new DepenseDAO().trouverTous().size());
        tester("DETAIL_ACHAT", () -> new DetailAchatDAO().trouverTous().size());
        tester("DETAIL_VENTE", () -> new DetailVenteDAO().trouverTous().size());
        tester("DETTE", () -> new DetteDAO().trouverTous().size());
        tester("MOUVEMENT_CAISSE", () -> new MouvementCaisseDAO().trouverTous().size());
        tester("PAIEMENT_DETTE", () -> new PaiementDetteDAO().trouverTous().size());
        tester("PRODUIT", () -> new ProduitDAO().trouverTous().size());
        tester("STOCK", () -> new StockDAO().trouverTous().size());
        tester("UNITE", () -> new UniteDAO().trouverTous().size());
        tester("VENTE", () -> new VenteDAO().trouverTous().size());
        tester("UTILISATEUR", () -> new UtilisateurAdminDAO().trouverTous().size());
    }
}
