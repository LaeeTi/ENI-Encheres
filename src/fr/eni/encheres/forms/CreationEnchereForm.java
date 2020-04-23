package fr.eni.encheres.forms;

import java.util.HashMap;
import java.util.Map;


import fr.eni.encheres.dao.RetraitDao;
import fr.eni.encheres.dao.ArticleVenduDao;


public class CreationEnchereForm {
	private static final String CHAMP_NOM       	= "nomArticle";
    private static final String CHAMP_DESCRIPTION   = "descriptionArticle";
    private static final String CHAMP_CATEGORIE   	= "categorieArticle";
    private static final String CHAMP_IMAGE 		= "imageArticle";
    private static final String CHAMP_MISE_A_PRIX   = "miseAPrixArticle";
    private static final String CHAMP_DEBUT_ENCHERE = "debutEnchereArticle";
    private static final String CHAMP_FIN_ENCHERE   = "finEnchereArticle";
    private static final String CHAMP_RUE    		= "rueRetrait";
    private static final String CHAMP_CODE_POSTAL	= "codePostalRetrait";
    private static final String CHAMP_VILLE    		= "villeRetrait";
    
    private static final int    TAILLE_TAMPON   = 10240;                        // 10ko

    private String              resultat;
    private Map<String, String> erreurs         = new HashMap<String, String>();
    private ArticleVenduDao		articleDao;
    private RetraitDao 			retrait;
    
    public CreationEnchereForm( ArticleVenduDao articleDao ) {
        this.articleDao = articleDao;
    }
    
    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }
    
}
