<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Accueil</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"></c:url>" />
</head>
<body>
	<header>
		<div class="top">
		<div class="logo">
			<p>ENI-Enchères</p>
		</div>
		<div class="menu">
			<p><a href="<c:url value=""/>">S'inscrire - se connecter</a></p>
		</div>
		</div>
		<div class="titre_page">
			<h1>Liste des enchères</h1>
		</div>	
	</header>
	 <%-- /rechercherEnchere correspond au nom définit en annotation sur la servlet (@WebServlet("/rechercherEnchere")) --%>
	<form method="post" action="<c:url value="/testDao"></c:url>">
		<fieldset class="filtre">
			<legend>Filtres :</legend>
            <input type="search" id="recherche" name="recherche" placeHolder="Le nom de l'article contient" size="30" maxlength="30" />
            <br />
            <label for=categorie">Catégorie :</label>
            <select name="categorie" id="categorie">
            	<option value="toutes">Toutes</option>
            	<c:forEach items="${ categories }" var="categorie">
                   <option value="${ categorie.noCategorie }">${categorie.libelle }</option>
				</c:forEach>
           	</select>
            <br />
            <input type="radio" id="achats" name="type" value="achats" checked />
            <label for="achats">Achats</label>
            <input type="checkbox" id="encheresOuvertes" name="encheresOuvertes" checked >
            <label for="encheresOuvertes" class="">enchères ouvertes</label>
            <input type="checkbox" id="encheresEnCours" name="encheresEnCours" >
            <label for="encheresEnCours" class="">mes enchères en cours</label>
            <input type="checkbox" id="encheresRemportees" name="encheresRemportees">
            <label for="encheresRemportees" disabled="false">mes enchères remportées</label>
                        
            <input type="radio" id="ventes" name="type" value="ventes" />
            <label for="ventes">Mes ventes</label>
            <input type="checkbox" id="ventesEnCours" name="ventesEnCours" disabled>
            <label for="ventesEnCours" class="disabled">ventes en cours</label>
            <input type="checkbox" id="ventesNonDebutees" name="ventesNonDebutees" disabled>
            <label for="ventesNonDebutees" class="disabled">ventes non debutées</label>
            <input type="checkbox" id="ventesTerminees" name="ventesTerminees" disabled>
            <label for="ventesTerminees" class="disabled">ventes terminées</label>

		</fieldset>
                <input type="submit" id="rechercher" value="rechercher"  />
	</form>
	<div class="liste_enchere">
		<div class="enchere_miniature"></div>
	</div>	
</body>
</html>