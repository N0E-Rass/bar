
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="sidebar">

    <h2>GESTION BAR</h2>

    <nav>
        <a href="<%= request.getContextPath() %>/dashboard.jsp">
            Tableau de bord
        </a>

        <a href="<%= request.getContextPath() %>/Produit">
            Produits
        </a>

        <a href="<%= request.getContextPath() %>/ventes.jsp">
            Ventes
        </a>

        <a href="<%= request.getContextPath() %>/achats.jsp">
            Achats
        </a>

        <a href="<%= request.getContextPath() %>/stock.jsp">
            Stock
        </a>

        <a href="<%= request.getContextPath() %>/clients.jsp">
            Clients
        </a>

        <a href="<%= request.getContextPath() %>/dettes.jsp">
            Dettes
        </a>

        <a href="<%= request.getContextPath() %>/depenses.jsp">
            Dépenses
        </a>

        <a href="<%= request.getContextPath() %>/rapports.jsp">
            Rapports
        </a>
    </nav>

</div>
