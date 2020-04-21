<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Connexion</title>
<link type="text/css" rel="stylesheet" href="inc/style.css" />

</head>
<body>
	<header>
		<div class="top">
		<div class="logo">
			<p>ENI-Ench�res</p>
		</div></div>
	</header>
	
	<form class="connexion" action="connexion" method="post">
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
			<input type="submit" id="inscription" value="Cr�er un compte" />
		</div>
		</form>
		
		<div>
 			 <input type="checkbox" id="enregistrement_profil" name="enregistrement_profil">
 			 <label for="horns">Se souvenir de moi</label>
		</div>
		
		<p> <a href="/connexion">Mot de passe oubli�</a> </p>
		
		
	</form>
</body>
</html>