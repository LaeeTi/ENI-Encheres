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
	
    /* Implémentation de la méthode définie dans l'interface EnchereDao */
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
                throw new DAOException( "Échec de la création de l'enchere, aucune ligne ajoutée dans la table." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses(  preparedStatement, connexion );
        }
    }

    /* Implémentation de la méthode définie dans l'interface EnchereDao */
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
                throw new DAOException( "Échec de la modification de l'enchere, aucune ligne ajoutée dans la table." );
            }
           
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
    
    /* Implémentation de la méthode définie dans l'interface EnchereDao */
    @Override
    public Enchere trouver(  Long noArticle , Long noEncherisseur  ) throws DAOException {
        return trouver( SQL_SELECT_BY, noArticle , noEncherisseur );
    }
    
    /* Implémentation de la méthode définie dans l'interface EnchereDao */
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
    
    /* Implémentation de la méthode définie dans l'interface EnchereDao */
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
	                throw new DAOException( "Échec de la suppression de l'enchere, aucune ligne supprimée de la table." );
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
     * Méthode générique utilisée pour retourner un enchere depuis la base de
     * données, correspondant à la requête SQL donnée prenant en paramètres les
     * objets passés en argument.
     */
    private Enchere trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Enchere enchere = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement un no_article) et exécution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
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
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des encheres (un ResultSet) et
     * un bean Enchere.
     */
    private Enchere map( ResultSet resultSet ) throws SQLException {
        Enchere enchere = new Enchere();

        /*
		 *Création d'un objet encherisseurDao à partir de la DAOFactory et utilisation de sa
		 *methode trouver pour récupérer dans la base, l'utilisateur avec le no_utilisateur souhaité
         */
        UtilisateurDao encherisseurDao = daoFactory.getUtilisateurDao();
        enchere.setEncherisseur( encherisseurDao.trouver(resultSet.getInt( "no_utilisateur" ) ) );
        
        /*
		 *Création d'un objet articleVenduDao à partir de la DAOFactory et utilisation de sa
		 *methode trouver pour récupérer dans la base, l'utilisateur avec le no_article souhaité
         */
        ArticleVenduDao articleVenduDao = daoFactory.getArticleVenduDao();
        enchere.setArticle( articleVenduDao.trouver(resultSet.getInt( "no_article" ) ) );        
        enchere.setDateEnchere( new DateTime( resultSet.getTimestamp( "date_enchere" ) ) );
        enchere.setMontantEnchere( resultSet.getInt( "montant_enchere" ) );

        return enchere;
    }
}
