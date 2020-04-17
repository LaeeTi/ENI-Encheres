package fr.eni.encheres.dao;

import static fr.eni.encheres.dao.DAOUtilitaire.fermeturesSilencieuses;
import static fr.eni.encheres.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.beans.Categorie;
import fr.eni.encheres.beans.Categorie;
import fr.eni.encheres.beans.Categorie;

public class CategorieDaoImpl implements CategorieDao{
	private static final String SQL_INSERT        = "INSERT INTO categories(libelle) values (?)";
	private static final String SQL_UPDATE        = "UPDATE categories SET libelle = ? WHERE no_categorie = ?";
	private static final String SQL_SELECT_BY     = "SELECT no_categorie, libelle FROM categories WHERE no_categorie = ?";
	private static final String SQL_SELECT_ALL	  = "SELECT no_categorie, libelle FROM categories ORDER BY no_categorie";
	private static final String SQL_DELETE_BY     = "DELETE FROM categories WHERE no_categorie = ?";
    
	private DAOFactory          daoFactory;

    CategorieDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
	
    /* Impl�mentation de la m�thode d�finie dans l'interface CategorieDao */
    @Override
    public Categorie trouver( long noCategorie ) throws DAOException {
        return trouver( SQL_SELECT_BY, noCategorie );
    }
    
    /* Impl�mentation de la m�thode d�finie dans l'interface CategorieDao */
    @Override
	public void creer(Categorie categorie) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try{
			connexion = daoFactory.getConnection();
			preparedStatement  = initialisationRequetePreparee( connexion, SQL_INSERT, true,categorie.getLibelle());

			int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "�chec de la cr�ation de la categorie, aucune ligne ajout�e dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                categorie.setNoCategorie( valeursAutoGenerees.getLong( 1 ));
            } else {
                throw new DAOException( "�chec de la cr�ation de la categorie en base, aucun ID auto-g�n�r� retourn�." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
	
    /* Impl�mentation de la m�thode d�finie dans l'interface CategorieDao */
    @Override
	public void modifier(Categorie categorie) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try{
			connexion = daoFactory.getConnection();
			preparedStatement  = initialisationRequetePreparee( connexion, SQL_UPDATE, true,
					categorie.getLibelle(),
					categorie.getNoCategorie());

			int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "�chec de la modification de la categorie, aucune ligne ajout�e dans la table." );
            }
           
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    
    /* Impl�mentation de la m�thode d�finie dans l'interface CategorieDao */
    @Override
    public List<Categorie> lister() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Categorie> categorie = new ArrayList<Categorie>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT_ALL );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                categorie.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return categorie;
    }
    
    /* Impl�mentation de la m�thode d�finie dans l'interface CategorieDao */
    @Override
	public void supprimer(Categorie categorie) throws DAOException{
		 Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_BY, true, categorie.getNoCategorie() );
	            int statut = preparedStatement.executeUpdate();
	            if ( statut == 0 ) {
	                throw new DAOException( "�chec de la suppression de l'categorie, aucune ligne supprim�e de la table." );
	            } else {
	                categorie.setNoCategorie( null );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( preparedStatement, connexion );
	        }
	    }
	
	 /*
     * M�thode g�n�rique utilis�e pour retourner un categorie depuis la base de
     * donn�es, correspondant � la requ�te SQL donn�e prenant en param�tres les
     * objets pass�s en argument.
     */
    private Categorie trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Categorie categorie = null;

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
                categorie = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return categorie;
    }

    /*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des categories (un ResultSet) et
     * un bean Categorie.
     */
    private Categorie map( ResultSet resultSet ) throws SQLException {
        Categorie categorie = new Categorie();
        categorie.setNoCategorie(resultSet.getLong("no_categorie"));
        categorie.setLibelle( resultSet.getString( "libelle" ) );
        return categorie;
    }
}
