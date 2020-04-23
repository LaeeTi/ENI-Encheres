package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.beans.Utilisateur;
import fr.eni.encheres.dao.DAOFactory;
import fr.eni.encheres.dao.UtilisateurDao;

public class AfficherProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOFactory daoFactory;
	
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long noUtilisateur = Long.parseLong(request.getParameter("noUtilisateur"));
		this.daoFactory = DAOFactory.getInstance();
		UtilisateurDao utilisateurDao = daoFactory.getUtilisateurDao();
		
		Utilisateur utilisateurAffiche = utilisateurDao.trouver(noUtilisateur);
		
		request.setAttribute("utilisateurAffiche", utilisateurAffiche);
		this.getServletContext().getRequestDispatcher( "/utilisateur/afficherProfil.jsp" ).forward( request, response );
	}

}
