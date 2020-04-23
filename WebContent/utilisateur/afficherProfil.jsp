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

	<div class="bloc-afficher-profil">
            <p>Pseudo : <c:out value="${ utilisateurAffiche.pseudo }"/></p><br>
            <p>Nom : <c:out value="${ utilisateurAffiche.nom }"/></p><br>
            <p>Prénom : <c:out value="${ utilisateurAffiche.prenom }"/></p><br>
            <p>Email : <c:out value="${ utilisateurAffiche.email }"/></p><br>
            <p>Téléphone : <c:out value="${ utilisateurAffiche.telephone }"/></p><br>
            <p>Rue : <c:out value="${ utilisateurAffiche.rue }"/></p><br>
            <p>Code postal : <c:out value="${ utilisateurAffiche.codePostal }"/></p><br>
            <p>Ville : <c:out value="${ utilisateurAffiche.ville }"/></p><br>
    </div>
	
		<c:if test="${ utilisateurAffiche.noUtilisateur == sessionScope.utilisateurConnecte.noUtilisateur }">
			<div class="bloc-inscription">
				<a href="./editionProfil"><input type="button" id="modifier" value="Modifier" /></a>
			</div>
		</c:if>	
</body>
</html>