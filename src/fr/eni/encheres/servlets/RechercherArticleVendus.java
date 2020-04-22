package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import fr.eni.encheres.beans.ArticleVendu;
import fr.eni.encheres.beans.Categorie;
import fr.eni.encheres.beans.Enchere;
import fr.eni.encheres.beans.Utilisateur;
import fr.eni.encheres.dao.ArticleVenduDao;
import fr.eni.encheres.dao.CategorieDao;
import fr.eni.encheres.dao.DAOFactory;
import fr.eni.encheres.dao.EnchereDao;

@WebServlet("/rechercherArticleVendus")
public class RechercherArticleVendus extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOFactory daoFactory;
	private CategorieDao categorieDao;
	private ArticleVenduDao articleDao;
	private EnchereDao enchereDao;
	private Map<Long, ArticleVendu> articles = new HashMap<Long, ArticleVendu>();
	private List<Categorie> categories;
	private List<Enchere> encheres;
	private Utilisateur utilisateurConnecte;

	// Methode initi� au lancement de l'application
	public void init() throws ServletException {
		// Initiation de la DAOFactory
		this.daoFactory = DAOFactory.getInstance();
		// Creation des objetsDao pour acc�der au methode lister()
		this.categorieDao = daoFactory.getCategorieDao();
		this.articleDao = daoFactory.getArticleVenduDao();
		this.enchereDao = daoFactory.getEnchereDao();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		rechercherArticleVendus(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		rechercherArticleVendus(request, response);
	}

	protected void rechercherArticleVendus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// R�cup�ration des listes d'article, d'ench�res et de cat�gories
		for (ArticleVendu a : articleDao.lister()) {
			this.articles.put(a.getNoArticle(), a);
		}
		this.categories = categorieDao.lister();
		this.encheres = enchereDao.lister();

		// Passage de la liste de cat�gorie en Attribute pour pouvoir la
		// r�cup�rer dans la JSP

		request.setAttribute("categories", categories);

		// On r�cup�re l'utilisateur stock� en session
		this.utilisateurConnecte = (Utilisateur) request.getSession().getAttribute("utilisateurConnecte");

			if (utilisateurConnecte == null) {
				// Si pas d'utilisateur connect�, on affiche la liste d'article par d�faut
				afficherListeNonConnecte(request, response);
			} else {
				// Si d'utilisateur connect�, on affiche la liste d'article en tenant compte des filtres
				afficherListeConnecte(request, response);
			}

		// Affichage de la page d'accueil
		this.getServletContext().getRequestDispatcher("/accueil.jsp").forward(request, response);
	}

	protected void afficherListeNonConnecte(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// On initialise une liste d'ench�res
		Map<Long, ArticleVendu> articlesAffiches = new HashMap<Long, ArticleVendu>();

		// R�cup�ration des champs du formulaire de recherche
		String recherche = request.getParameter("recherche");
		String categorie = request.getParameter("categorie");

		// Par d�faut, on afficher la liste des articles en cours parmis tous
		// les articles
		articlesAffiches = rechercherParEtatAchat(true, false, false, this.articles);
		// Puis on filtre par cat�gorie
		articlesAffiches = rechercherParCategorie(categorie, articlesAffiches);
		// Puis on filtre par mot recherch�
		articlesAffiches = rechercherParMotCle(recherche, articlesAffiches);

		// On affecte la map d'article � la request
		request.setAttribute("articlesAffiches", articlesAffiches);
	}

	protected void afficherListeConnecte(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// On initialise une liste d'ench�res
		Map<Long, ArticleVendu> articlesAffiches = new HashMap<Long, ArticleVendu>();

		// R�cup�ration des champs du formulaire de recherche
		String recherche = request.getParameter("recherche");
		String categorie = request.getParameter("categorie");
		String type = request.getParameter("type");
		boolean ventesEnCours = request.getParameter("ventesEnCours") != null;
		boolean ventesNonDebutees = request.getParameter("ventesNonDebutees") != null;
		boolean ventesTerminees = request.getParameter("ventesTerminees") != null;
		boolean encheresOuvertes = request.getParameter("encheresOuvertes") != null;
		boolean encheresEnCours = request.getParameter("encheresEnCours") != null;
		boolean encheresRemportees = request.getParameter("encheresRemportees") != null;

		// on filtre en fonction du bouton radio, on filtre par les checkboxes
		// coch�es correspondantes
		if (type.equals("achats")) {
			articlesAffiches = rechercherParEtatAchat(encheresOuvertes, encheresEnCours, encheresRemportees,
					this.articles);
		} else {
			articlesAffiches = rechercherParEtatVente(ventesEnCours, ventesNonDebutees, ventesTerminees, this.articles);
		}
		// Puis on filtre par cat�gorie
		articlesAffiches = rechercherParCategorie(categorie, articlesAffiches);
		// Puis par mot recherch�
		articlesAffiches = rechercherParMotCle(recherche, articlesAffiches);

		// On affecte la map d'article � la request
		request.setAttribute("articlesAffiches", articlesAffiches);
	}

	private Map<Long, ArticleVendu> rechercherParCategorie(String categorie,
			Map<Long, ArticleVendu> ArticleVendusAFiltrer) {
		Map<Long, ArticleVendu> articlesVendusOk = new HashMap<Long, ArticleVendu>();
		if (categorie != null && !categorie.equals("toutes")) {
			for (Map.Entry<Long, ArticleVendu> a : articlesVendusOk.entrySet()) {
				if (a.getValue().getCategorieArticle().getNoCategorie() == Long.parseLong(categorie)) {
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
			for (Map.Entry<Long, ArticleVendu> a : articlesVendusOk.entrySet()) {
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
		// Si la case "ench�res ouvertes" est coch�e
		if (ouverte) {
			for (Map.Entry<Long, ArticleVendu> a : ArticleVendusAFiltrer.entrySet()) {
				// On ajoute � la map d'articles � afficher si l'etat est "OU"
				if (a.getValue().getEtatVente().equals("EC")) {
					articlesVendusOk.put(a.getKey(), a.getValue());
				}
			}
		}
		// Si la case "mes ench�res en cours" est coch�e
		if (encheres != null && enCours) {
			for (Enchere e : encheres) {
				// Si une enchere � comme encherisseur l'utilisateur connect�,
				// alors on ajoute l'article de l'ench�re � la MAP
				if (e.getEncherisseur().getNoUtilisateur() == this.utilisateurConnecte.getNoUtilisateur() && e.getArticle().getEtatVente().equals("EC")) {
					articlesVendusOk.put(e.getArticle().getNoArticle(), e.getArticle());
				}
			}
		}
		// Si la case "mes ench�res remport�es" est coch�e
		if (remportee) {
			for (Map.Entry<Long, ArticleVendu> a : ArticleVendusAFiltrer.entrySet()) {
				System.out.println(a.getValue().getAcheteur());
				// Si un article � pour acheteur l'utilisateur connect�, alors
				// on ajoute l'article � la MAP
				if (a.getValue().getAcheteur() != null && a.getValue().getAcheteur().getNoUtilisateur() == this.utilisateurConnecte.getNoUtilisateur() && a.getValue().getEtatVente().equals("TE")) {
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
			// On affiche des r�sultats uniquement si les articles appartiennent
			// � l'utilisateur
			if (a.getValue().getVendeur().getNoUtilisateur() == this.utilisateurConnecte.getNoUtilisateur()) {
				// Si "Mes ventes en cours" est coch�e et que la date de debut
				// d'ench�re est pass�e
				if (enCours && a.getValue().getEtatVente().equals("EC")) {
					articlesVendusOk.put(a.getKey(), a.getValue());
				}
				// Si "ventes non debut�es" est coch�e et que la date de debut
				// d'ench�re n'est pas encore pass�e
				if (nonDebutee && a.getValue().getEtatVente().equals("ND")) {
					articlesVendusOk.put(a.getKey(), a.getValue());
				}
				// Si "ventes termin�es" est coch�e et que la date de fin
				// d'ench�re est pass�e
				if (terminee && a.getValue().getEtatVente().equals("TE")) {
					articlesVendusOk.put(a.getKey(), a.getValue());
				}
			}
		}
		return articlesVendusOk;
	}
}