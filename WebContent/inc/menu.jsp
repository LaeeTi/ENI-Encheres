<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="menu">
    <p><a href="<c:url value="/utilisateur/editionEnchere"/>">Enchères</a></p>
    <p><a href="<c:url value="/utilisateur/vente"/>">Vendre un article</a></p>
    <p><a href="<c:url value="/utilisateur/afficherProfil"/>">Mon profil</a></p>
    <p><a href="<c:url value="/deconnexion"/>">Déconnexion</a></p>
</div>