package Servlet;

import DAO.ProduitDAO;
import DAO.CaisseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {

    private final ProduitDAO produitDAO = new ProduitDAO();
    private final CaisseDAO caisseDAO = new CaisseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(">>> AccueilServlet appelé");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        try {
            request.setAttribute("listeProduits", produitDAO.trouverTous());

            request.setAttribute("soldeCaisse", caisseDAO.calculerSolde());

            request.getRequestDispatcher("/acceuil.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors de la récupération des produits", e);
        }
    }
}