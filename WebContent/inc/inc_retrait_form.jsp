<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rueVendeur" value="${sessionScope.utilisateurConnecte.rue }"/> 
<c:if test =" ${not empty retrait.rue }"><c:set var="rueVendeur" value="${retrait.rue}"/>dans le if </c:if>

<label for="rueRetrait">Rue : </label>
<input type="text" id="rue" name="rueRetrait" value="<c:out value="${rueVendeur}"/>" />
<span class="erreur">${form.erreurs['rueRetrait']}</span>
<br />

<label for="codePostalRetrait">Code Postal : </label>
<input type="text" id="codePostalRetrait" name="codePostalRetrait" value="<c:out value="${retrait.codePostal}"/>" />
<span class="erreur">${form.erreurs['codePostalRetrait']}</span>
<br />

<label for="villeRetrait">Ville : </label>
<input type="text" id="villeRetrait" name="villeRetrait" value="<c:out value="${retrait.ville}"/>" />
<span class="erreur">${form.erreurs['villeRetrait']}</span>
<br />