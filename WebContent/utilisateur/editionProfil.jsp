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

	<h1>Mon profil</h1>
		
        <div>
            <form method="post" action="<c:url value="/editionProfil"/>" >
                   
                   <c:import url="/inc/inc_profil_form.jsp" />
                
                
                
                <p class="info">${ form.resultat }</p>
                
                <div class="bloc_enregistrer">
                	<input type="submit" value="Enregistrer"  />          
                </div>
                
                <div class="bloc_supprimer">
					<%-- <a href="/SupprimerProfil"> <input type="button" value="Supprimer">  </a> --%>
					<a href="<c:url value="/afficherProfil?noUtilisateur=${ sessionScope.utilisateurConnecte.noUtilisateur }"/>"><input type="button" value="Supprimer"> </a>
				</div>			
  		</form>  
        </div>
        
</body>
</html>