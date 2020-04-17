package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import fr.eni.encheres.beans.ArticleVendu;
import fr.eni.encheres.beans.Categorie;
import fr.eni.encheres.beans.Enchere;
import fr.eni.encheres.beans.Retrait;
import fr.eni.encheres.beans.Utilisateur;
import fr.eni.encheres.dao.ArticleVenduDao;
import fr.eni.encheres.dao.CategorieDao;
import fr.eni.encheres.dao.DAOFactory;
import fr.eni.encheres.dao.EnchereDao;
import fr.eni.encheres.dao.RetraitDao;
import fr.eni.encheres.dao.UtilisateurDao;

/**
 * Servlet implementation class testDao
 */
@WebServlet("/testDao")
public class testDao extends HttpServlet {
	
	private UtilisateurDao          utilisateurDao;
	private CategorieDao			categorieDao;
	private RetraitDao				retraitDao;
	private EnchereDao				enchereDao;
	private ArticleVenduDao			articleDao;
	
    private DAOFactory daoFactory;
    
	 public void init() throws ServletException {
		 //Initialisation de la DAOFactory
    		this.daoFactory = DAOFactory.getInstance();
	 }
	 
	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	        //Initialisation de la date du jour
	    	DateTime dt= new DateTime();
	        
	    	/*******************************************Test des methodes creer********************************************************/
	    	//Création d'une instance objetDao via la DAOFactory qui va nous servir pour créer les objets
	        this.utilisateurDao = daoFactory.getUtilisateurDao();
	        //Creation d'un objet (bean)
	        Utilisateur vendeur = new Utilisateur("vendeur", "nom", "prenom", "email", "telephone", "rue",
	    			"cp", "ville", "motDePasse", 0, true);
	        //Creation de l'objet en base de donnée via la methode creer de l'objet DAO
	        utilisateurDao.creer(vendeur);
	        //Ajout d'autres objets
	        Utilisateur acheteur = new Utilisateur("acheteur", "nom1", "prenom1", "email1", "telephone1", "rue1",
	    			"cp1", "ville1", "motDePasse1", 0, false);
	        utilisateurDao.creer(acheteur);
	        Utilisateur encherisseur = new Utilisateur("encherisseur", "nom2", "prenom2", "email2", "telephone2", "rue2",
	    			"cp2", "ville2", "motDePasse2", 0, false);
	        utilisateurDao.creer(encherisseur);
	        
	        
	        //idem pour les autres types d'objet
	        this.categorieDao = daoFactory.getCategorieDao();
	        Categorie informatique = new Categorie("Informatique");
	        categorieDao.creer(informatique);
	        Categorie ameublement = new Categorie("Ameublement");
	        categorieDao.creer(ameublement);
	        Categorie vetement = new Categorie("Vêtement");
	        categorieDao.creer(vetement);
	        Categorie sportLoisirs = new Categorie("Sport&Loisirs");
	        categorieDao.creer(sportLoisirs);
	        
	        this.retraitDao = daoFactory.getRetraitDao();
	        Retrait retrait = new Retrait("rue", "79000", "Niort");
	        retraitDao.creer(retrait);
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article1 = new ArticleVendu("article1", "test", dt, dt, 50, "EC", vendeur, informatique , retrait);
	        articleDao.creer(article1);
	        

	        this.enchereDao = daoFactory.getEnchereDao();
	        Enchere enchere = new Enchere(dt, 100 , article1 ,encherisseur);
	        enchereDao.creer(enchere);
	        
	        
	        
	    }

}
