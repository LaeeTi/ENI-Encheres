package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.dao.DAOFactory;


import fr.eni.encheres.dao.CategorieDao;
import fr.eni.encheres.beans.Categorie;

public class CreerArticle extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";

	private CategorieDao 		categorieDao;
	private DAOFactory 			daoFactory;
	
	public void init() throws ServletException {
        /* Initialisation d'une instance de notre DAO Article */
		this.daoFactory = DAOFactory.getInstance();
		categorieDao = daoFactory.getCategorieDao();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Categorie> categories = categorieDao.lister();
		request.setAttribute("categories", categories);
	    request.getRequestDispatcher("/utilisateur/editionEnchere.jsp").forward(request, response);
		//this.getServletContext().getRequestDispatcher( "/utilisateur/editionEnchere.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
