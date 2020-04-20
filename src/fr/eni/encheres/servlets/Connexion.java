package fr.eni.encheres.servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.beans.Utilisateur;
import fr.eni.encheres.dao.DAOFactory;
import fr.eni.encheres.dao.UtilisateurDao;



/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOFactory daoFactory;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		valider(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		valider(request, response);
	}

	protected void valider(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//RequestDispatcher dispatcher;
		
		//boolean isAdministrateur=false;
		String accessPage = null;
		String menuJsp=null;
		String utilisateurConnecteAttName=null;
		Utilisateur utilisateurConnecte = null;
		
		/*
		// Acces pour un animateur ou un stagiaire ?
		// C'est un animateur
		if ("animateur".equals(request.getParameter("typeUtilisateur"))) {
			isAdministrateur=true;
			accessPage="AccesAnimateurPage";
			utilisateurConnecteAttName="animateurConnecte";
			menuJsp="/animateur/menu.jsp";
		}
		// C'est un stagiaire
		else if ("stagiaire".equals(request.getParameter("typeUtilisateur"))) {
			isStagiaire=true;
			accessPage="AccesStagiairePage";
			utilisateurConnecteAttName="stagiaireConnecte";
			menuJsp="/stagiaire/menu.jsp";
		}
		// Probl�me...
		else {
			// TODO page erreur ? page accueil ?
		} 

		// Si l'utilisateur est d�j� connect�, on redirige vers son menu
		utilisateurConnecte = (Utilisateur)request.getSession().getAttribute(utilisateurConnecteAttName);	
		if (utilisateurConnecte!=null) {
			response.sendRedirect(request.getContextPath()+menuJsp);
			return;
		}
		*/
		
		
		// R�cup�ration des informations saisies dans le formulaire
		String pseudo = request.getParameter("pseudo");
		String motdepasse = request.getParameter("motdepasse");

		// Controle des informations :
		// si tous les champs ne sont pas renseign�s, revenir sur la page du formulaire
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
		/*
		try {
			// Valider l'identification par rapport aux informations de la base
			if (isAdministrateur) {
				utilisateurConnecte = AnimateurDAO.rechercher(new Animateur(0, null, null, motdepasse, mail));
			}
			else if (isStagiaire) {
				utilisateurConnecte = StagiaireDAO.rechercher(new Stagiaire(0, null, null, motdepasse, mail));
			}
			
		} catch (SQLException sqle) {
			// Placer l'objet repr�sentant l'exception dans le contexte de requete
			request.setAttribute("erreur", sqle);
			// Passer la main � la page de pr�sentation des erreurs
			dispatcher = getServletContext().getRequestDispatcher("/erreur/erreur.jsp");
			dispatcher.forward(request, response);
			return;
		}		
	*/		
		// Si l'authenification est r�ussie...
		if (utilisateurConnecte != null) {
			// Invalider la session en cours dans le cas o� c'est un autre profil qui est d�j� connect�
			request.getSession().invalidate();
			
			// Placer le bean dans le contexte de session
			request.getSession().setAttribute(utilisateurConnecteAttName, utilisateurConnecte);
			// Pr�senter la r�ponse
			response.sendRedirect("/WEB-INF/connexion.jsp");
			return;
		}
		// ...sinon
		else {
			// Retourner � l'�cran d'identification avec un message d'erreur fonctionnel			
			String message = "Identifiant ou mot de passe incorrect";
			request.setAttribute("messageErreur", message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/connexion.jsp" ).forward( request, response );
			}
	}
}
