package fr.eni.encheres.servlets;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.beans.Utilisateur;
import fr.eni.encheres.dao.DAOFactory;

import fr.eni.encheres.dao.UtilisateurDao;
import fr.eni.encheres.forms.CreationProfilForm;

public class CreerProfil extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_UTILISATEUR       = "utilisateur";
    public static final String ATT_FORM         = "form";
    public static final String SESSION_UTILISATEURS  = "utilisateurs";
    public static final String VUE_SUCCES       = "/utilisateur/afficherProfil.jsp";
    public static final String VUE_FORM         = "/creationProfil.jsp";

    private UtilisateurDao          utilisateurDao;
    private DAOFactory 				daoFactory;
	
    //R�cup�ration d'une instance de la DaoUtilisateur
    public void init() throws ServletException {
		 //Initialisation de la DAOFactory
   		this.daoFactory = DAOFactory.getInstance();
   		utilisateurDao = daoFactory.getUtilisateurDao();
	 }
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/creationProfil.jsp" ).forward( request, response );
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		/* Pr�paration de l'objet formulaire */
        CreationProfilForm form = new CreationProfilForm( utilisateurDao );
		
        /* Traitement de la requ�te et r�cup�ration du bean en r�sultant */
        Utilisateur utilisateur = form.creerUtilisateur( request );
        
        /* Ajout du bean et de l'objet m�tier � l'objet requ�te */
        request.setAttribute( ATT_UTILISATEUR, utilisateur );
        request.setAttribute( ATT_FORM, form );

        /* Si aucune erreur */
        if ( form.getErreurs().isEmpty() ) {
            /* Alors r�cup�ration de la map des clients dans la session */
            HttpSession session = request.getSession();
            Map<Long, Utilisateur> utilisateurs = (HashMap<Long, Utilisateur>) session.getAttribute( SESSION_UTILISATEURS );
            /* Si aucune map n'existe, alors initialisation d'une nouvelle map */
            if ( utilisateurs == null ) {
            	utilisateurs = new HashMap<Long, Utilisateur>();
            }
            /* Puis ajout du client courant dans la map */
            utilisateurs.put( utilisateur.getNoUtilisateur(), utilisateur );
            /* Et enfin (r�)enregistrement de la map en session */
            session.setAttribute( SESSION_UTILISATEURS, utilisateurs );

            /* Affichage de la fiche r�capitulative */
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
            /* Sinon, r�-affichage du formulaire de cr�ation avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }
	

}
