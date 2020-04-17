package fr.eni.encheres.dao;

import static fr.eni.encheres.dao.DAOUtilitaire.fermeturesSilencieuses;
import static fr.eni.encheres.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.beans.Retrait;


public class RetraitDaoImpl implements RetraitDao {
	private static final String SQL_INSERT 		= "INSERT INTO retraits(rue, code_postal, ville) values (?,?,?)";
	private static final String SQL_UPDATE 		= "UPDATE retraits SET rue = ?, code_postal = ?, ville = ? WHERE no_retrait = ?";
	private static final String SQL_SELECT_BY 	= "SELECT no_retrait, rue, code_postal, ville FROM retraits WHERE no_retrait = ?";
	private static final String SQL_SELECT_ALL 	= "SELECT no_retrait, rue, code_postal, ville FROM retraits ORDER BY no_retrait";
	private static final String SQL_DELETE_BY 	= "DELETE FROM retraits WHERE no_retrait = ?";

	private DAOFactory daoFactory;

	RetraitDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	/* Impl�mentation de la m�thode d�finie dans l'interface RetraitDao */
	@Override
	public Retrait trouver(long noRetrait) throws DAOException {
		return trouver(SQL_SELECT_BY, noRetrait);
	}

	/* Impl�mentation de la m�thode d�finie dans l'interface RetraitDao */
	@Override
	public void creer(Retrait retrait) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true,
					retrait.getRue(),
					retrait.getCodePostal(),
					retrait.getVille());

			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("�chec de la cr�ation de la retrait, aucune ligne ajout�e dans la table.");
			}
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				retrait.setNoRetrait(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException("�chec de la cr�ation de la retrait en base, aucun ID auto-g�n�r� retourn�.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
		}
	}

	/* Impl�mentation de la m�thode d�finie dans l'interface RetraitDao */
	@Override
	public void modifier(Retrait retrait) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true,
					retrait.getRue(),
					retrait.getCodePostal(),
					retrait.getVille(),
					retrait.getNoRetrait());

			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("�chec de la modification de la retrait, aucune ligne ajout�e dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
		}
	}

	/* Impl�mentation de la m�thode d�finie dans l'interface RetraitDao */
	@Override
	public List<Retrait> lister() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Retrait> retrait = new ArrayList<Retrait>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				retrait.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}

		return retrait;
	}

	/* Impl�mentation de la m�thode d�finie dans l'interface RetraitDao */
	@Override
	public void supprimer(Retrait retrait) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE_BY, true,
					retrait.getNoRetrait());
			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("�chec de la suppression de l'retrait, aucune ligne supprim�e de la table.");
			} else {
				retrait.setNoRetrait(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}

	/*
	 * M�thode g�n�rique utilis�e pour retourner un retrait depuis la base de
	 * donn�es, correspondant � la requ�te SQL donn�e prenant en param�tres les
	 * objets pass�s en argument.
	 */
	private Retrait trouver(String sql, Object... objets) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Retrait retrait = null;

		try {
			/* R�cup�ration d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			/*
			 * Pr�paration de la requ�te avec les objets pass�s en arguments
			 * (ici, uniquement un no_article) et ex�cution.
			 */
			preparedStatement = initialisationRequetePreparee(connexion, sql, false, objets);
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de donn�es retourn�e dans le ResultSet */
			if (resultSet.next()) {
				retrait = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return retrait;
	}

	/*
	 * Simple m�thode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des retraits (un ResultSet)
	 * et un bean Retrait.
	 */
	private Retrait map(ResultSet resultSet) throws SQLException {
		Retrait retrait = new Retrait();
		retrait.setNoRetrait(resultSet.getLong("no_retrait"));
		retrait.setRue(resultSet.getString("rue"));
		retrait.setCodePostal(resultSet.getString("code_postal"));
		retrait.setVille(resultSet.getString("ville"));
		
		return retrait;
	}
}
