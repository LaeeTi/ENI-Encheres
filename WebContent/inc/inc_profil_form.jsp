<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<label for="pseudoUtilisateur">Pseudo :<span class="requis">*</span></label>
<input type="text" id="pseudoUtilisateur" name="pseudoUtilisateur" value="<c:out value="${utilisateur.pseudo}"/>" />
<span class="erreur">${form.erreurs['pseudoUtilisateur']}</span>


<label for="nomUtilisateur">Nom :<span class="requis">*</span></label>
<input type="text" id="nomUtilisateur" name="nomUtilisateur" value="<c:out value="${utilisateur.nom}"/>" />
<span class="erreur">${form.erreurs['nomUtilisateur']}</span>
<br />

<label for="prenomUtilisateur">Prénom :<span class="requis">*</span></label>
<input type="text" id="prenomUtilisateur" name="prenomUtilisateur" value="<c:out value="${utilisateur.prenom}"/>" />
<span class="erreur">${form.erreurs['prenomUtilisateur']}</span>


<label for="emailUtilisateur">Email :<span class="requis">*</span></label>
<input type="email" id="emailUtilisateur" name="emailUtilisateur" value="<c:out value="${utilisateur.email}"/>" />
<span class="erreur">${form.erreurs['emailUtilisateur']}</span>
<br />

<label for="telephoneUtilisateur">Telephone :<span class="requis">*</span></label>
<input type="text" id="telephoneUtilisateur" name="telephoneUtilisateur" value="<c:out value="${utilisateur.telephone}"/>" />
<span class="erreur">${form.erreurs['telephoneUtilisateur']}</span>


<label for="rueUtilisateur">Rue :<span class="requis">*</span></label>
<input type="text" id="rueUtilisateur" name="rueUtilisateur" value="<c:out value="${utilisateur.rue}"/>" />
<span class="erreur">${form.erreurs['rueUtilisateur']}</span>
<br />

<label for="codePostalUtilisateur">Code Postal :<span class="requis">*</span></label>
<input type="text" id="codePostalUtilisateur" name="codePostalUtilisateur" value="<c:out value="${utilisateur.codePostal}"/>" />
<span class="erreur">${form.erreurs['codePostalUtilisateur']}</span>


<label for="villeUtilisateur">Ville :<span class="requis">*</span></label>
<input type="text" id="villeUtilisateur" name="villeUtilisateur" value="<c:out value="${utilisateur.ville}"/>" />
<span class="erreur">${form.erreurs['villeUtilisateur']}</span>
<br />

<label for="motDePasseUtilisateur">Mot de passe :<span class="requis">*</span></label>
<input type="password" id="motDePasseUtilisateur" name="motDePasseUtilisateur" />
<span class="erreur">${form.erreurs['motDePasseUtilisateur']}</span>


<label for="VerificationMotDePasseUtilisateur">Confirmation :<span class="requis">*</span></label>
<input type="password" id="VerificationMotDePasseUtilisateur" name="VerificationMotDePasseUtilisateur" />
<span class="erreur">${form.erreurs['verificationMotDePasseUtilisateur']}</span>
<br />
