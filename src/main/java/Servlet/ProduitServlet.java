package Servlet;

import Modele.Produit;
import Service.ProduitService;
import bar.fenetreConnexion.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {

    private final ProduitService service = new ProduitService();

    // Si quelqu'un tape /produits dans la barre d'adresse : retour a l'accueil
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/accueil");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String racine = request.getContextPath();

        // 1. Qui est connecte ? Seul un admin peut ajouter un produit
        HttpSession session = request.getSession(false);
        Utilisateur u = (session == null) ? null : (Utilisateur) session.getAttribute("utilisateur");
        if (u == null) {
            response.sendRedirect(racine + "/connexion");
            return;
        }
        if (!u.estAdministrateur()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // 2. Lire le formulaire puis enregistrer
        try {
            String nom = request.getParameter("nom");
            String categorie = request.getParameter("categorie");
            BigDecimal prixAchat = new BigDecimal(request.getParameter("prixAchat"));
            BigDecimal prixVente = new BigDecimal(request.getParameter("prixVente"));
            int quantite = Integer.parseInt(request.getParameter("quantite"));

            String seuilTexte = request.getParameter("seuilAlerte");
            int seuil = (seuilTexte == null || seuilTexte.isEmpty()) ? 0 : Integer.parseInt(seuilTexte);

            if (nom == null || nom.trim().isEmpty()
                    || prixAchat.signum() < 0 || prixVente.signum() < 0
                    || quantite < 0 || seuil < 0) {
                response.sendRedirect(racine + "/accueil?page=ajouter&msg=erreur");
                return;
            }

            Produit produit = new Produit();
            produit.setNomProduit(nom.trim());
            produit.setCategorie(categorie);
            produit.setPrixAchat(prixAchat);
            produit.setPrixVente(prixVente);
            produit.setSeuilAlerte(seuil);
            // l'unite (IDUNITE) restera vide pour l'instant

            service.ajouterProduitAvecStock(produit, quantite);

            response.sendRedirect(racine + "/accueil?page=stock&msg=ok");

        } catch (Exception e) {
            e.printStackTrace();       // l'erreur exacte s'affiche dans la console
            response.sendRedirect(racine + "/accueil?page=ajouter&msg=erreur");
        }
    }
}