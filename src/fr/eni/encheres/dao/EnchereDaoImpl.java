package fr.eni.encheres.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import fr.eni.encheres.beans.Enchere;

import static fr.eni.encheres.dao.DAOUtilitaire.fermeturesSilencieuses;
import static fr.eni.encheres.dao.DAOUtilitaire.initialisationRequetePreparee;

public class EnchereDaoImpl implements EnchereDao {
	private static final String SQL_INSERT        = "INSERT INTO encheres(no_utilisateur, no_article, date_enchere, montant_enchere) values (?,?,?,?)";
	private static final String SQL_UPDATE        = "UPDATE encheres SET date_enchere = ?, montant_enchere = ? WHERE no_article = ? AND no_utilisateur = ?";
	private static final String SQL_SELECT_BY 	  = "SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM encheres WHERE no_article = ? AND no_utilisateur = ?";
	private static final String SQL_SELECT_ALL	  = "SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM encheres ORDER BY no_article, no_utilisateur ";
	private static final String SQL_DELETE_BY     = "DELETE FROM encheres WHERE no_article = ? AND no_utilisateur = ?";
    private DAOFactory          daoFactory;

    EnchereDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
	
    /* Impl�mentation de la m�thode d�finie dans l'interface EnchereDao */
    @Override
	public void creer(Enchere enchere) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;


		try{
			connexion = daoFactory.getConnection();
			preparedStatement  = initialisationRequetePreparee( connexion, SQL_INSERT, true,
					enchere.getEncherisseur().getNoUtilisateur(),
					enchere.getArticle().getNoArticle(),
					new Timestamp( enchere.getDateEnchere().getMillis() ),
					enchere.getMontantEnchere());
			int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "�chec de la cr�ation de l'enchere, aucune ligne ajout�e dans la table." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses(  preparedStatement, connexion );
        }
    }

    /* Impl�mentation de la m�thode d�finie dans l'interface EnchereDao */
    @Override
	public void modifier(Enchere enchere) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try{
			connexion = daoFactory.getConnection();
			preparedStatement  = initialisationRequetePreparee( connexion, SQL_UPDATE, true,
					new Timestamp( enchere.getDateEnchere().getMillis() ),
					enchere.getMontantEnchere(),
					enchere.getArticle().getNoArticle(),
					enchere.getEncherisseur().getNoUtilisateur());
		
			int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "�chec de la modification de l'enchere, aucune ligne ajout�e dans la table." );
            }
           
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
    
    /* Impl�mentation de la m�thode d�finie dans l'interface EnchereDao */
    @Override
    public Enchere trouver(  Long noArticle , Long noEncherisseur  ) throws DAOException {
        return trouver( SQL_SELECT_BY, noArticle , noEncherisseur );
    }
    
    /* Impl�mentation de la m�thode d�finie dans l'interface EnchereDao */
    @Override
    public List<Enchere> lister() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Enchere> encheres = new ArrayList<Enchere>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT_ALL );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                encheres.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return encheres;
    }
    
    /* Impl�mentation de la m�thode d�finie dans l'interface EnchereDao */
    @Override
	public void supprimer(Enchere enchere) throws DAOException{
		 Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_BY , true, 	
	            		enchere.getArticle().getNoArticle(),
	            		enchere.getEncherisseur().getNoUtilisateur());
	            int statut = preparedStatement.executeUpdate();
	            if ( statut == 0 ) {
	                throw new DAOException( "�chec de la suppression de l'enchere, aucune ligne supprim�e de la table." );
	            } else {
	                enchere.setArticle( null );
	                enchere.setEncherisseur( null );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( preparedStatement, connexion );
	        }
	    }
	
	 /*
     * M�thode g�n�rique utilis�e pour retourner un enchere depuis la base de
     * donn�es, correspondant � la requ�te SQL donn�e prenant en param�tres les
     * objets pass�s en argument.
     */
    private Enchere trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Enchere enchere = null;

        try {
            /* R�cup�ration d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Pr�paration de la requ�te avec les objets pass�s en arguments
             * (ici, uniquement un no_article) et ex�cution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de donn�es retourn�e dans le ResultSet */
            if ( resultSet.next() ) {
                enchere = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return enchere;
    }

    /*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des encheres (un ResultSet) et
     * un bean Enchere.
     */
    private Enchere map( ResultSet resultSet ) throws SQLException {
        Enchere enchere = new Enchere();

        /*
		 *Cr�ation d'un objet encherisseurDao � partir de la DAOFactory et utilisation de sa
		 *methode trouver pour r�cup�rer dans la base, l'utilisateur avec le no_utilisateur souhait�
         */
        UtilisateurDao encherisseurDao = daoFactory.getUtilisateurDao();
        enchere.setEncherisseur( encherisseurDao.trouver(resultSet.getInt( "no_utilisateur" ) ) );
        
        /*
		 *Cr�ation d'un objet articleVenduDao � partir de la DAOFactory et utilisation de sa
		 *methode trouver pour r�cup�rer dans la base, l'utilisateur avec le no_article souhait�
         */
        ArticleVenduDao articleVenduDao = daoFactory.getArticleVenduDao();
        enchere.setArticle( articleVenduDao.trouver(resultSet.getInt( "no_article" ) ) );        
        enchere.setDateEnchere( new DateTime( resultSet.getTimestamp( "date_enchere" ) ) );
        enchere.setMontantEnchere( resultSet.getInt( "montant_enchere" ) );

        return enchere;
    }
}
