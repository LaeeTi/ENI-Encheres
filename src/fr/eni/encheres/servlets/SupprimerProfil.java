package fr.eni.encheres.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.dao.DAOFactory;
import fr.eni.encheres.dao.UtilisateurDao;



public class SupprimerProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UtilisateurDao          utilisateurDao;
    private DAOFactory 				daoFactory;
	
	//R�cup�ration d'une instance de la DaoUtilisateur
    public void init() throws ServletException {
		 //Initialisation de la DAOFactory
   		this.daoFactory = DAOFactory.getInstance();
   		utilisateurDao = daoFactory.getUtilisateurDao();
	 }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String noUtilisateur = getValeurParametre( request, "noUtilisateur" );
        Long id = Long.parseLong( noUtilisateur );
        
        
		response.sendRedirect(request.getContextPath() + "/accueil");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
	
	private static String getValeurParametre( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
	}
}
