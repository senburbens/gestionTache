package com.gestiontache.milorme.basededonnees;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import com.gestiontache.milorme.entites.Tache;
import com.gestiontache.milorme.entites.Membre;

public class ConnexionBaseDeDonnees {	
	//Variables representant les ressources manipulees
	private static ConnexionBaseDeDonnees connexionBaseDeDonneesSingleton=null;
	Connection c = null;
	Statement stmt = null;
	ResultSet rs=null;
	final String NOM_BASE_DE_DONNEES ="gestiontache.db";

	//private final String STATUS_NOUVEAU="nouveau";
	//private final String STATUS_EN_PROGRES="en-progres";
	//private final String STATUS_TERMINE="termine";
	
	//Methode permettant de recuperer l'objet singleton de la base de donnees
	public static ConnexionBaseDeDonnees recupererObjetSingleton(){
		if(connexionBaseDeDonneesSingleton==null){
			connexionBaseDeDonneesSingleton = new ConnexionBaseDeDonnees();
		}		
		return connexionBaseDeDonneesSingleton;
	}
		
	//Constructeur de la classe permettant d'initialiser, d'instancier et de creer une connexion a la base de donnees 
	private ConnexionBaseDeDonnees() {		
		boolean fichierExiste=false;
		File f1 = new File(NOM_BASE_DE_DONNEES); 
		//Test de l'existence de la base
		if(f1.exists()){
		   fichierExiste=true;
		}		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + NOM_BASE_DE_DONNEES);
			c.setAutoCommit(true);

			stmt = c.createStatement();
			
			if(!fichierExiste){
				creerTablesDansLaBaseDeDonnees();
			}
			
		} catch (Exception e) {
			//System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.out.println("\nErreur au niveau de la base de donnees\n");
			System.exit(0);
		}
	}


	//Methode de creation des tables dans la base de donnees
	private void creerTablesDansLaBaseDeDonnees(){

		String sql;

		try {
			sql = "CREATE TABLE TACHE " +
					"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
					" NOM TEXT NOT NULL," +
					"DESCRIPTION TEXT NOT NULL,"+
					"STATUS TEXT NOT NULL," +
					"ID_MEMBRE INTEGER NULL)";					
			stmt.executeUpdate(sql);

			sql="CREATE TABLE MEMBRE " +
					"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
					" NOM TEXT NOT NULL)";					
			stmt.executeUpdate(sql);

		} catch ( Exception e ) {
			//System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("\nCreation des tables echouee\n");
			System.exit(0);
		}
		System.out.println("Creation des tables TACHE et MEMBRE reussie\n");
	}

	//Methode permettant de creer une tache
	public void creerTache(Tache tache) {
		int idMembre=tache.getIdMembre();
		String status="", sql="";

		tache.setNom(tache.getNom().replace('\'', ' '));
		tache.setDescription((tache.getDescription().replace('\'', ' ')));
		tache.setStatus((tache.getNom().replace('\'', ' ')));
		
		if( idMembre<=0){
			status="nouveau";
			sql="INSERT INTO TACHE(NOM, DESCRIPTION, STATUS, ID_MEMBRE) " + "VALUES('" + tache.getNom()+"','"+ tache.getDescription() +"','" + status + "', NULL );";
		}else{
			status="en-progres";
			sql="INSERT INTO TACHE(NOM, DESCRIPTION, STATUS, ID_MEMBRE) " + "VALUES('" + tache.getNom()+"','"+ tache.getDescription() +"','" + status + "'," + idMembre + ");";
		}

		try {			
			stmt.executeUpdate(sql);
			sql="UPDATE TACHE SET ID_MEMBRE = NULL WHERE ID_MEMBRE=0;";
			stmt.executeUpdate(sql);
			System.out.println("\n\t\t\t\tTache enregistree\n");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("\n\t\t\t\tErreur !. La tache n'a pas ete cree.\n");
		}
	}

	//Methode permettant de creer un membre
	public boolean creerMembre(Membre membre) {		
		
		membre.setNom(membre.getNom().replace('\'', ' '));
		
		try {
			stmt.executeUpdate("INSERT INTO MEMBRE (NOM) " + 
					"VALUES ('" + membre.getNom()+"');");
			System.out.println("\n\t\t\t\t\t\tMembre enregistre\n");
		} catch (SQLException e) {
			System.out.println("\n\t\t\t\t\t\tErreur ! Le membre n'a pas ete cree\n");
			return false;
		}
		return true;
	}

	//Methode permettant d'afficher les taches assignees a un membre
	public List<Tache> afficherTachesMembre(int identifiant) throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM TACHE WHERE ID_MEMBRE = " + identifiant + ";");
		List<Tache> listeTachesmembre = new ArrayList<Tache>();

		while (rs.next()) {
			int id = rs.getInt("ID");
			String name = rs.getString("NOM");
			String description = rs.getString("DESCRIPTION");
			String status = rs.getString("STATUS");
			int idMembre = rs.getInt("ID_MEMBRE");
			listeTachesmembre.add(new Tache(id, name, description, status, idMembre));
		}
		return listeTachesmembre;
	}

	//Methode permettant d'afficher toutes les taches selon leurs status et les infos du membre associe
	public List<Object[]> afficherStatusTaches(int statusARechercher) throws SQLException {

		List<Object[]> listeTachesStatus = new ArrayList<Object[]>();	
		//List temporaire=new ArrayList();
		Object[] temporaire =new Object[2];
		String stringStatus;
		
		if (statusARechercher == 1) {
			stringStatus = "nouveau";
		} else if (statusARechercher == 2) {
			stringStatus = "en-progres";
		} else {
			stringStatus = "termine";
		}
		rs = stmt.executeQuery("SELECT T.ID AS tacheID, T.NOM AS tacheNOM, T.DESCRIPTION AS tacheDESCRIPTION,"
				+ "T.STATUS AS tacheSTATUS, M.ID AS membreID, M.NOM AS membreNOM  FROM TACHE AS T "
				+ "LEFT JOIN MEMBRE AS M  ON T.ID_MEMBRE = M.ID WHERE T.STATUS = '"+stringStatus+"';");

		while (rs.next()) {
			//Creation objet tache
			int id = rs.getInt("tacheID");
			String name = rs.getString("tacheNOM");
			String description = rs.getString("tacheDESCRIPTION");
			String status = rs.getString("tacheSTATUS");

			//Creation objet membre
			int idMembre = rs.getInt("membreID");
			String nomMembre=rs.getString("membreNOM");

			temporaire[0]=new Tache(id, name, description, status, idMembre);
			temporaire[1]=new Membre(idMembre,nomMembre);
			listeTachesStatus.add(temporaire);
		}
		return listeTachesStatus;
	}

	// Methode permettant de recuperer et d'afficher tous les membres
	public List<Membre> afficherMembres() throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM MEMBRE;");
		List<Membre> listeMembres = new ArrayList<Membre>();
		while (rs.next()) {
			int id = rs.getInt("ID");
			String name = rs.getString("NOM");
			listeMembres.add(new Membre(id, name));
		}
		return listeMembres;
	}

	// Methode permettant de recuperer et d'afficher toutes les taches
	public List<Tache> afficherTaches() throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM TACHE;");
		List<Tache> listeTaches = new ArrayList<Tache>();

		while (rs.next()) {
			int id = rs.getInt("ID");
			String name = rs.getString("NOM");
			String description = rs.getString("DESCRIPTION");
			String status = rs.getString("STATUS");
			int idMembre = rs.getInt("ID_MEMBRE");
			listeTaches.add(new Tache(id, name, description, status, idMembre));
		}
		return listeTaches;
	}

	//Modifications
	public boolean modifierTache(Tache tache) {		
		
		tache.setNom(tache.getNom().replace('\'', ' '));
		tache.setDescription((tache.getDescription().replace('\'', ' ')));
		tache.setStatus((tache.getNom().replace('\'', ' ')));
		
		try {
			stmt.executeUpdate("UPDATE TACHE SET NOM='"+tache.getNom()+"',DESCRIPTION='"+tache.getDescription()+
					"',STATUS='"+tache.getStatus()+"',ID_MEMBRE='"+tache.getIdMembre()+
					"' WHERE ID='"+tache.getId()+"';"); 

			System.out.println("\n\t\t\t\tTache modifie\n");
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public void modifierMembre(Membre membre) {		
		
		membre.setNom(membre.getNom().replace('\'', ' '));
		
		try {
			stmt.executeUpdate("UPDATE MEMBRE SET NOM='"+membre.getNom()+
					"' WHERE ID='"+membre.getId()+"';"); 

			System.out.println("\n\t\t\t\tMembre modifie\n");
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("\t\t\t\t!!! Echec de la modification !!! Verifiez l'ID\n");
		}
	}

	//Methode d'assignation de tache
	public void assignerTacheAUnMembre(int idTache, int idMembre){	
		try {
			stmt.executeUpdate("UPDATE TACHE SET ID_MEMBRE='"+idMembre
					+"'WHERE ID='"+idTache+"';"); 

			System.out.println("\n\t\t\t\tTache assignee\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 

	//Suppressions
	public void supprimerTache(int idTache) {
		String sql = "DELETE FROM TACHE WHERE ID="+ idTache + ";";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("\n\t\t\t\tTache supprimee\n");
	}

	public void supprimerMembre(int idMembre) {
		String sql = "DELETE FROM MEMBRE WHERE ID="+ idMembre + ";";
		try {
			stmt.executeUpdate(sql);
			sql="UPDATE TACHE SET ID_MEMBRE = NULL WHERE ID_MEMBRE=" + idMembre +";";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("\n\t\t\t\tMembre supprime\n");
	}

	// Methode permettant de fermer et liberer les ressources
	public void fermerRessourceBaseDeDonnees() {
		try {
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur de fermeture des ressources de la base de donnees\n\n");
		}
	}
}
