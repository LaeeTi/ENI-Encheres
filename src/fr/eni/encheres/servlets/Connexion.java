package fr.eni.encheres.servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.beans.Utilisateur;
import fr.eni.encheres.dao.DAOFactory;
import fr.eni.encheres.dao.UtilisateurDao;


public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOFactory daoFactory;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/connexion.jsp" ).forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		valider(request, response);
	}

	protected void valider(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher dispatcher;
		
		Utilisateur utilisateurConnecte = null;
			
		// Récupération des informations saisies dans le formulaire
		String pseudo = request.getParameter("pseudo");
		String motdepasse = request.getParameter("motdepasse");

		// Controle des informations :
		// si tous les champs ne sont pas renseignés, revenir sur la page du formulaire
		if (   (pseudo == null) || (pseudo.length() == 0) 
			|| (motdepasse == null) || (motdepasse.length() == 0)) {
			
			String message = "Les champs Identifiant et Mot de passe sont obligatoires";
			request.setAttribute("messageErreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/connexion.jsp" ).forward( request, response );
			return;
		}
		
		
		this.daoFactory = DAOFactory.getInstance();
		UtilisateurDao utilisateurDao = daoFactory.getUtilisateurDao();
		
		List<Utilisateur> utilisateurs = utilisateurDao.lister();
		for (Utilisateur u : utilisateurs) {
			if(u.getPseudo().equals(pseudo)){
				if(u.getMotDePasse().equals(motdepasse)){
					utilisateurConnecte = u;
				}
			}
		}
		HttpSession session = request.getSession();
		session.setAttribute("utilisateurConnecte", utilisateurConnecte);
	
		// Si l'authenification est réussie...
		if (utilisateurConnecte != null) {
			// Présenter la réponse
			response.sendRedirect(request.getContextPath() + "/accueil");
			return;
		}
		// ...sinon
		else {
			// Retourner à l'écran d'identification avec un message d'erreur fonctionnel			
			String message = "Identifiant ou mot de passe incorrect";
			request.setAttribute("messageErreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/connexion.jsp" ).forward( request, response );
			}
	}
}
