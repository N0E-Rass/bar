
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, Modele.Produit" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Gestion de Bar</title>

    <!-- Liaison avec le fichier CSS -->
    <link rel="stylesheet" href="css/style_Acceuil.css">

    <!-- MODIFICATION : styles pour l'état du stock -->
    <style>
        .stock-faible {
            color: red;
            font-weight: bold;
        }

        .stock-disponible {
            color: green;
            font-weight: bold;
        }
    </style>
</head>

<body>

<div class="container">

    <!-- =========================
         MENU GAUCHE
    ========================== -->
    <aside class="sidebar">

        <h2> BAR NOELA</h2>

        <nav>
            <a href="#" onclick="showPage('dashboard')">
                📊 Dashboard
            </a>

            <a href="#" onclick="showPage('stock')">
                📦 Stock
            </a>

            <a href="#" onclick="showPage('ajouter')">
                ➕ Ajouter stock
            </a>

            <a href="#" onclick="showPage('achat')">
                🛒 Achats
            </a>

            <a href="#" onclick="showPage('vente')">
                💰 Ventes
            </a>

            <a href="#" onclick="showPage('historique')">
                📋 Historique
            </a>
        </nav>

        <div class="sidebar-bottom">
            <a href="connexion.jsp">
                🚪 Déconnexion
            </a>
        </div>

    </aside>


    <!-- =========================
         CONTENU PRINCIPAL
    ========================== -->

    <main class="main">

        <!-- HEADER -->
        <header>
            <div>
                <h1 id="pageTitle">Dashboard</h1>
                <p>Gestion de votre bar</p>
            </div>

            <div class="user">
                👤 Administrateur
            </div>
        </header>


        <!-- =========================
             DASHBOARD
        ========================== -->

        <section id="dashboard" class="page active">

            <div class="cards">

                <div class="card">
                    <div class="icon">📦</div>
                    <div>
                        <p>Total produits</p>
                        <h2 id="totalProduits">0</h2>
                    </div>
                </div>

                <div class="card">
                    <div class="icon">📊</div>
                    <div>
                        <p>Quantité en stock</p>
                        <h2 id="totalStock">0</h2>
                    </div>
                </div>

                <div class="card">
                    <div class="icon">💰</div>
                    <div>
                        <p>Ventes</p>
                        <h2 id="totalVentes">0 Ar</h2>
                    </div>
                </div>

                <div class="card">
                    <div class="icon">🛒</div>
                    <div>
                        <p>Achats</p>
                        <h2 id="totalAchats">0 Ar</h2>
                    </div>
                </div>
                <!-- CARTE CAISSE -->
                <div class="card caisse-card">
                   <div class="icon">💵</div>
                    <div>
                       <p>Argent disponible en caisse</p>
                       <h2 id="soldeCaisse"><h2 id="soldeCaisse">
                                                <%= request.getAttribute("soldeCaisse") != null
                                                    ? request.getAttribute("soldeCaisse") : 0 %> Ar
                                            </h2>
                    </div>
                </div>

            </div>

            <div class="dashboard-box">
                <h2>Bienvenue dans votre système de gestion</h2>
                <p>
                    Utilisez le menu à gauche pour gérer les produits,
                    les stocks, les achats et les ventes.
                </p>
            </div>

        </section>


        <!-- =========================
             PAGE STOCK
        ========================== -->

        <p>
            Nombre de produits reçus :
            <%= request.getAttribute("listeProduits") == null ? "null"
                : ((List<Produit>) request.getAttribute("listeProduits")).size() %>
        </p>

        <section id="stock" class="page">

            <div class="section-header">

                <h2>📦 Gestion du stock</h2>

                <input
                    type="text"
                    id="searchStock"
                    placeholder="🔍 Rechercher un produit..."
                    onkeyup="searchProduct()">

            </div>

            <!-- MODIFICATION : ajout des colonnes des quantités et de l'état -->
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Produit</th>
                        <th>Catégorie</th>
                        <th>Prix achat</th>
                        <th>Prix vente</th>
                        <th>Seuil d'alerte</th>

                        <!-- NOUVELLES COLONNES -->
                        <th>Quantité entrée</th>
                        <th>Quantité sortie</th>
                        <th>Quantité disponible</th>
                        <th>État</th>
                    </tr>
                </thead>

                <tbody id="stockTable">

                    <%
                        List<Produit> listeProduits =
                            (List<Produit>) request.getAttribute("listeProduits");

                        if (listeProduits != null) {
                            for (Produit produit : listeProduits) {
                    %>

                        <tr>
                            <td><%= produit.getIdProduit() %></td>
                            <td><%= produit.getNomProduit() %></td>
                            <td><%= produit.getCategorie() %></td>
                            <td><%= produit.getPrixAchat() %> Ar</td>
                            <td><%= produit.getPrixVente() %> Ar</td>
                            <td><%= produit.getSeuilAlerte() %></td>

                            <!-- MODIFICATION : affichage des quantités récupérées depuis ProduitDAO -->
                            <td><%= produit.getQuantiteEntree() %></td>
                            <td><%= produit.getQuantiteSortie() %></td>
                            <td><%= produit.getQuantiteDisponible() %></td>

                            <!-- MODIFICATION : état calculé selon le seuil d'alerte -->
                            <td>
                                <%
                                    if (produit.getQuantiteDisponible()
                                            <= produit.getSeuilAlerte()) {
                                %>
                                    <span class="stock-faible">Stock faible</span>
                                <%
                                    } else {
                                %>
                                    <span class="stock-disponible">Disponible</span>
                                <%
                                    }
                                %>
                            </td>
                        </tr>

                    <%
                            }
                        }
                    %>

                </tbody>
            </table>

        </section>


        <!-- =========================
             AJOUTER STOCK
        ========================== -->

        <section id="ajouter" class="page">

            <div class="form-container">

                <h2>➕ Ajouter un produit</h2>

                <form id="productForm"
                      method="post"
                      action="<%= request.getContextPath() %>/produits">

                    <label>Nom du produit</label>
                    <input type="text"
                           id="nom"
                           name="nom"
                           placeholder="Ex: Coca Cola"
                           required>

                    <label>Catégorie</label>
                    <select id="categorie" name="categorie" required>
                        <option value="Boisson gazeuse">Boisson gazeuse</option>
                        <option value="Bière">Bière</option>
                        <option value="Jus">Jus</option>
                        <option value="Eau">Eau</option>
                        <option value="Alcool">Alcool</option>
                        <option value="Autre">Autre</option>
                    </select>

                    <label>Prix d'achat</label>
                    <input type="number"
                           id="prixAchat"
                           name="prixAchat"
                           min="0"
                           step="0.01"
                           placeholder="Ex: 2500"
                           required>

                    <label>Prix de vente</label>
                    <input type="number"
                           id="prixVente"
                           name="prixVente"
                           min="0"
                           step="0.01"
                           placeholder="Ex: 3500"
                           required>

                    <label>Quantité initiale</label>
                    <input type="number"
                           id="quantite"
                           name="quantite"
                           min="0"
                           placeholder="Ex: 20"
                           required>

                    <label>Seuil d'alerte</label>
                    <input type="number"
                           id="seuilAlerte"
                           name="seuilAlerte"
                           min="0"
                           value="5"
                           required>

                    <button type="submit">
                        ➕ Ajouter au stock
                    </button>

                </form>

            </div>

        </section>


        <!-- =========================
             ACHATS
        ========================== -->

        <section id="achat" class="page">

            <div class="form-container">

                <h2>🛒 Enregistrer un achat</h2>

                <form id="achatForm">

                    <label>Produit</label>
                    <select id="achatProduit"></select>

                    <label>Quantité achetée</label>
                    <input type="number"
                           id="achatQuantite"
                           min="1"
                           required>

                    <label>Prix total</label>
                    <input type="number"
                           id="achatPrix"
                           min="0"
                           required>

                    <button type="submit">
                        🛒 Enregistrer l'achat
                    </button>

                </form>

            </div>

        </section>


        <!-- =========================
             VENTES
        ========================== -->

        <section id="vente" class="page">

            <div class="form-container">

                <h2>💰 Enregistrer une vente</h2>

                <form id="venteForm">

                    <label>Produit</label>
                    <select id="venteProduit"></select>

                    <label>Quantité vendue</label>
                    <input type="number"
                           id="venteQuantite"
                           min="1"
                           required>

                    <label>Prix total</label>
                    <input type="number"
                           id="ventePrix"
                           min="0"
                           required>

                    <button type="submit">
                        💰 Enregistrer la vente
                    </button>

                </form>

            </div>

        </section>


        <!-- =========================
             HISTORIQUE
        ========================== -->

        <section id="historique" class="page">

            <h2>📋 Historique des opérations</h2>

            <table>

                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Type</th>
                        <th>Produit</th>
                        <th>Quantité</th>
                        <th>Montant</th>
                    </tr>
                </thead>

                <tbody id="historiqueTable">
                </tbody>

            </table>

        </section>

    </main>

</div>


<!-- JavaScript -->
<script src="script.js"></script>

</body>
</html>
