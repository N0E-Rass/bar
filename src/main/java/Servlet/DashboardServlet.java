
package Servlet;

import DAO.CaisseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private CaisseDAO caisseDAO;

    @Override
    public void init() {
        caisseDAO = new CaisseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {
            BigDecimal soldeCaisse = caisseDAO.calculerSolde();

            request.setAttribute("soldeCaisse", soldeCaisse);

            request.getRequestDispatcher("/acceuil.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Erreur lors du chargement du solde de la caisse", e
            );
        }
    }
}
