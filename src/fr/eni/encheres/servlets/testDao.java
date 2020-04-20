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
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article2 = new ArticleVendu("article2", "test", dt, dt, 50, "EC", vendeur, sportLoisirs , retrait);
	        articleDao.creer(article2);
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article3 = new ArticleVendu("article3", "test", dt, dt, 50, "TE", vendeur, ameublement , retrait);
	        articleDao.creer(article3);
	        
	        this.articleDao = daoFactory.getArticleVenduDao();
	        ArticleVendu article4 = new ArticleVendu("article4", "test", dt, dt, 50, "TE", vendeur, vetement , retrait);
	        articleDao.creer(article4);
	        
	        
	        this.enchereDao = daoFactory.getEnchereDao();
	        Enchere enchere = new Enchere(dt, 100 , article1 ,encherisseur);
	        enchereDao.creer(enchere);
	        
	        /*******************************************Test des methodes modifier********************************************************/
	        
	        vendeur.setNom("vendeur modifié");
	        vendeur.setCredit(100);
	        utilisateurDao.modifier(vendeur);
	        
	        Categorie ajout = new Categorie("ajout");
	        categorieDao.creer(ajout);
	        ajout.setLibelle("modifiée");
	        categorieDao.modifier(ajout);
	        
	        retrait.setRue("26 Rue Irène Joliot Curie");
	        retraitDao.modifier(retrait);
	        
	        article1.setEtatVente("OK");
	        articleDao.modifier(article1);
	        
	        article1.setAcheteur(acheteur);
	        article1.setPrixVente(300);
	        article1.setEtatVente("VE");
	        articleDao.modifier(article1);
	                
	        enchere.setMontantEnchere(300);
	        enchereDao.modifier(enchere);
	        
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
		    Utilisateur utilisateurTrouve = utilisateurDao.trouver(acheteur.getNoUtilisateur());
		    System.out.println(utilisateurTrouve.toString());
		    
		    Categorie catTrouvee = categorieDao.trouver(informatique.getNoCategorie());
		    System.out.println(catTrouvee.toString());
		    
		    Retrait retTrouve = retraitDao.trouver(retrait.getNoRetrait());
		    System.out.println(retTrouve.toString());
		    
		    ArticleVendu artTrouve = articleDao.trouver(article1.getNoArticle());
		    System.out.println(artTrouve.toString());
		    
		    Enchere enchereTrouvee = enchereDao.trouver(enchere.getArticle().getNoArticle(),enchere.getEncherisseur().getNoUtilisateur());
		    System.out.println(enchereTrouvee);
		    
		    /*******************************************Test des methodes supprimer********************************************************/
		    utilisateurDao.supprimer(vendeur);
		    categorieDao.supprimer(ajout);
		    retraitDao.supprimer(retrait);
		    enchereDao.supprimer(enchere);
		    articleDao.supprimer(article1);
		    
		    
		    /******************************************Test envoi paramètres****************************************************************/
		    request.setAttribute( "categories", categories );
		    request.setAttribute( "encheres", encheres );
	    	this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil.jsp" ).forward( request, response );
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
        this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil.jsp" ).forward( request, response );
	    

	    }
}
