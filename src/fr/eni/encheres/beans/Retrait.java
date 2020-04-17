package fr.eni.encheres.beans;

public class Retrait {
	private Long noRetrait;
	private String rue;
	private String codePostal;
	private String ville;
	
	/**
	 * Constructeur par défaut
	 */
	public Retrait() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructeur avec paramètres
	 * @param rue
	 * @param codePostal
	 * @param ville
	 * @param article
	 */
	public Retrait(String rue, String codePostal, String ville) {
		super();
		setRue(rue);
		setCodePostal(codePostal);
		setVille(ville);
	}

	/**
	 * @return the rue
	 */
	public String getRue() {
		return rue;
	}

	/**
	 * @param rue the rue to set
	 */
	public void setRue(String rue) {
		this.rue = rue;
	}

	/**
	 * @return the codePostal
	 */
	public String getCodePostal() {
		return codePostal;
	}

	/**
	 * @param codePostal the codePostal to set
	 */
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}

	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	/**
	 * @return the noRetrait
	 */
	public Long getNoRetrait() {
		return noRetrait;
	}

	/**
	 * @param noRetrait the noRetrait to set
	 */
	public void setNoRetrait(Long noRetrait) {
		this.noRetrait = noRetrait;
	}
	
	

}
