package fr.eni.encheres.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.beans.ArticleVendu;
import fr.eni.encheres.beans.Categorie;
import fr.eni.encheres.dao.ArticleVenduDao;
import fr.eni.encheres.dao.CategorieDao;
import fr.eni.encheres.dao.DAOFactory;


public class PrechargementFilter implements Filter {

	private DAOFactory daoFactory;
	private ArticleVenduDao articleDao;
	private CategorieDao categorieDao;


    public void init( FilterConfig config ) throws ServletException {
    			// Initiation de la DAOFactory
    			this.daoFactory = DAOFactory.getInstance();
    			// Creation des objetsDao pour acc�der au methode lister()
    			this.articleDao = daoFactory.getArticleVenduDao();
    			this.categorieDao = daoFactory.getCategorieDao();
    			
    			
    }

    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException,
            ServletException {
        /* Cast de l'objet request */
        HttpServletRequest request = (HttpServletRequest) req;

        /* R�cup�ration de la session depuis la requ�te */
        HttpSession session = request.getSession();

        /*
         * Si la map des utilisateurs n'existe pas en session, alors l'utilisateur se
         * connecte pour la premi�re fois et nous devons pr�charger en session
         * les infos contenues dans la BDD.
         */
        if ( session.getAttribute( "articles" ) == null ) {
            /*
             * R�cup�ration de la liste des utilisateur existants, et enregistrement
             * en session
             */
            List<ArticleVendu> articles = articleDao.lister();
            Map<Long, ArticleVendu> mapArticles = new HashMap<Long, ArticleVendu>();
            for ( ArticleVendu a : articles ) {
            	mapArticles.put( a.getNoArticle(), a );
            }
            System.out.println("map dans filter" + mapArticles);
            session.setAttribute( "articles", mapArticles );
        }

        //De m�me pour les cat�gories
        if ( session.getAttribute( "categories" ) == null ) {
            List<Categorie> categories = categorieDao.lister();
            Map<Long, Categorie> mapCategories = new HashMap<Long, Categorie>();
            for ( Categorie c : categories ) {
            	mapCategories.put( c.getNoCategorie(), c );
            }
            System.out.println("map dans filter" + mapCategories);
            session.setAttribute( "categories", mapCategories );
        }
        
        //Afin de pouvoir afficher la liste des articles en cours de vente par d�faut
        if( request.getAttribute( "articlesAffiches" ) == null) {
        	List<ArticleVendu> articles = articleDao.lister();
            Map<Long, ArticleVendu> mapArticlesAffiches = new HashMap<Long, ArticleVendu>();
        	for (ArticleVendu a : articles){
        		if(a.getEtatVente().equals("EC")){
        			mapArticlesAffiches.put(a.getNoArticle(), a);      			
        		}
        	}
        	request.setAttribute( "articlesAffiches", mapArticlesAffiches );
        }
        
        
        /* Pour terminer, poursuite de la requ�te en cours */
        chain.doFilter( request, res );
    }

    public void destroy() {
    }
}