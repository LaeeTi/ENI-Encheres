package fr.eni.encheres.dao;

import java.util.List;

import fr.eni.encheres.beans.Enchere;

public interface EnchereDao {
	void creer(Enchere enchere) throws DAOException;
    
    void modifier(Enchere enchere) throws DAOException;
    
	Enchere trouver(Long id, Long id2) throws DAOException;
	
    List<Enchere> lister() throws DAOException;

    void supprimer( Enchere enchere ) throws DAOException;

}
