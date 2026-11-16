package Servlet;

import DAO.UtilisateurDAO;
import bar.fenetreConnexion.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {

    private final UtilisateurDAO dao = new UtilisateurDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/connexion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String nom = req.getParameter("nom");
        String motDePasse = req.getParameter("motdepasse");

        if (nom == null || nom.trim().isEmpty() || motDePasse == null || motDePasse.isEmpty()) {
            req.setAttribute("message", "Veuillez remplir tous les champs.");
            req.getRequestDispatcher("/connexion.jsp").forward(req, resp);
            return;
        }

        try {
            Utilisateur u = dao.authentifier(nom.trim(), motDePasse);

            if (u != null) {
                System.out.println("[CONNEXION] SUCCES : " + u.getNomUtilisateur() + " / " + u.getRole());
                HttpSession ancienne = req.getSession(false);
                if (ancienne != null) ancienne.invalidate();
                req.getSession(true).setAttribute("utilisateur", u);
                resp.sendRedirect(req.getContextPath() + "/accueil");
            } else {
                System.out.println("[CONNEXION] ECHEC : nom introuvable ou mot de passe different");
                req.setAttribute("message", "Nom d'utilisateur ou mot de passe incorrect.");
                req.getRequestDispatcher("/connexion.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            System.out.println("[CONNEXION] ERREUR ORACLE : " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("message", "Erreur technique, réessayez plus tard.");
            req.getRequestDispatcher("/connexion.jsp").forward(req, resp);
        }
    }
}