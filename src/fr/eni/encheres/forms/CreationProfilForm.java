package fr.eni.encheres.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.eni.encheres.dao.DAOException;
import fr.eni.encheres.forms.FormValidationException;

import fr.eni.encheres.beans.Utilisateur;
import fr.eni.encheres.dao.UtilisateurDao;

public class CreationProfilForm {
	private static final String CHAMP_PSEUDO 			= "pseudoUtilisateur";
	private static final String CHAMP_NOM 				= "nomUtilisateur";
	private static final String CHAMP_PRENOM			= "prenomUtilisateur";
	private static final String CHAMP_EMAIL 			= "emailUtilisateur";
	private static final String CHAMP_TELEPHONE 		= "telephoneUtilisateur";
	private static final String CHAMP_RUE 				= "rueUtilisateur";
	private static final String CHAMP_CODE_POSTAL 		= "codePostalUtilisateur";
	private static final String CHAMP_VILLE				= "villeUtilisateur";
	private static final String CHAMP_MOT_DE_PASSE 		= "motDePasseUtilisateur";
	private static final String CHAMP_VERIFICATION_MDP	= "VerificationMotDePasseUtilisateur";

	private String 				resultat;
	private Map<String, String> erreurs 		= new HashMap<String, String>();
	private UtilisateurDao 		utilisateurDao;
	
	public CreationProfilForm( UtilisateurDao utilisateurDao ) {
        this.utilisateurDao = utilisateurDao;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }
	
    public Utilisateur creerUtilisateur( HttpServletRequest request) {
    	String pseudo = request.getParameter("pseudoUtilisateur");
    	String nom = getValeurChamp( request, CHAMP_NOM );
        String prenom = getValeurChamp( request, CHAMP_PRENOM );
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String telephone = getValeurChamp( request, CHAMP_TELEPHONE );
        String rue = getValeurChamp( request, CHAMP_RUE );
        String codePostal = getValeurChamp( request, CHAMP_CODE_POSTAL );
        String ville = getValeurChamp( request, CHAMP_VILLE );
        String motDePasse = getValeurChamp( request, CHAMP_MOT_DE_PASSE );
        String verificationMdp = getValeurChamp( request, CHAMP_VERIFICATION_MDP );
        

        Utilisateur utilisateur = new Utilisateur();

        traiterPseudo( pseudo, utilisateur);
        traiterNom( nom, utilisateur );
        traiterPrenom( prenom, utilisateur );
        traiterEmail( email, utilisateur );
        traiterTelephone( telephone, utilisateur );
        traiterRue( rue, utilisateur );
        traiterCodePostal( codePostal, utilisateur );
        traiterVille( ville, utilisateur );
        traiterMotDePasse( motDePasse, verificationMdp, utilisateur );


        try {
            if ( erreurs.isEmpty() ) {
            	utilisateurDao.creer( utilisateur );
                resultat = "Succès de la création de l'utilisateur.";
            } else {
                resultat = "Échec de la création de l'utilisateur.";
            }
        } catch ( DAOException e ) {
            setErreur( "imprévu", "Erreur imprévue lors de la création." );
            resultat = "Échec de la création du profil : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return utilisateur;
    }
    
    private void traiterPseudo( String pseudo, Utilisateur utilisateur ) {
        try {
            validationPseudo( pseudo );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PSEUDO, e.getMessage() );
        }
        utilisateur.setPseudo( pseudo );
    }
    
    private void traiterNom( String nom, Utilisateur utilisateur ) {
        try {
            validationNom( nom );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_NOM, e.getMessage() );
        }
        utilisateur.setNom( nom );
    }
    
    private void traiterPrenom( String prenom, Utilisateur utilisateur ) {
        try {
            validationPrenom( prenom );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PRENOM, e.getMessage() );
        }
        utilisateur.setPrenom( prenom );
    }
    
    private void traiterEmail( String email, Utilisateur utilisateur ) {
        try {
            validationEmail( email );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        utilisateur.setEmail( email );
    }
    
    private void traiterTelephone( String telephone, Utilisateur utilisateur ) {
        try {
            validationTelephone( telephone );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_TELEPHONE, e.getMessage() );
        }
        utilisateur.setTelephone( telephone );
    }
    
    private void traiterRue( String rue, Utilisateur utilisateur ) {
        try {
            validationRue( rue );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_RUE, e.getMessage() );
        }
        utilisateur.setRue( rue );
    }
    
    private void traiterCodePostal( String codePostal, Utilisateur utilisateur ) {
        try {
            validationCodePostal( codePostal );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_CODE_POSTAL, e.getMessage() );
        }
        utilisateur.setCodePostal( codePostal );
    }
    
    private void traiterVille( String ville, Utilisateur utilisateur ) {
        try {
            validationVille( ville );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_VILLE, e.getMessage() );
        }
        utilisateur.setVille( ville );
    }
    
    private void traiterMotDePasse( String motDePasse, String verificationMdp, Utilisateur utilisateur ) {
        try {
            validationMotDePasse( motDePasse, verificationMdp );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_MOT_DE_PASSE, e.getMessage() );
            setErreur( CHAMP_VERIFICATION_MDP, e.getMessage() );
        }
        utilisateur.setMotDePasse( motDePasse );
    }
    
    private void validationPseudo( String pseudo ) throws FormValidationException {
        if ( pseudo != null ) {
            if ( pseudo.length() < 2 ) {
                throw new FormValidationException( "Le pseudo de l'utilisateur doit contenir au moins 2 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci d'entrer un pseudo." );
        }
    }
    
    private void validationNom( String nom ) throws FormValidationException {
        if ( nom != null ) {
            if ( nom.length() < 2 ) {
                throw new FormValidationException( "Le nom de l'utilisateur doit contenir au moins 2 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci d'entrer un nom d'utilisateur." );
        }
    }
    
    private void validationPrenom( String prenom ) throws FormValidationException {
        if ( prenom != null ) {
            if ( prenom.length() < 2 ) {
                throw new FormValidationException( "Le prénom de l'utilisateur doit contenir au moins 2 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci d'entrer un prénom d'utilisateur." );
        }
    }
    
    private void validationEmail ( String email ) throws FormValidationException {
    	if ( email != null){
    		if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
    			throw new FormValidationException( "Merci de saisir une adresse mail valide." );
    		} 
    	} else {
            throw new FormValidationException( "Merci d'entrer une adresse mail." );
        }
    }
    
    private void validationTelephone( String telephone ) throws FormValidationException {
        if ( telephone != null ) {
            if ( !telephone.matches( "^\\d+$" ) ) {
                throw new FormValidationException( "Le numéro de téléphone doit uniquement contenir des chiffres." );
            } else if ( telephone.length() < 10 ) {
                throw new FormValidationException( "Le numéro de téléphone doit contenir au moins 10 chiffres." );
            }
        } else {
            throw new FormValidationException( "Merci d'entrer un numéro de téléphone." );
        }
    }

     //Ajoute un message correspondant au champ spécifié à la map des erreurs.   
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
    
    private void validationRue( String rue ) throws FormValidationException {
        if ( rue != null ) {
            if ( rue.length() < 2 ) {
                throw new FormValidationException( "Le nom de la rue doit contenir au moins 2 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci d'entrer un nom de rue." );
        }
    }
    
    private void validationCodePostal( String codePostal ) throws FormValidationException {
        if ( codePostal != null ) {
        	if ( !codePostal.matches( "^\\d+$" ) ) {
                throw new FormValidationException( "Le code postal doit uniquement contenir des chiffres." );
            } else if ( codePostal.length() < 5 ) {
                throw new FormValidationException( "Le code postal doit contenir au moins 5 chiffres." );
            }
        } else {
            throw new FormValidationException( "Merci d'entrer un code postal." );
        }
    }
    
    private void validationVille( String ville ) throws FormValidationException {
        if ( ville != null ) {
            if ( ville.length() < 2 ) {
                throw new FormValidationException( "Le nom de la ville doit contenir au moins 2 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci d'entrer un nom de ville." );
        }
    }
    
    private void validationMotDePasse( String motDePasse , String verificationMdp ) throws FormValidationException {
        if ( motDePasse != null ) {
            if ( motDePasse.length() < 2 ) {
                throw new FormValidationException( "Le mot de passe doit contenir au moins 2 caractères." );             	
            } else {
            	if (!motDePasse.equals(verificationMdp)){
            		throw new FormValidationException( "Les deux mots de passe doivent être identiques" );
            	}
            } 
        } else {
            throw new FormValidationException( "Merci d'entrer un mot de passe." );
        }
    }

    
	//Méthode utilitaire qui retourne null si un champ est vide, et son contenu
    //sinon.    
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
	
}
