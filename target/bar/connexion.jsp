
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GESTIONBAR</title>

    <link rel="stylesheet" href="css/fenetreconnexion.css">
</head>

<body>
    <div class="Cont">
        <h1>Gestion bar</h1>

        <h2>Bienvenue sur le site de connexion</h2>
        <h2>Connexion</h2>
        <p>${message}</p>

        <form action="<%= request.getContextPath() %>/connexion" method="post">
            <label for="Nom">Nom de l utilisateur </label>
            <input type="text" id="Nom" placeholder="Entrer votre nom">

            <label for="Password">Mot de passe</label>
            <input type="password" id="Password" placeholder="Entrer votre mot de passe">

            <button type="submit">Se connecter</button>
        </form>

        <p>Vous n'avez pas de compte ?</p>
        <a href="Inscription.html">Creer mon compte</a>
    </div>
</body>
</html>
