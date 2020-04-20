package fr.eni.encheres.beans;

import org.joda.time.DateTime;

public class Enchere {
	private DateTime dateEnchere;
	private int montantEnchere;
	private ArticleVendu article;
	private Utilisateur encherisseur;
	
	
	/**
	 * Constructeur par défaut
	 */
	public Enchere() {
		super();
	}
	/**
	 * Constructeur avec paramètres
	 * @param dateEnchere
	 * @param montantEnchere
	 * @param article
	 * @param encherisseur
	 */
	public Enchere(DateTime dateEnchere, int montantEnchere, ArticleVendu article, Utilisateur encherisseur) {
		super();
		setDateEnchere(dateEnchere);
		setMontantEnchere(montantEnchere);
		setArticle(article);
		setEncherisseur(encherisseur);
	}
	/**
	 * @return the dateEnchere
	 */
	public DateTime getDateEnchere() {
		return dateEnchere;
	}
	/**
	 * @param dateEnchere the dateEnchere to set
	 */
	public void setDateEnchere(DateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}
	/**
	 * @return the montant_enchere
	 */
	public int getMontantEnchere() {
		return montantEnchere;
	}
	/**
	 * @param montantEnchere the montantEnchere to set
	 */
	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}
	/**
	 * @return the article
	 */
	public ArticleVendu getArticle() {
		return article;
	}
	/**
	 * @param article the article to set
	 */
	public void setArticle(ArticleVendu article) {
		this.article = article;
	}
	/**
	 * @return the encherisseur
	 */
	public Utilisateur getEncherisseur() {
		return encherisseur;
	}
	/**
	 * @param encherisseur the encherisseur to set
	 */
	public void setEncherisseur(Utilisateur encherisseur) {
		this.encherisseur = encherisseur;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Enchere [dateEnchere=" + dateEnchere + ", montantEnchere=" + montantEnchere + ", article=" + article
				+ ", encherisseur=" + encherisseur + "]";
	}
	
	
}
