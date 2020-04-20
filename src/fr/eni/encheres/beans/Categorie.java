package fr.eni.encheres.beans;

public class Categorie {
	private Long noCategorie;
	private String libelle;
	
	/**
	 * Constructeru par défaut
	 */
	public Categorie() {
		super();
	}

	/**
	 * Constructeur avec paramètres
	 * @param libelle
	 */
	public Categorie(String libelle) {
		super();
		setLibelle(libelle);
	}

	/**
	 * @return the noCategorie
	 */
	public Long getNoCategorie() {
		return noCategorie;
	}

	/**
	 * @param noCategorie the noCategorie to set
	 */
	public void setNoCategorie(Long noCategorie) {
		this.noCategorie = noCategorie;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Categorie [noCategorie=" + noCategorie + ", libelle=" + libelle + "]";
	}
	
}
