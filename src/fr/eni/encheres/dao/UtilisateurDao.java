package fr.eni.encheres.dao;

import java.util.List;
import fr.eni.encheres.beans.Utilisateur;

public interface UtilisateurDao {

	void creer(Utilisateur utilisateur) throws DAOException;
    
    void modifier(Utilisateur utilisateur) throws DAOException;
    
	Utilisateur trouver(long id) throws DAOException;
	
    List<Utilisateur> lister() throws DAOException;

    void supprimer( Utilisateur utilisateur ) throws DAOException;

}
