package bar.fenetreConnexion;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class Autrefiltre implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String chemin = req.getRequestURI().substring(req.getContextPath().length());
        boolean pagePublique = chemin.equals("/connexion") || chemin.equals("/connexion.jsp")
                || chemin.startsWith("/css/") || chemin.startsWith("/js/")
                || chemin.startsWith("/images/") || chemin.startsWith("/Inscription");

        HttpSession session = req.getSession(false);
        boolean connecte = session != null && session.getAttribute("utilisateur") != null;

        if (pagePublique || connecte) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/connexion");
        }
    }
}