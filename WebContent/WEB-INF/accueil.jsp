<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Accueil</title>
<link type="text/css" rel="stylesheet"
	href=<c:url value="/inc/style.css"></c:url> />
</head>
<body>
	<header>
	<div class="top">
		<div class="logo">
			<p>ENI-Enchères</p>
		</div>
		<div class="menu">
			<p>
			<c:if test ="${ empty utilisateurConnecte }"><a href="<c:url value="/connexion"/>">S'inscrire - se connecter</a></c:if>
			<c:if test ="${ not empty utilisateurConnecte }"><a href="<c:url value="/deconnexion"/>">Se déconnecter</a></c:if>
			</p>
		</div>
	</div>
	<div class="titre-page">
		<h1>
			Liste des enchères (
			<c:out value="${ sessionScope.utilisateurConnecte.pseudo }"></c:out>
			)
		</h1>
	</div>
	</header>
	<%-- /rechercherEnchere correspond au nom définit en annotation sur la servlet (@WebServlet("/rechercherEnchere")) --%>

	<form method="post" action="<c:url value="/accueil"></c:url>">
		<div class="liste-ventes">
			<div class="filtres">
				<div class="titre">Filtres :</div>
				<div class=search>
					<img class=search-icon src="./inc/loupe.svg" alt="icône de loupe">
					<input type="search" id="recherche" name="recherche"
						placeHolder="Le nom de l'article contient"
						value="<c:out value="${recherche}"></c:out>" />
				</div>
				<div class="categorie">
					<label for=categorie">Catégorie :</label> <select name="categorie"
						id="categorie">
						<option value="0">Toutes</option>
						<c:forEach items="${ categories }" var="cat">
							<%-- Condition pour garder la selection precedente --%>
							<c:set var="isSelected" scope="page" value="" />
							<c:if test="${ categorie == cat.noCategorie }">
								<c:set var="isSelected" value="selected" />
							</c:if>
							<option value="${ cat.noCategorie }" ${ isSelected }>${cat.libelle }
							</option>
						</c:forEach>

					</select>
				</div>
				<%-- Condition pour garder les cases cochées précedement --%>
				
				
				<div class="radio-type" <c:if test ="${ empty utilisateurConnecte }">style="display:none"</c:if> >
					<div class="checkboxes">
						<span><input type="radio" id="achats" name="type"
							value="achats" <c:if test ="${ type == 'achats' }">checked</c:if> />
							<label for="achats">Achats</label></span> <span><input
							type="checkbox" id="encheresOuvertes" name="encheresOuvertes"
							class="input-achats"
							<c:if test ="${ encheresOuvertes }">checked</c:if>> <label
							class="input-achats" for="encheresOuvertes">enchères
								ouvertes</label></span> <span><input type="checkbox"
							id="encheresEnCours" name="encheresEnCours" class="input-achats"
							<c:if test ="${ encheresEnCours }">checked</c:if>> <label
							class="input-achats" for="encheresEnCours">mes enchères
								en cours</label></span> <span><input type="checkbox"
							id="encheresRemportees" name="encheresRemportees"
							class="input-achats"
							<c:if test ="${ encheresRemportees }">checked</c:if>> <label
							class="input-achats" for="encheresRemportees">mes
								enchères remportées</label></span>
					</div>
					<div class="checkboxes">
						<span><input type="radio" id="ventes" name="type"
							value="ventes" <c:if test ="${ type == 'ventes' }">checked</c:if> />
							<label for="ventes">Mes ventes</label></span> <span><input
							type="checkbox" id="ventesEnCours" name="ventesEnCours"
							class="input-ventes"
							<c:if test ="${ ventesEnCours }">checked</c:if>> <label
							class="input-ventes" for="ventesEnCours">mes ventes en
								cours</label></span> <span><input type="checkbox" id="ventesNonDebutees"
							name="ventesNonDebutees" class="input-ventes"
							<c:if test ="${ ventesNonDebutees }">checked</c:if>> <label
							class="input-ventes" for="ventesNonDebutees">ventes non
								debutées</label></span> <span><input type="checkbox"
							id="ventesTerminees" name="ventesTerminees" class="input-ventes"
							<c:if test ="${ ventesTerminees }">checked</c:if>> <label
							class="input-ventes" for="ventesTerminees">ventes
								terminées</label></span>
					</div>
				</div>
			</div>
			<div class="bouton-rechercher">
				<input type="submit" id="rechercher" value="rechercher" />
			</div>
		</div>
	</form>
	<div class="liste_enchere">
		<c:forEach items="${ articlesAffiches }" var="article">
			<div class="enchere-miniature">

				<p>
					<a href="<c:url value="/vente.jsp"/>"> <c:out
							value="${ article.value.nomArticle }"></c:out></a>
				</p>
				<p>
					Prix :
					<c:out value="${ article.value.miseAPrix }"></c:out>
					points
				</p>

				<p>
					Fin de l'enchère :
					<joda:format value="${ article.value.dateFinEncheres}"
						pattern="dd/MM/yyyy"></joda:format>
				</p>
				<p>
					Vendeur :
					<c:out value="${ article.value.vendeur.pseudo }"></c:out>
				</p>
			</div>
		</c:forEach>

	</div>
	<script src="<c:url value="/inc/jquery.js"></c:url>"></script>

	<%-- Petite fonction jQuery permettant d'inactiver les checkboxes du bouton radio non choisi. --%>
	<script>
		var valRadio;
		jQuery(document).ready(function() {		
			valRadio = jQuery('input[name=type]:checked').val();
			test(valRadio);
			jQuery('input[name=type]:radio').click(function() {
				valRadio = jQuery(this).val()
				test(valRadio)});
		});	
		function test (valRadio) {
			$("input.input-achats, input.input-ventes").prop("disabled", true);
			$("input.input-" + valRadio).prop("disabled", false);
			console.log(valRadio);
			if (valRadio == "achats") {
				$("input.input-ventes").prop("checked", false);
				$("label.input-ventes").addClass("grisee");
				$("label.input-achats").removeClass("grisee");
			} else if (valRadio == "ventes") {
				$("input.input-achats").prop("checked", false);
				$("label.input-achats").addClass("grisee");
				$("label.input-ventes").removeClass("grisee");
			}
		}
	</script>
</body>
</html>