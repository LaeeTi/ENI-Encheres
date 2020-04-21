package fr.eni.encheres.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.beans.Utilisateur;
import static fr.eni.encheres.dao.DAOUtilitaire.fermeturesSilencieuses;
import static fr.eni.encheres.dao.DAOUtilitaire.initialisationRequetePreparee;

public class UtilisateurDaoImpl implements UtilisateurDao {
	private static final String SQL_INSERT        = "INSERT INTO utilisateurs(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) values (?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE        = "UPDATE utilisateurs SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? where no_utilisateur = ?";
	private static final String SQL_SELECT_PAR_ID = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur  FROM utilisateurs WHERE no_utilisateur = ?";
	private static final String SQL_SELECT        = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur  FROM utilisateurs ORDER BY no_utilisateur";
	private static final String SQL_DELETE_PAR_ID = "DELETE FROM utilisateurs WHERE no_utilisateur = ?";
	// private static final String SQL_CONNEXION     = "SELECT pseudo, email FROM utilisateurs";

    private DAOFactory          daoFactory;

    UtilisateurDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
	
    /* Impl�mentation de la m�thode d�finie dans l'interface UtilisateurDao */
    @Override
	public void creer(Utilisateur utilisateur) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try{
			connexion = daoFactory.getConnection();
			preparedStatement  = initialisationRequetePreparee( connexion, SQL_INSERT, true, utilisateur.getPseudo(),
			utilisateur.getNom(),
			utilisateur.getPrenom(),
			utilisateur.getEmail(),
			utilisateur.getTelephone(),
			utilisateur.getRue(),
			utilisateur.getCodePostal(),
			utilisateur.getVille(),
			utilisateur.getMotDePasse(),
			utilisateur.getCredit(),
			utilisateur.isAdministrateur());
			int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "�chec de la cr�ation de l'utilisateur, aucune ligne ajout�e dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                utilisateur.setNoUtilisateur( valeursAutoGenerees.getLong( 1 ));
            } else {
                throw new DAOException( "�chec de la cr�ation de l'utilisateur en base, aucun ID auto-g�n�r� retourn�." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    /* Impl�mentation de la m�thode d�finie dans l'interface UtilisateurDao */
    @Override
	public void modifier(Utilisateur utilisateur) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try{
			connexion = daoFactory.getConnection();
			preparedStatement  = initialisationRequetePreparee( connexion, SQL_UPDATE, true,
			utilisateur.getPseudo(),
			utilisateur.getNom(),
			utilisateur.getPrenom(),
			utilisateur.getEmail(),
			utilisateur.getTelephone(),
			utilisateur.getRue(),
			utilisateur.getCodePostal(),
			utilisateur.getVille(),
			utilisateur.getMotDePasse(),
			utilisateur.getNoUtilisateur());
			
			int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "�chec de la modification de l'utilisateur, aucune ligne ajout�e dans la table." );
            }
           
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
	
    /* Impl�mentation de la m�thode d�finie dans l'interface UtilisateurDao */
    @Override
    public Utilisateur trouver( long noUtilisateur ) throws DAOException {
        return trouver( SQL_SELECT_PAR_ID, noUtilisateur );
    }
    
    public Utilisateur rechercher (String pseudo, String motDePasse) throws DAOException {
    	Utilisateur utilisateur = new Utilisateur();
    	Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try{
			connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_CONNEXION );      
            preparedStatement.setString(1, pseudo);
            preparedStatement.setString(2, motDePasse);
            resultSet = preparedStatement.executeQuery();
            
        if(resultSet.next())
        {
        	utilisateur.setNoUtilisateur( resultSet.getLong( "no_utilisateur" ) );
            utilisateur.setPseudo(pseudo);
            utilisateur.setNom( resultSet.getString( "nom" ) );
            utilisateur.setPrenom( resultSet.getString( "prenom" ) );
            utilisateur.setEmail( resultSet.getString( "email" ) );
            utilisateur.setTelephone( resultSet.getString( "telephone" ) );
            utilisateur.setRue( resultSet.getString( "rue" ) );
            utilisateur.setCodePostal( resultSet.getString( "code_postal" ) );
            utilisateur.setVille( resultSet.getString( "ville" ) );
            utilisateur.setMotDePasse( motDePasse );
            utilisateur.setCredit( resultSet.getInt( "credit" ) );
            utilisateur.setAdministrateur( resultSet.getBoolean( "administrateur" ) );
        } 
        else {
        	utilisateur = null;
        	}
		} catch ( SQLException e ) {
            throw new DAOException( e );    
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connection );
		} 
		return utilisateur;
    }
    
    
    /* Impl�mentation de la m�thode d�finie dans l'interface ClientDao */
    @Override
    public List<Utilisateur> lister() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Utilisateur> utilisateur = new ArrayList<Utilisateur>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                utilisateur.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return utilisateur;
    }
    
	
    /* Impl�mentation de la m�thode d�finie dans l'interface UtilisateurDao */
    @Override
	public void supprimer(Utilisateur utilisateur) throws DAOException{
		 Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, true, utilisateur.getNoUtilisateur() );
	            int statut = preparedStatement.executeUpdate();
	            if ( statut == 0 ) {
	                throw new DAOException( "�chec de la suppression de l'utilisateur, aucune ligne supprim�e de la table." );
	            } else {
	                utilisateur.setNoUtilisateur( null );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( preparedStatement, connexion );
	        }
	    }

	 /*
     * M�thode g�n�rique utilis�e pour retourner un utilisateur depuis la base de
     * donn�es, correspondant � la requ�te SQL donn�e prenant en param�tres les
     * objets pass�s en argument.
     */
    private Utilisateur trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;

        try {
            /* R�cup�ration d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Pr�paration de la requ�te avec les objets pass�s en arguments
             * (ici, uniquement le no_utilisateur) et ex�cution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de donn�es retourn�e dans le ResultSet */
            if ( resultSet.next() ) {
                utilisateur = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return utilisateur;
    }

    /*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des utilisateurs (un ResultSet) et
     * un bean Utilisateur.
     */
    private static Utilisateur map( ResultSet resultSet ) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNoUtilisateur( resultSet.getLong( "no_utilisateur" ) );
        utilisateur.setPseudo(resultSet.getString("pseudo"));
        utilisateur.setNom( resultSet.getString( "nom" ) );
        utilisateur.setPrenom( resultSet.getString( "prenom" ) );
        utilisateur.setEmail( resultSet.getString( "email" ) );
        utilisateur.setTelephone( resultSet.getString( "telephone" ) );
        utilisateur.setRue( resultSet.getString( "rue" ) );
        utilisateur.setCodePostal( resultSet.getString( "code_postal" ) );
        utilisateur.setVille( resultSet.getString( "ville" ) );
        utilisateur.setMotDePasse( resultSet.getString( "mot_de_passe" ) );
        utilisateur.setCredit( resultSet.getInt( "credit" ) );
        utilisateur.setAdministrateur( resultSet.getBoolean( "administrateur" ) );
        return utilisateur;
    }

    /*
	@Override
	public connexion(String pseudo, String motdepasse) throws DAOException {		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Utilisateur utilisateur = null;
		boolean connexionValide = false;
		
		try{
			connexion = daoFactory.getConnection();
			
			preparedStatement = initialisationRequetePreparee( connexion, SQL_CONNEXION, true, utilisateur.getPseudo(), utilisateur.getMotDePasse() );
            resultSet = preparedStatement.executeQuery();
            // Parcours de la ligne de donn�es retourn�e dans le ResultSet 
            if ( resultSet.next() ) {
                utilisateur = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
		
		
		return connexionValide;
	}
	*/
	
}
