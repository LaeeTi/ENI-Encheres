<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Edition Profil</title>
		<link type="text/css" rel="stylesheet" href="inc/style.css" />
	</head>
	
	<header>
		<div class="top">
		<div class="logo">
			<p>ENI-Enchères</p>
		</div></div>
	</header>
	
	<body>
		<h1>Mon profil</h1>
		
        <div>
            <form method="post" action="<c:url value="/creationProfil"/>" >
                <fieldset>
                    <legend>Mon profil</legend>
                    <c:import url="/inc/inc_profil_form.jsp" />
                </fieldset>  
                <p class="info">${ form.resultat }</p>
                
                <div class="bloc_creer">
                	<input type="submit" value="Créer"  />          
                </div>
                
                <div class="bloc_annuler">
					<a href="./accueil"> <input type="button" value="Annuler">  </a>
				</div>
				
				
            </form>
        </div>
    </body>
</html>