// ========================================
// LISTE DES PRODUITS
// ========================================



// ========================================
// CHANGER DE PAGE
// ========================================

function showPage(page) {

    // Cacher toutes les pages
    let pages = document.querySelectorAll(".page");

    pages.forEach(function(p) {
        p.classList.remove("active");
    });


    // Afficher la page demandée
    document.getElementById(page).classList.add("active");


    // Modifier le titre
    let titres = {
        dashboard: "Dashboard",
        stock: "Stock",
        ajouter: "Ajouter stock",
        achat: "Achats",
        vente: "Ventes",
        historique: "Historique"
    };

    document.getElementById("pageTitle").textContent =
        titres[page];


}



// ========================================
// AFFICHER LE STOCK
// ========================================

function afficherStock() {

}



// ========================================
// AJOUTER UN PRODUIT
// =======================================



// ========================================
// METTRE À JOUR LE DASHBOARD
// ========================================

function mettreAJourDashboard() {

}



// ========================================
// REMPLIR LES SELECTS
// ========================================

function remplirSelects() {

    let achatSelect =
        document.getElementById("achatProduit");

    let venteSelect =
        document.getElementById("venteProduit");


    achatSelect.innerHTML = "";

    venteSelect.innerHTML = "";


    produits.forEach(function(produit) {

        let optionAchat =
            document.createElement("option");

        optionAchat.value = produit.id;

        optionAchat.textContent = produit.nom;

        achatSelect.appendChild(optionAchat);


        let optionVente =
            document.createElement("option");

        optionVente.value = produit.id;

        optionVente.textContent = produit.nom;

        venteSelect.appendChild(optionVente);

    });
}



// ========================================
// ENREGISTRER UN ACHAT
// ========================================

document.getElementById("achatForm").addEventListener(
    "submit",
    function(event) {

        event.preventDefault();


        let id =
            Number(document.getElementById("achatProduit").value);

        let quantite =
            Number(document.getElementById("achatQuantite").value);

        let prix =
            Number(document.getElementById("achatPrix").value);


        let produit =
            produits.find(p => p.id === id);


        if (produit) {

            // Ajouter au stock
            produit.stock += quantite;


            // Ajouter dans historique
            historique.push({

                date: new Date().toLocaleString(),

                type: "Achat",

                produit: produit.nom,

                quantite: quantite,

                montant: prix
            });


            alert("Achat enregistré !");


            document.getElementById("achatForm").reset();


            afficherStock();

            afficherHistorique();

            mettreAJourDashboard();
        }

    }
);



// ========================================
// ENREGISTRER UNE VENTE
// ========================================

document.getElementById("venteForm").addEventListener(
    "submit",
    function(event) {

        event.preventDefault();


        let id =
            Number(document.getElementById("venteProduit").value);

        let quantite =
            Number(document.getElementById("venteQuantite").value);

        let prix =
            Number(document.getElementById("ventePrix").value);


        let produit =
            produits.find(p => p.id === id);


        if (produit) {

            // Vérifier le stock
            if (produit.stock < quantite) {

                alert("Stock insuffisant !");

                return;
            }


            // Diminuer le stock
            produit.stock -= quantite;


            // Historique
            historique.push({

                date: new Date().toLocaleString(),

                type: "Vente",

                produit: produit.nom,

                quantite: quantite,

                montant: prix
            });


            alert("Vente enregistrée !");


            document.getElementById("venteForm").reset();


            afficherStock();

            afficherHistorique();

            mettreAJourDashboard();

        }

    }
);



// ========================================
// AFFICHER HISTORIQUE
// ========================================

function afficherHistorique() {

    let table =
        document.getElementById("historiqueTable");


    table.innerHTML = "";


    historique.forEach(function(operation) {

        let ligne = `

            <tr>

                <td>${operation.date}</td>

                <td>${operation.type}</td>

                <td>${operation.produit}</td>

                <td>${operation.quantite}</td>

                <td>${operation.montant} Ar</td>

            </tr>

        `;


        table.innerHTML += ligne;

    });

}



// ========================================
// RECHERCHE PRODUIT
// ========================================

function searchProduct() {

    let recherche =
        document.getElementById("searchStock")
            .value
            .toLowerCase();


    let lignes =
        document.querySelectorAll("#stockTable tr");


    lignes.forEach(function(ligne) {

        let texte =
            ligne.textContent.toLowerCase();


        if (texte.includes(recherche)) {

            ligne.style.display = "";

        } else {

            ligne.style.display = "none";

        }

    });

}



// ========================================
// INITIALISATION
// ========================================


