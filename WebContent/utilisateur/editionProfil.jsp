<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edition Profil</title>
	<link type="text/css" rel="stylesheet" href="inc/style.css" />
	</head>
<body>
		<header>
		<div class="top">
		<div class="logo">
			<p>ENI-Enchères</p>
		</div></div>
	</header>

	<div id="corps">
            <p class="info">${ form.resultat }</p>
            <p>Pseudo : <c:out value="${ utilisateur.pseudo }"/></p><br>
            <p>Nom : <c:out value="${ utilisateur.nom }"/></p><br>
            <p>Prénom : <c:out value="${ utilisateur.prenom }"/></p><br>
            <p>Email : <c:out value="${ utilisateur.email }"/></p><br>
            <p>Téléphone : <c:out value="${ utilisateur.telephone }"/></p><br>
            <p>Rue : <c:out value="${ utilisateur.rue }"/></p><br>
            <p>Code postal : <c:out value="${ utilisateur.codePostal }"/></p><br>
            <p>Ville : <c:out value="${ utilisateur.ville }"/></p><br>
        </div>
</body>
</html>