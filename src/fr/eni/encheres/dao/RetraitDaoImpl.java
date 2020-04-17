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

	/* Implémentation de la méthode définie dans l'interface RetraitDao */
	@Override
	public Retrait trouver(long noRetrait) throws DAOException {
		return trouver(SQL_SELECT_BY, noRetrait);
	}

	/* Implémentation de la méthode définie dans l'interface RetraitDao */
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
				throw new DAOException("Échec de la création de la retrait, aucune ligne ajoutée dans la table.");
			}
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				retrait.setNoRetrait(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException("Échec de la création de la retrait en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
		}
	}

	/* Implémentation de la méthode définie dans l'interface RetraitDao */
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
				throw new DAOException("Échec de la modification de la retrait, aucune ligne ajoutée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
		}
	}

	/* Implémentation de la méthode définie dans l'interface RetraitDao */
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

	/* Implémentation de la méthode définie dans l'interface RetraitDao */
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
				throw new DAOException("Échec de la suppression de l'retrait, aucune ligne supprimée de la table.");
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
	 * Méthode générique utilisée pour retourner un retrait depuis la base de
	 * données, correspondant à la requête SQL donnée prenant en paramètres les
	 * objets passés en argument.
	 */
	private Retrait trouver(String sql, Object... objets) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Retrait retrait = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			/*
			 * Préparation de la requête avec les objets passés en arguments
			 * (ici, uniquement un no_article) et exécution.
			 */
			preparedStatement = initialisationRequetePreparee(connexion, sql, false, objets);
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données retournée dans le ResultSet */
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
	 * Simple méthode utilitaire permettant de faire la correspondance (le
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
