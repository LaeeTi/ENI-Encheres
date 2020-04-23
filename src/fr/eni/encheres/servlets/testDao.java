package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

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
	    	DateTime dt = new DateTime();
	    	
	        
	    	/*******************************************Test des methodes creer********************************************************/
	    	//Création d'une instance objetDao via la DAOFactory qui va nous servir pour créer les objets
	        this.utilisateurDao = daoFactory.getUtilisateurDao();
	        //Creation d'un objet (bean)
	        Utilisateur user1 = new Utilisateur("user1", "nom", "prenom", "email", "telephone", "rue",
	    			"cp", "ville", "a", 0, false);
	        //Creation de l'objet en base de donnée via la methode creer de l'objet DAO
	        utilisateurDao.creer(user1);
	        //Ajout d'autres objets
	        Utilisateur user2 = new Utilisateur("user2", "nom1", "prenom1", "email1", "telephone1", "rue1",
	    			"cp1", "ville1", "a", 0, false);
	        utilisateurDao.creer(user2);
	        Utilisateur user3 = new Utilisateur("user3", "nom2", "prenom2", "email2", "telephone2", "rue2",
	    			"cp2", "ville2", "motDePasse2", 0, false);
	        utilisateurDao.creer(user3);
	        Utilisateur administrateur = new Utilisateur("a", "admin", "adm", "email2", "telephone2", "rue2",
	    			"cp2", "ville2", "a", 0, true);
	        utilisateurDao.creer(administrateur);
	        Utilisateur user4 = new Utilisateur("user4", "vendeur", "adm", "email2", "telephone2", "rue2",
	    			"cp2", "ville2", "a", 0, false);
	        utilisateurDao.creer(user4);
	        Utilisateur user5 = new Utilisateur("user5", "vendeur", "adm", "email2", "telephone2", "rue2",
	    			"cp2", "ville2", "a", 0, false);
	        utilisateurDao.creer(user5);
	        
	        
 
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
	        
	        this.retraitDao = daoFactory.getRetraitDao();
	        Retrait retrait2 = new Retrait("rue2", "79000", "Niort");
	        retraitDao.creer(retrait2);
	        
	        this.retraitDao = daoFactory.getRetraitDao();
	        Retrait retrait3 = new Retrait("rue2", "79000", "Niort");
	        retraitDao.creer(retrait3);
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article1 = new ArticleVendu("clavier", "test", new DateTime(2020,03,25,0,0), new DateTime(2020,05,25,0,0), 50, "EC", user1, informatique , retrait);
	        articleDao.creer(article1);
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article2 = new ArticleVendu("velo", "test", new DateTime(2020,03,25,0,0), new DateTime(2020,04,12,0,0), 50, "TE", user1, sportLoisirs , retrait);
	        articleDao.creer(article2);
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article3 = new ArticleVendu("buffet", "test", new DateTime(2020,04,25,0,0), new DateTime(2020,05,25,0,0), 50, "ND", user1, ameublement , retrait);
	        articleDao.creer(article3);
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article4 = new ArticleVendu("robe", "test", new DateTime(2020,03,25,0,0), new DateTime(2020,05,25,0,0), 50, "EC", user2, vetement , retrait);
	        articleDao.creer(article4);
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article5 = new ArticleVendu("ordinateur", "test", new DateTime(2020,03,25,0,0), new DateTime(2020,04,12,0,0), 50, "TE", user2, informatique , retrait);
	        articleDao.creer(article5);
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article6 = new ArticleVendu("buffet", "test", new DateTime(2020,04,25,0,0), new DateTime(2020,05,25,0,0), 50, "ND", user2, sportLoisirs , retrait);
	        articleDao.creer(article6);
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article7 = new ArticleVendu("table", "test", new DateTime(2020,03,25,0,0), new DateTime(2020,05,25,0,0), 50, "EC", user2, ameublement , retrait);
	        articleDao.creer(article7);
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article8 = new ArticleVendu("pentalon", "test", new DateTime(2020,03,25,0,0), new DateTime(2020,04,12,0,0), 50, "TE", user1, vetement , retrait);
	        articleDao.creer(article8);
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article9 = new ArticleVendu("souris", "test", new DateTime(2020,04,25,0,0), new DateTime(2020,05,25,0,0), 50, "EC", user1, informatique , retrait);
	        articleDao.creer(article9);
	       
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article10 = new ArticleVendu("souris", "test", new DateTime(2020,04,25,0,0), new DateTime(2020,05,25,0,0), 50, "EC", user1, informatique , retrait);
	        articleDao.creer(article10);
	        
	        
	        
	        this.enchereDao = daoFactory.getEnchereDao();
	        Enchere enchere1 = new Enchere(new DateTime(2020,03,27,0,0), 100 , article2 ,user2);
	        enchereDao.creer(enchere1);
	        Enchere enchere2 = new Enchere(new DateTime(2020,03,27,0,0), 100 , article5 ,user1);
	        enchereDao.creer(enchere2);
	        Enchere enchere3 = new Enchere(new DateTime(2020,03,27,0,0), 100 , article8 ,user2);
	        enchereDao.creer(enchere3);
	        Enchere enchere4 = new Enchere(new DateTime(2020,03,25,0,0), 100 , article1 ,user2);
	        enchereDao.creer(enchere4);
	        Enchere enchere5 = new Enchere(new DateTime(2020,03,25,0,0), 100 , article4 ,user1);
	        enchereDao.creer(enchere5);
	        Enchere enchere6 = new Enchere(new DateTime(2020,03,25,0,0), 100 , article4 ,user3);
	        enchereDao.creer(enchere6);
	        
	        /*******************************************Test des methodes modifier********************************************************/
	        
	        user3.setCredit(100);
	        utilisateurDao.modifier(user3);
	        
	        Categorie ajout = new Categorie("ajout");
	        categorieDao.creer(ajout);
	        ajout.setLibelle("modifiée");
	        categorieDao.modifier(ajout);
	        
	        retrait.setRue("26 Rue Irène Joliot Curie");
	        retraitDao.modifier(retrait);
	        
	        article9.setEtatVente("ND");
	        articleDao.modifier(article1);
	        
	        article2.setAcheteur(user2);
	        article2.setPrixVente(300);
	        articleDao.modifier(article2);
	        article5.setAcheteur(user1);
	        article5.setPrixVente(500);
	        articleDao.modifier(article5);
	        article8.setAcheteur(user2);
	        article8.setPrixVente(700);
	        articleDao.modifier(article8);
	                
	        enchere4.setMontantEnchere(300);
	        enchereDao.modifier(enchere4);
	        
	        /*******************************************Test des methodes lister********************************************************/
	        System.out.println("Liste des utilisateurs :");
	        List<Utilisateur> utilisateurs = utilisateurDao.lister();
	        for(Utilisateur u : utilisateurs){
	        	System.out.println(u.toString());
	        }
	        System.out.println("Liste des catégories :");
	        List<Categorie> categories = categorieDao.lister();
	        for(Categorie c : categories){
	        	System.out.println(c.toString());
	        }
	        System.out.println("Liste des retraits :");
	        List<Retrait> retraits = retraitDao.lister();
	        for(Retrait r : retraits){
	        	System.out.println(r.toString());
	        }
	        System.out.println("Liste des articles :");
	        List<ArticleVendu> articles = articleDao.lister();
	        for(ArticleVendu a : articles){
	        	System.out.println(a.toString());
	        }
	        System.out.println("Liste des enchères :");
	        List<Enchere> encheres = enchereDao.lister();
	        for(Enchere e : encheres){
	        	System.out.println(e.toString());
	        }
	        
	        
	        /*******************************************Test des methodes trouver********************************************************/
		    Utilisateur utilisateurTrouve = utilisateurDao.trouver(user1.getNoUtilisateur());
		    System.out.println(utilisateurTrouve.toString());
		    
		    Categorie catTrouvee = categorieDao.trouver(informatique.getNoCategorie());
		    System.out.println(catTrouvee.toString());
		    
		    Retrait retTrouve = retraitDao.trouver(retrait.getNoRetrait());
		    System.out.println(retTrouve.toString());
		    
		    ArticleVendu artTrouve = articleDao.trouver(article1.getNoArticle());
		    System.out.println(artTrouve.toString());
		    
		    Enchere enchereTrouvee = enchereDao.trouver(enchere1.getArticle().getNoArticle(),enchere1.getEncherisseur().getNoUtilisateur());
		    System.out.println(enchereTrouvee);
		    
		    /*******************************************Test des methodes supprimer********************************************************/
		    utilisateurDao.supprimer(user5);
		    categorieDao.supprimer(ajout);
		    retraitDao.supprimer(retrait3);
		    enchereDao.supprimer(enchere6);
		    articleDao.supprimer(article10);
		    
		    
		    /******************************************Test envoi paramètres****************************************************************/
		    request.setAttribute( "categories", categories );
		    request.setAttribute( "encheres", encheres );
	    	//this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil.jsp" ).forward( request, response );
	    }   
	    
	    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {   
        String recherche = request.getParameter("recherche");
        String categorie = request.getParameter("categorie");
        String type = request.getParameter("type");
        String encheresOuvertes = request.getParameter("encheresOuvertes");
        String encheresEnCours = request.getParameter("encheresEnCours");
        String encheresRemportees = request.getParameter("encheresRemportees");
        String ventesEnCours = request.getParameter("ventesEnCours");
        String ventesNonDebutees = request.getParameter("ventesNonDebutees");
        String ventesTerminees = request.getParameter("ventesTerminees");

        List<Categorie> categories = categorieDao.lister();

        System.out.println(recherche);
        System.out.println(categorie);
        System.out.println(type);
        System.out.println(encheresOuvertes);
        System.out.println(encheresEnCours);
        System.out.println(encheresRemportees);
        System.out.println(ventesEnCours);
        System.out.println(ventesNonDebutees);
        System.out.println(ventesTerminees);
        List<ArticleVendu> articles = articleDao.lister();      
        if(categorie.equals("toutes")){
        	for(ArticleVendu a : articles){
        		System.out.println(a.toString());	
        	}
        } else {
        	for(ArticleVendu a : articles){
        		if(categorie.equals(a.getCategorieArticle().getNoCategorie().toString())){
        			System.out.println(a.toString());	
        		}		
        	}
        }
    
        request.setAttribute( "categories", categories );
       // this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil.jsp" ).forward( request, response );
	    

	    }
}
