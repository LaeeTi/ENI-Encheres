<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Accueil</title>
<link type="text/css" rel="stylesheet" href=<c:url value="/inc/style.css"></c:url> />
</head>
<body>
	<header>
		<div class="top">
		<div class="logo">
			<p>ENI-Enchères</p>
		</div>
		
		<div class="menu">
			<c:import url="/inc/menu.jsp" />
		</div>
		</div>
		<div class="titre-page">
			<h1>Liste des enchères</h1>
		</div>	
	</header>
	 <%-- /rechercherEnchere correspond au nom définit en annotation sur la servlet (@WebServlet("/rechercherEnchere")) --%>
	
	<form method="post" action="<c:url value="/testDao"></c:url>">
	<div class="form">	
		<div class="filtres">
			<div class="titre">Filtres :</div>
			<div class=search>
				<img class=search-icon src="./inc/loupe.svg" alt="icône de loupe">
				<input type="search" id="recherche" name="recherche" placeHolder="Le nom de l'article contient" />
			</div>
			<div class="categorie">
            <label for=categorie">Catégorie :</label>
            <select name="categorie" id="categorie">
            	<option value="toutes">Toutes</option>
            	<c:forEach items="${ categories }" var="categorie">
                   <option value="${ categorie.noCategorie }">${categorie.libelle }</option>
				</c:forEach>
           	</select>
           	</div>
            <div class="radio-type">
            	<div class="checkboxes">            		
            		<span><input type="radio" id="achats" name="type" value="achats" checked />
            		<label for="achats">Achats</label></span>
            		<span><input type="checkbox" id="encheresOuvertes" name="encheresOuvertes" class="input-achats" checked >
            		<label class="input-achats" for="encheresOuvertes" >enchères ouvertes</label></span>
            		<span><input type="checkbox" id="encheresEnCours" name="encheresEnCours" class="input-achats" >
            		<label class="input-achats" for="encheresEnCours" >mes enchères en cours</label></span>
            		<span><input type="checkbox" id="encheresRemportees" name="encheresRemportees" class="input-achats">
            		<label class="input-achats" for="encheresRemportees" >mes enchères remportées</label></span>
                </div>
                <div class="checkboxes">        
            		<span><input type="radio" id="ventes" name="type" value="ventes" />
            		<label for="ventes">Mes ventes</label></span>
            		<span><input type="checkbox" id="ventesEnCours" name="ventesEnCours" class="input-ventes">
            		<label class="input-ventes" for="ventesEnCours" >mes ventes en cours</label></span>
            		<span><input type="checkbox" id="ventesNonDebutees" name="ventesNonDebutees" class="input-ventes">
            		<label class="input-ventes" for="ventesNonDebutees" >ventes non debutées</label></span>
            		<span><input type="checkbox" id="ventesTerminees" name="ventesTerminees" class="input-ventes">
            		<label class="input-ventes" for="ventesTerminees" >ventes terminées</label></span>
				</div>
			</div>
		</div>
		<div class="bouton-rechercher">
                <input type="submit" id="rechercher" value="rechercher"  />
		</div>
	</div>
	</form>
	<div class="liste_enchere">
		<div class="enchere_miniature"></div>
	</div>
	   <script src="<c:url value="/inc/jquery.js"></c:url>"></script>
        
        <%-- Petite fonction jQuery permettant d'inactiver les checkboxes du bouton radio non choisi. --%>
        <script>
        	jQuery(document).ready(function(){
        		/* 1 - Au lancement de la page, on cache le bloc d'éléments du formulaire correspondant aux clients existants */
        		    $("input.input-ventes").prop("disabled", true);
        		/* 2 - Au clic sur un des deux boutons radio "choixNouveauClient", on affiche le bloc d'éléments correspondant (nouveau ou ancien client) */
                jQuery('input[name=type]:radio').click(function(){
                	$("input-ventes, input-achats").prop("disabled", true);
                    var valRadio = jQuery(this).val();
                    $("input.input-"+ valRadio).prop("disabled", false);
                    if(valRadio == "achats"){
                    	$("input.input-ventes").prop("checked", false);	
                    } else if(valRadio == "ventes"){
                    	$("input.input-achats").prop("checked", false);
                    }
                });
            });
        </script>	
</body>
</html>