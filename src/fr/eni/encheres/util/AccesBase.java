package fr.eni.encheres.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class AccesBase {
	public static Connection getConnection() throws SQLException{
		
		String uri = "jdbc:mysql://localhost:3308/trocencheres";
		String user = "dbowner";
		String password = "Pa$$w0rd";
		Connection connexion = null;
		/* Chargement du driver JDBC pour MySQL */
		try {
		    Class.forName( "com.mysql.cj.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
		    /* Gérer les éventuelles erreurs ici. */
			System.err.println("Chargement du driver impossible!");
		}
		try {
		    connexion = DriverManager.getConnection( uri, user, password );

		} catch ( SQLException e ) {
		    /* Gérer les éventuelles erreurs ici */
			System.err.println("Connexion à la base impossible!");
		} finally {
		    if ( connexion != null )
		        try {
		            /* Fermeture de la connexion */
		            connexion.close();
		        } catch ( SQLException ignore ) {
		            /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
		        }
		}
		return connexion;		
	}
}
