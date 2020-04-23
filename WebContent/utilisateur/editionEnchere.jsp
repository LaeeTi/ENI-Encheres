<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Edition enchère</title>
		<link type="text/css" rel="stylesheet" href="inc/style.css" />
	</head>
<body>
		<header>
		<div class="top">
		<div class="logo">
			<p>ENI-Enchères</p>
		</div></div>
	</header>
	
	<h1>Nouvelle Vente</h1>
	
	<form method="post" action="<c:url value="/utilisateur/editionEnchere"/>" >
		
		<label for="nomArticle">Article  :<span class="requis">*</span></label>
		<input type="text" id="nomArticle" name="nomArticle" value="<c:out value="${article.nom}"/>" size="30" maxlength="30" />
		<span class="erreur">${form.erreurs['nomArticle']}</span>
		<br>
		
		<label for="descriptionArticle">Description : </label>
		<input type="text" id="descriptionArticle" name="descriptionArticle" value="<c:out value="${article.description}"/>" size="30" maxlength="30" />
		<span class="erreur">${form.erreurs['descriptionArticle']}</span>
		<br>
		
		<label for="categorieArticle">Categorie : </label>
		<input type="text" id="categorieArticle" name="categorieArticle" value="<c:out value="${article.categorie}"/>" size="30" maxlength="30" />
		<span class="erreur">${form.erreurs['categorieArticle']}</span>
		<br>
		
		<label for="categorieArticle">Categorie : </label>
		<select name="categories">
    		<c:forEach items="${categories}" var="categories">
       			<option value="${categorie.noCategorie}">${categorie.libelle}</option>
    		</c:forEach>
		</select>
		<br>
		
		<label for="imageArticle">Image</label>
		<input type="file" id="imageArticle" name="imageArticle" />
		<span class="erreur">${form.erreurs['imageArticle']}</span>
		<br />
		
		<label for="miseAPrixArticle">Mise à prix : <span class="requis">*</span></label>
		<input type="number" id="miseAPrixArticle" name="miseAPrixArticle" value="<c:out value="${article.miseAPrix}"/>" size="30" maxlength="30" />
		<span class="erreur">${form.erreurs['miseAPrixArticle']}</span>
		<br>
		
		<label for="debutEnchereArticle">Début de l'enchère : <span class="requis">*</span></label>
		<input type="date" id="debutEnchereArticle" name="debutEnchereArticle" value="<c:out value="${article.debutEnchere}"/>" size="30" maxlength="30" />
		<span class="erreur">${form.erreurs['debutEnchereArticle']}</span>
		<br>
		
		<label for="finEnchereArticle">Fin de l'enchère : <span class="requis">*</span></label>
		<input type="date" id="finEnchereArticle" name="finEnchereArticle" value="<c:out value="${article.finEnchere}"/>" size="30" maxlength="30" />
		<span class="erreur">${form.erreurs['finEnchereArticle']}</span>
		<br>
		
		
		<fieldset>
        	<legend>Retrait</legend>
            <c:import url="/inc/inc_retrait_form.jsp" />
       	</fieldset> 
		<br>
		
		<div class="bloc_creer">
             <input type="submit" value="Enregistrer"  />          
        </div>
        <br>
        
        <div class="bloc_annuler">
			<a href="/accueil"> <input type="button" value="Annuler">  </a>
		</div>
	
	
	</form>
</body>
</html>