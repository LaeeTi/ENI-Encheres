package fr.eni.encheres.dao;

import java.util.List;

import fr.eni.encheres.beans.ArticleVendu;

public interface ArticleVenduDao {

	void creer(ArticleVendu articleVendu) throws DAOException;
    
    void modifier(ArticleVendu articleVendu) throws DAOException;
    
    ArticleVendu trouver(long id) throws DAOException;
    
    List<ArticleVendu> lister() throws DAOException;

    void supprimer( ArticleVendu articleVendu ) throws DAOException;

}
