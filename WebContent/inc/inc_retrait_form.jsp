<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<label for="rueRetrait">Rue : </label>
<input type="text" id="rue" name="rueRetrait" value="<c:out value="${retrait.rue}"/>" size="30" maxlength="60" />
<span class="erreur">${form.erreurs['rueRetrait']}</span>
<br />

<label for="codePostalRetrait">Code Postal : </label>
<input type="text" id="codePostalRetrait" name="codePostalRetrait" value="<c:out value="${retrait.codePostal}"/>" size="30" maxlength="60" />
<span class="erreur">${form.erreurs['codePostalRetrait']}</span>
<br />

<label for="villeRetrait">Ville : </label>
<input type="text" id="villeRetrait" name="villeRetrait" value="<c:out value="${retrait.ville}"/>" size="30" maxlength="60" />
<span class="erreur">${form.erreurs['villeRetrait']}</span>
<br />