
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Modele.Produit" %>

<!DOCTYPE html>
<html>
<head>
    <title>Gestion des produits</title>
</head>
<body>

    <h1>Liste des produits</h1>

<%
    List<Produit> produits =
        (List<Produit>) request.getAttribute("listeProduits");
%>

<table border="1">
    <tr>
        <th>ID< /th>
        <th>Nom du produit</th>
        <th>Catégorie</th>
        <th>Prix de vente</th>
        <th>Prix d'achat</th>
        <th>Seuil d'alerte</th>
        <th>ID Unité</th>
    </tr>

    <% for (Produit p : produits) { %>
        <tr>
            <td><%= p.getIdProduit() %></td>
            <td><%= p.getNomProduit() %></td>
            <td><%= p.getCategorie() %></td>
            <td><%= p.getPrixVente() %></td>
            <td><%= p.getPrixAchat() %></td>
            <td><%= p.getSeuilAlerte() %></td>
            <td><%= p.getIdUnite() %></td>
        </tr>
    <% } %>
</table>


</body>
</html>
