<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Connexion</title>
<link type="text/css" rel="stylesheet" href="inc/style.css" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

</head>
<body>
	<header>
		<div class="top">
		<div class="logo">
			<p>ENI-Enchères</p>
		</div></div>
	</header>
	
	<%
		String messageErreur = (String)request.getAttribute("messageErreur");
		if (messageErreur==null) messageErreur="";
	%>
	
	<div class="connexion">
	<form action="connexion" method="post">
		<div class="bloc-champs">
			<span><label for="pseudo"> Identifiant : </label>
			<input class="champtexte" type="text" name="pseudo" id="pseudo"/></span>
			<span><label for="motdepasse">Mot de passe : </label>
			<input class="champtexte" type="password"  id="motdepasse" name="motdepasse"/></span>
		</div>
		<div class="bloc-bas">	
			<div class="bloc-bouton">
				<input type="submit" id="connexion" value="Connexion" />
			</div>
		
			<div class="bloc-droit">   
 			 	<span><input type="checkbox" id="enregistrement_profil" name="enregistrement_profil">
 			 	<label for="horns">Se souvenir de moi</label></span>
 			 	<span><a href="/connexion">Mot de passe oubliïé</a> </span>
			</div>
		</div>	
	</form>
			
		<div class="erreur">
		<% if (!"".equals(messageErreur)) { %>
			<div>
				<p><%=messageErreur%></p>
			</div>
			<% } %>
		</div>	
		<div class="bloc-inscription">
		<a href="/creationProfil"><input type="button" id="inscription" value="Créer un compte" /></a>
		</div>
	</div>		
</body>
</html>