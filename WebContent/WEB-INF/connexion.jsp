<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Connexion</title>
</head>
<body>
	<header>
		<div class="top">
		<div class="logo">
			<p>ENI-Enchères</p>
		</div></div>
	</header>
	
	<%
		String pseudo = request.getParameter("pseudo");
		if (pseudo==null) pseudo="";
		String mdp = request.getParameter("motdepasse");
		if (mdp==null) mdp="";
		String messageErreur = (String)request.getAttribute("messageErreur");
		if (messageErreur==null) messageErreur="";
	%>
	
	<form class="connexion" action="<%=request.getContextPath()%>/accueil" method="post">
		<div class="bloc_identifiant"></div>
			<label for="pseudo"> Pseudo : </label>
			<input class="champtexte" type="text" name="pseudo" id="pseudo"/>
		</div>	
		
		<div class="bloc_motdepasse"></div>
			<label for="motdepasse">Mot de passe</label>
			<input class="champtexte" type="password"  id="motdepasse" name="motdepasse"/>
		</div>	
		
			
		<div class="bloc_connexion">
			<input type="submit" id="connexion" value="Connexion" />
		</div>
	
		<div class="bloc_inscription">
			<input type="submit" id="inscription" value="Créer un compte" />
		</div>
		</form>
		
		<div>
 			 <input type="checkbox" id="enregistrement_profil" name="enregistrement_profil">
 			 <label for="horns">Se souvenir de moi</label>
		</div>
		
		<p> <a href="/connexion">Mot de passe oublié</a> </p>
		
		<% if (!"".equals(messageErreur)) { %>
		<div><p><%=messageErreur%></p></div>
		<% } %>
		
	</form>
</body>
</html>