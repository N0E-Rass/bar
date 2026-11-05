<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="bar.fenetreConnexion.Utilisateur"%>
<%
Utilisateur U =(Utilisateur) session.getAttribute("utilisateur");
%>
<html>
<head>
 <title> Accueil -Gestion  bar</title>
</head>
<body>
    <h1>Bienvenue <%= U.getNomUtilisateur() %> <%= U.getRole()%></h1>
    <ul>
        <li><a href="<%=request.getContextPath() %>/commande">Commandes</a></li>
        <% if (U.estAdministrateur()) { %>
                        <li><a href="<%=request.getContextPath() %>/produits">Produit et stock</a></li>
        <% } %>
        <li><a href="<%=request.getContextPath()%>/Deconnexion"> Se deconnecter </a><li>

    </ul>
</body>

</html>