package fr.eni.encheres.dao;

import java.util.List;

import fr.eni.encheres.beans.Retrait;

public interface RetraitDao {

	void creer(Retrait retrait) throws DAOException;
    
    void modifier(Retrait retrait) throws DAOException;
    
	Retrait trouver(long id) throws DAOException;
	
    List<Retrait> lister() throws DAOException;

    void supprimer( Retrait retrait ) throws DAOException;

}
