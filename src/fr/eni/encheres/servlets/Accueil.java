package fr.eni.encheres.servlets;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;


import fr.eni.encheres.beans.ArticleVendu;
import fr.eni.encheres.beans.Categorie;
import fr.eni.encheres.beans.Enchere;
import fr.eni.encheres.beans.Utilisateur;
import fr.eni.encheres.dao.DAOFactory;
import fr.eni.encheres.dao.EnchereDao;

public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOFactory daoFactory;

	private EnchereDao enchereDao;
	private Map<Long, ArticleVendu> articles ;
	private Map<Long, Categorie> categories;
	private List<Enchere> encheres;
	private Utilisateur utilisateurConnecte;



	public void init() throws ServletException {
		// Initiation de la DAOFactory
		this.daoFactory = DAOFactory.getInstance();
		// Creation des objetsDao pour accéder au methode lister()

		this.enchereDao = daoFactory.getEnchereDao();


	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("TEST doGet");

		rechercherArticleVendus(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		rechercherArticleVendus(request, response);
	}

	protected void rechercherArticleVendus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récupération des listes d'article, d'enchères et de catégories
		//for (ArticleVendu a : articleDao.lister()) {
		//	this.articles.put(a.getNoArticle(), a);
		//}
		/*Recupération de la session */
		HttpSession session = request.getSession();
		/* Récupération de la map des articles dans la session */
        this.articles = (HashMap<Long, ArticleVendu>) session.getAttribute( "articles" );
        this.categories = (HashMap<Long, Categorie>) session.getAttribute( "categories" );
        System.out.println("map dans servlet" + articles);
        /* Si aucune map n'existe, alors initialisation d'une nouvelle map */
        if ( articles == null ) {
            this.articles = new HashMap<Long, ArticleVendu>();
        }
		if (categories == null) {
			this.categories = new HashMap<Long, Categorie>();
		}
    
		this.encheres = enchereDao.lister();

		// On récupère l'utilisateur stocké en session
		this.utilisateurConnecte = (Utilisateur) request.getSession().getAttribute("utilisateurConnecte");

		if (utilisateurConnecte == null) {
			// Si pas d'utilisateur connecté, on affiche la liste d'article par
			// défaut
			afficherListeNonConnecte(request, response);
		} else {
			// Si d'utilisateur connecté, on affiche la liste d'article en
			// tenant compte des filtres
			afficherListeConnecte(request, response);
		}

		// Affichage de la page d'accueil
		this.getServletContext().getRequestDispatcher("/accueil.jsp").forward(request, response);
	}

	protected void afficherListeNonConnecte(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// On initialise une liste d'enchères
		Map<Long, ArticleVendu> articlesAffiches = new HashMap<Long, ArticleVendu>();

		// Récupération des champs du formulaire de recherche
		String recherche = request.getParameter("recherche");
		String categorie = request.getParameter("categorie");

		// Par défaut, on afficher la liste des articles en cours parmis tous les articles
		articlesAffiches = rechercherParEtatAchat(true, false, false, articles);
		// Puis on filtre par catégorie
		articlesAffiches = rechercherParCategorie(categorie, articlesAffiches);
		// Puis on filtre par mot recherché
		articlesAffiches = rechercherParMotCle(recherche, articlesAffiches);

		// On envoi les attribus dans la request
		// on envoi les résultats de la recherche précédente pour pouvoir les
		// réafficher au rechergement de la page
		request.setAttribute("recherche", recherche);
		request.setAttribute("categorie", categorie);
		// on envoi la liste des articles à afficher à la session
		request.setAttribute("articlesAffiches", articlesAffiches);
	}

	protected void afficherListeConnecte(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// On initialise une liste d'enchères
		Map<Long, ArticleVendu> articlesAffiches = new HashMap<Long, ArticleVendu>();

		// Récupération des champs du formulaire de recherche
		String recherche = request.getParameter("recherche");
		String categorie = request.getParameter("categorie");
		String type = request.getParameter("type");
		boolean ventesEnCours = request.getParameter("ventesEnCours") != null;
		boolean ventesNonDebutees = request.getParameter("ventesNonDebutees") != null;
		boolean ventesTerminees = request.getParameter("ventesTerminees") != null;
		boolean encheresOuvertes = request.getParameter("encheresOuvertes") != null;
		boolean encheresEnCours = request.getParameter("encheresEnCours") != null;
		boolean encheresRemportees = request.getParameter("encheresRemportees") != null;


		//Si c'est le premier affichage de la page, on met coche par défaut
		if (type == null || type.length() == 0){
			type = "achats";
			encheresOuvertes = true;
		}	
		
		
		// on filtre en fonction du bouton radio, on filtre par les checkboxes
		// cochées correspondantes
		if (type.equals("achats")) {
			articlesAffiches = rechercherParEtatAchat(encheresOuvertes, encheresEnCours, encheresRemportees,
					this.articles);
		} else {
			articlesAffiches = rechercherParEtatVente(ventesEnCours, ventesNonDebutees, ventesTerminees, this.articles);
		}
		// Puis on filtre par catégorie
		articlesAffiches = rechercherParCategorie(categorie, articlesAffiches);
		// Puis par mot recherché
		articlesAffiches = rechercherParMotCle(recherche, articlesAffiches);

		// On envoi les attribus dans la request
		// on envoi les résultats de la recherche précédente pour pouvoir les
		// réafficher au rechergement de la page
		request.setAttribute("recherche", recherche);
		request.setAttribute("categorie", categorie);
		request.setAttribute("type", type);
		request.setAttribute("ventesEnCours", ventesEnCours);
		request.setAttribute("ventesNonDebutees", ventesNonDebutees);
		request.setAttribute("ventesTerminees", ventesTerminees);
		request.setAttribute("encheresOuvertes", encheresOuvertes);
		request.setAttribute("encheresEnCours", encheresEnCours);
		request.setAttribute("encheresRemportees", encheresRemportees);
		
		// on envoi la liste des articles à afficher à la session
		request.setAttribute("articlesAffiches", articlesAffiches);
	}

	private Map<Long, ArticleVendu> rechercherParCategorie(String categorie,
			Map<Long, ArticleVendu> ArticleVendusAFiltrer) {
		Map<Long, ArticleVendu> articlesVendusOk = new HashMap<Long, ArticleVendu>();
		if (categorie != null && !categorie.equals("0")) {
			for (Map.Entry<Long, ArticleVendu> a : ArticleVendusAFiltrer.entrySet()) {
				if (a.getValue().getCategorieArticle().getNoCategorie().toString().equals(categorie)) {
					articlesVendusOk.put(a.getKey(), a.getValue());
				}
			}
			return articlesVendusOk;
		} else {
			return ArticleVendusAFiltrer;
		}

	}

	private Map<Long, ArticleVendu> rechercherParMotCle(String recherche,
			Map<Long, ArticleVendu> ArticleVendusAFiltrer) {
		Map<Long, ArticleVendu> articlesVendusOk = new HashMap<Long, ArticleVendu>();
		if (recherche != null && recherche.trim().length() != 0) {
			for (Map.Entry<Long, ArticleVendu> a : ArticleVendusAFiltrer.entrySet()) {
				if (a.getValue().getNomArticle().toLowerCase().contains(recherche.toLowerCase())) {
					articlesVendusOk.put(a.getKey(), a.getValue());
				}
			}
			return articlesVendusOk;
		} else {
			return ArticleVendusAFiltrer;
		}
	}

	private Map<Long, ArticleVendu> rechercherParEtatAchat(boolean ouverte, boolean enCours, boolean remportee,
			Map<Long, ArticleVendu> ArticleVendusAFiltrer) {
		Map<Long, ArticleVendu> articlesVendusOk = new HashMap<Long, ArticleVendu>();
		// Si la case "enchères ouvertes" est cochée
		if (ouverte) {
			for (Map.Entry<Long, ArticleVendu> a : ArticleVendusAFiltrer.entrySet()) {
				// On ajoute à la map d'articles à afficher si l'etat est "OU"
				if (a.getValue().getEtatVente().equals("EC")) {
					articlesVendusOk.put(a.getKey(), a.getValue());
				}
			}
		}
		// Si la case "mes enchères en cours" est cochée
		if (encheres != null && enCours) {
			for (Enchere e : encheres) {
				// Si une enchere à comme encherisseur l'utilisateur connecté,
				// alors on ajoute l'article de l'enchère à la MAP
				if (e.getEncherisseur().getNoUtilisateur() == this.utilisateurConnecte.getNoUtilisateur()
						&& e.getArticle().getEtatVente().equals("EC")) {
					articlesVendusOk.put(e.getArticle().getNoArticle(), e.getArticle());
				}
			}
		}
		// Si la case "mes enchères remportées" est cochée
		if (remportee) {
			for (Map.Entry<Long, ArticleVendu> a : ArticleVendusAFiltrer.entrySet()) {
				// Si un article à pour acheteur l'utilisateur connecté, alors
				// on ajoute l'article à la MAP
				if (a.getValue().getAcheteur() != null
						&& a.getValue().getAcheteur().getNoUtilisateur() == this.utilisateurConnecte.getNoUtilisateur()
						&& a.getValue().getEtatVente().equals("TE")) {
					articlesVendusOk.put(a.getKey(), a.getValue());

				}
			}
		}
		return articlesVendusOk;
	}

	private Map<Long, ArticleVendu> rechercherParEtatVente(boolean enCours, boolean nonDebutee, boolean terminee,
			Map<Long, ArticleVendu> ArticleVendusAFiltrer) {
		Map<Long, ArticleVendu> articlesVendusOk = new HashMap<Long, ArticleVendu>();
		// Initialisation de la date du jour
		DateTime dateDuJour = new DateTime();
		for (Map.Entry<Long, ArticleVendu> a : ArticleVendusAFiltrer.entrySet()) {
			// On affiche des résultats uniquement si les articles appartiennent
			// à l'utilisateur
			if (a.getValue().getVendeur().getNoUtilisateur() == this.utilisateurConnecte.getNoUtilisateur()) {
				// Si "Mes ventes en cours" est cochée et que la date de debut
				// d'enchère est passée
				if (enCours && a.getValue().getEtatVente().equals("EC")) {
					articlesVendusOk.put(a.getKey(), a.getValue());
				}
				// Si "ventes non debutées" est cochée et que la date de debut
				// d'enchère n'est pas encore passée
				if (nonDebutee && a.getValue().getEtatVente().equals("ND")) {
					articlesVendusOk.put(a.getKey(), a.getValue());
				}
				// Si "ventes terminées" est cochée et que la date de fin
				// d'enchère est passée
				if (terminee && a.getValue().getEtatVente().equals("TE")) {
					articlesVendusOk.put(a.getKey(), a.getValue());
				}
			}
		}
		return articlesVendusOk;
	}
}
