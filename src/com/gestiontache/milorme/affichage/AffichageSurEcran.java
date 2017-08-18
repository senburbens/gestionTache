package com.gestiontache.milorme.affichage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.gestiontache.milorme.entites.Tache;
import com.gestiontache.milorme.manager.Gestion;
import com.gestiontache.milorme.entites.Membre;
import com.gestiontache.milorme.app.Application;
import com.gestiontache.milorme.basededonnees.ConnexionBaseDeDonnees;


public class AffichageSurEcran  implements Gestion {	
	
	private static int reponse=0;
	
	//Methodes d'affichage utilisees dans le programme
	//Centralisation dans une meme classe des methodes d'affichage les plus importantes du programme
	//Dans un souci d'organisation

	public static void messageBienvenue(){
		System.out.println("\n\n\t\t\t\t\t===============================================");
		System.out.println("\t\t\t\t\t!!! Bienvenue dans le programme de gestion des\n\t\t\t\t\ttaches.\n\t\t\t\t\t\tCe programme permet de definir des\n\t\t\t\t\ttaches et de"
				+ " les attribuer a des membres !!!\n\n\t\t\t\t\t\tRealise par\n\t\t\t\t\t\t\tPierre Rubens MILORME");
		System.out.println("\t\t\t\t\t===============================================\n\n");
	}

	public static void affichageMenuPrincipal(){
		System.out.print("\n\n\t\tMENU PRINCIPAL\n");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.print("1 : GESTION DES TACHES\n");
		System.out.print("2 : GESTION DES MEMBRES\n");
		System.out.print("3 : AFFICHAGE\n");
		System.out.print("4 : SORTIR DU PROGRAMME\n");
		System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nChoix : ");
	}

	public static void afficherMembres(List<Membre> listeMembres){	
		System.out.println("\n\nLISTE DES MEMBRES EXISTANTS\n");
		for(Membre m : listeMembres){
			System.out.print(m.getId());
			System.out.println("\t\t"+m.getNom());
			System.out.println("----------");
		}
	}

	public static void afficherTaches(List<Tache> listeTaches){	
		System.out.println("\n\nLISTE DES TACHES EXISTANTES\n");
		for(Tache t : listeTaches){
			System.out.println(t.getId());
			System.out.println(t.getNom());
			System.out.println(t.getDescription());
			System.out.println(t.getStatus());
			System.out.println(t.getIdMembre());
			System.out.println("");
		}
	}

	public static void afficherMenuGestionTaches(){
		System.out.println("\n\n\t\t---> GESTION DES TACHES\n");
		System.out.print("\t\t1 : Creer une tache\n");
		System.out.print("\t\t2 : Modifier une tache\n");
		System.out.print("\t\t3 : Supprimmer une tache\n");
		System.out.print("\t\t4 : Assigner une tache a un membre\n");
		System.out.print("\t\t5 : Afficher toutes les taches\n");
		System.out.print("\t\t6 : Annuler\n");
		System.out.print("\n\t\tChoix : ");			
	}

	public static void afficherMenuGestionMembres(){
		System.out.println("\n\n\t\t---> GESTION DES MEMBRES\n");
		System.out.print("\t\t1 : Creer un membre\n");
		System.out.print("\t\t2 : Modifier un membre\n");
		System.out.print("\t\t3 : Supprimmer un membre\n");
		System.out.print("\t\t4 : Afficher tous les membres\n");
		System.out.print("\t\t5 : Annuler\n");
		System.out.print("\n\t\tChoix : ");			
	}

	public static void afficherMenuAffichage(){
		System.out.println("\n\n\t\t---> AFFICHAGE\n");
		System.out.print("\t\t1 : Afficher les taches d'un membre\n");
		System.out.print("\t\t2 : Affichage des taches en fonction de leur status\n");
		System.out.print("\t\t3 : Annuler\n");
		System.out.print("\n\t\tChoix : ");			
	}


	//Affichage de toutes les taches d'un membre 
	//Affichage des taches en fonctions de leurs statuts
	public static void affichage(){

		AffichageSurEcran.afficherMenuAffichage();
		reponse=0;

		while(reponse<1 || reponse>3){	
			Application.verifierType();
			reponse = sc.nextInt();		
			if(reponse<1 || reponse>3)
				System.out.println("Entrez 1, 2 ou 3\n");
		}		
		switch(reponse){			

		case 1 :
			afficherTachesMembre();           
			break;
		case 2 :
			afficherStatutTaches();
			break;
		case 3 :
			annulerAction();
			break;
		}
	}

	private static void afficherTachesMembre(){
		
		//Affichage des informations sur les membres
		reponse=1;
		while(reponse==1){
			System.out.println("\n\n---> INFORMATIONS SUR LES MEMBRES\n");
			try {
				List<Membre> listeMembre=base.afficherMembres();
				for(Membre m : listeMembre){
					System.out.println("\nID : " + m.getId()+ "\t\tNOM : " + m.getNom());
					System.out.println();
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}

			System.out.print("\n---> Identifiant du membre pour lequel afficher les taches\n\n\tID :");
			Application.verifierType();
			reponse=sc.nextInt();
			//Affichage des taches du membre 
			System.out.println("\n\nAffichage des taches du membre\n");
			try {
				List<Tache> listeTaches=base.afficherTachesMembre(reponse);
				System.out.print("ID\t\tNOM\t\tDESCRIPTION\t\tSTATUS\t\tMEMBRE\n\n");
				for(Tache m : listeTaches){
					System.out.print(m.getId());
					System.out.print("\t\t" + m.getNom());
					System.out.print("\t\t" + m.getDescription());
					System.out.print("\t\t" + m.getStatus());
					System.out.println("\t\t" + m.getIdMembre());
					System.out.println();
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}   
			System.out.println("\n\nRecommencer ?1 : OUI\t2 : NON\n\nreponse : ");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
			}
		}  
	}
	
	private static void afficherStatutTaches(){
		reponse=1;
		while(reponse==1){
			System.out.println("---> Veuillez entrer le status des taches a afficher\n\t 1 : nouveau\t2 : en_progres\t3 : termine \n");

			reponse=0;			
			while(reponse<1 || reponse>3){
				Application.verifierType();
				reponse = sc.nextInt();		
				if(reponse<1 || reponse>2)
					System.out.println("Entrez 1, 2 ou 3\n");
			}	
			try {
				List<Object[]>  statusTaches=base.afficherStatusTaches(reponse);
				Tache t;
				Membre m;

				for(Object[] obj : statusTaches){
					t= (Tache) obj[0];
					m= (Membre) obj[1];

					System.out.println("ID: " + t.getId());
					System.out.println("NOM TACHE: " + t.getNom());
					System.out.println("DESCRIPTION TACHE: " + t.getDescription());
					System.out.println("STATUS TACHE: " + t.getStatus());
					System.out.println("ID MEMBRE: " + m.getId());
					System.out.println("NOM MEMBRE: " + m.getNom());
					System.out.println();
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("\n\nRecommencer ?1 : OUI\t2 : NON\n\nreponse : ");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
			}
		}
	}
	
	private static void annulerAction(){
		try {
			Runtime.getRuntime().exec("clear");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Action Annulee\n");
	}
}
