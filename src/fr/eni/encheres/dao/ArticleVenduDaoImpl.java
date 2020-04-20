package fr.eni.encheres.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import fr.eni.encheres.beans.ArticleVendu;

import static fr.eni.encheres.dao.DAOUtilitaire.fermeturesSilencieuses;
import static fr.eni.encheres.dao.DAOUtilitaire.initialisationRequetePreparee;

public class ArticleVenduDaoImpl implements ArticleVenduDao {
	private static final String SQL_INSERT        	= "INSERT INTO articles_vendus(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, etat_vente, no_utilisateur_vendeur, no_categorie, no_retrait) values (?,?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE        	= "UPDATE articles_vendus SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, etat_vente = ?, no_utilisateur_acheteur = ?, no_utilisateur_vendeur = ?, no_categorie = ?, no_retrait = ? WHERE no_article = ?";
	private static final String SQL_SELECT_BY 	  	= "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, etat_vente, no_utilisateur_acheteur, no_utilisateur_vendeur, no_categorie, no_retrait FROM articles_vendus WHERE no_article = ?";
	private static final String SQL_SELECT_ALL	  	= "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, etat_vente, no_utilisateur_acheteur, no_utilisateur_vendeur, no_categorie, no_retrait FROM articles_vendus ORDER BY no_article";
	private static final String SQL_DELETE_BY 		= "DELETE FROM articles_vendus WHERE no_article = ?";
    private DAOFactory          daoFactory;

    ArticleVenduDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
	
    /* Implémentation de la méthode définie dans l'interface ArticleVenduDao */
    @Override
	public void creer(ArticleVendu articleVendu) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try{
			connexion = daoFactory.getConnection();
			preparedStatement  = initialisationRequetePreparee( connexion, SQL_INSERT, true,
			articleVendu.getNomArticle(),
			articleVendu.getDescription(),
			new Timestamp( articleVendu.getDateDebutEncheres().getMillis()),
			new Timestamp( articleVendu.getDateFinEncheres().getMillis()),
			articleVendu.getMiseAPrix(),
			articleVendu.getEtatVente(),
			articleVendu.getVendeur().getNoUtilisateur(),
			articleVendu.getCategorieArticle().getNoCategorie(),
			articleVendu.getLieuRetrait().getNoRetrait());
			int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de l'articleVendu, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                articleVendu.setNoArticle( valeursAutoGenerees.getLong( 1 ));
            } else {
                throw new DAOException( "Échec de la création de l'articleVendu en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    /* Implémentation de la méthode définie dans l'interface ArticleVenduDao */
    @Override
	public void modifier(ArticleVendu articleVendu) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try{
			connexion = daoFactory.getConnection();

			Long no_acheteur = null;
			if(articleVendu.getAcheteur() != null){
				no_acheteur = articleVendu.getAcheteur().getNoUtilisateur();
			}
			preparedStatement  = initialisationRequetePreparee( connexion, SQL_UPDATE, true,
			articleVendu.getNomArticle(),
			articleVendu.getDescription(),
			new Timestamp( articleVendu.getDateDebutEncheres().getMillis()),
			new Timestamp( articleVendu.getDateFinEncheres().getMillis()),
			articleVendu.getMiseAPrix(),
			articleVendu.getPrixVente(),
			articleVendu.getEtatVente(),
			no_acheteur,
			articleVendu.getVendeur().getNoUtilisateur(),
			articleVendu.getCategorieArticle().getNoCategorie(),
			articleVendu.getLieuRetrait().getNoRetrait(),
			articleVendu.getNoArticle());
			
			int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la modification de l'articleVendu, aucune ligne ajoutée dans la table." );
            }
           
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
    
    /* Implémentation de la méthode définie dans l'interface ArticleVenduDao */
    @Override
    public ArticleVendu trouver( long noArticleVendu ) throws DAOException {
        return trouver( SQL_SELECT_BY, noArticleVendu );
    }
    
    /* Implémentation de la méthode définie dans l'interface ClientDao */
    @Override
    public List<ArticleVendu> lister() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ArticleVendu> articlesVendus = new ArrayList<ArticleVendu>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT_ALL );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                articlesVendus.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return articlesVendus;
    }
    
    /* Implémentation de la méthode définie dans l'interface ArticleVenduDao */
    @Override
	public void supprimer(ArticleVendu articleVendu) throws DAOException{
		 Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_BY, true, articleVendu.getNoArticle() );
	            int statut = preparedStatement.executeUpdate();
	            if ( statut == 0 ) {
	                throw new DAOException( "Échec de la suppression de l'articleVendu, aucune ligne supprimée de la table." );
	            } else {
	                articleVendu.setNoArticle( null );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( preparedStatement, connexion );
	        }
	    }
	
	 /*
     * Méthode générique utilisée pour retourner un articleVendu depuis la base de
     * données, correspondant à la requête SQL donnée prenant en paramètres les
     * objets passés en argument.
     */
    private ArticleVendu trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArticleVendu articleVendu = null;

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
                articleVendu = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return articleVendu;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des articleVendus (un ResultSet) et
     * un bean ArticleVendu.
     */
    private ArticleVendu map( ResultSet resultSet ) throws SQLException {
        ArticleVendu articleVendu = new ArticleVendu();
        articleVendu.setNoArticle(resultSet.getLong("no_article"));
        articleVendu.setNomArticle( resultSet.getString( "nom_article" ) );
        articleVendu.setDescription( resultSet.getString( "description" ) );
        articleVendu.setDateDebutEncheres( new DateTime( resultSet.getTimestamp( "date_debut_encheres" ) ) );
        articleVendu.setDateFinEncheres( new DateTime( resultSet.getTimestamp( "date_fin_encheres" ) ) );     
        articleVendu.setMiseAPrix( resultSet.getInt( "prix_initial" ) );
        articleVendu.setPrixVente( resultSet.getInt( "prix_vente" ) );
        articleVendu.setEtatVente( resultSet.getString( "etat_vente" ) );
        
        /*
		 *Création d'un objet acheteurDao à partir de la DAOFactory et utilisation de sa
		 *methode trouver pour récupérer dans la base, l'utilisateur avec le no_utilisateur souhaité
         */
        UtilisateurDao acheteurDao = daoFactory.getUtilisateurDao();
        articleVendu.setVendeur( acheteurDao.trouver(resultSet.getInt( "no_utilisateur_acheteur" ) ) );

        /*
		 *Création d'un objet vendeurDao à partir de la DAOFactory et utilisation de sa
		 *methode trouver pour récupérer dans la base, l'utilisateur avec le no_utilisateur souhaité
         */
        UtilisateurDao vendeurDao = daoFactory.getUtilisateurDao();
        articleVendu.setVendeur( vendeurDao.trouver(resultSet.getInt( "no_utilisateur_vendeur" ) ) );

        /*
		 *Création d'un objet categorieDao à partir de la DAOFactory et utilisation de sa
		 *methode trouver pour récupérer dans la base, l'utilisateur avec le no_categorie souhaité
         */
        CategorieDao categorieDao = daoFactory.getCategorieDao();
        articleVendu.setCategorieArticle( categorieDao.trouver(resultSet.getInt( "no_categorie" ) ) );

        /*
		 *Création d'un objet retraitDao à partir de la DAOFactory et utilisation de sa
		 *methode trouver pour récupérer dans la base, l'utilisateur avec le no_retrait souhaité
         */
        RetraitDao retraitDao = daoFactory.getRetraitDao();
        articleVendu.setLieuRetrait( retraitDao.trouver(resultSet.getInt( "no_retrait" ) ) );
        
        return articleVendu;
    }
}
