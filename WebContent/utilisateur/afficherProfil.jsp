<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Afficher profil</title>
		<link type="text/css" rel="stylesheet" href="inc/style.css" />
	</head>

<body>
	<header>
		<div class="top">
		<div class="logo">
			<p>ENI-Enchères</p>
		</div></div>
	</header>

	<div id="bloc-afficher-profil">
            <p>Pseudo : <c:out value="${ utilisateurAffiche.pseudo }"/></p>
            <p>Nom : <c:out value="${ utilisateurAffiche.nom }"/></p>
            <p>Prénom : <c:out value="${ utilisateurAffiche.prenom }"/></p>
            <p>Email : <c:out value="${ utilisateurAffiche.email }"/></p>
            <p>Téléphone : <c:out value="${ utilisateurAffiche.telephone }"/></p>
            <p>Rue : <c:out value="${ utilisateurAffiche.rue }"/></p>
            <p>Code postal : <c:out value="${ utilisateurAffiche.codePostal }"/></p>
            <p>Ville : <c:out value="${ utilisateurAffiche.ville }"/></p>
    </div>
	
	Num utilisateur affiché<c:out value="${ utilisateurAffiche.noUtilisateur }"/>
	Num utilisateur connecté<c:out value="${ utilisateurConnecte.noUtilisateur }"/>
	
</body>
</html>