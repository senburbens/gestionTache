package com.gestiontache.milorme.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import com.gestiontache.milorme.entites.Tache;
import com.gestiontache.milorme.manager.GestionMembres;
import com.gestiontache.milorme.manager.GestionTaches;
import com.gestiontache.milorme.affichage.AffichageSurEcran;
import com.gestiontache.milorme.manager.Gestion;
import com.gestiontache.milorme.basededonnees.ConnexionBaseDeDonnees;
import com.gestiontache.milorme.entites.Membre;

public class Application implements Gestion{

	//Methode principale de l'application
	public static void lancerApplication() {	
	    int reponse=0;

		AffichageSurEcran.messageBienvenue();//Appel de la methode messagBienvenue

		while(true){

			AffichageSurEcran.affichageMenuPrincipal(); //Methode affichage du menu principale
			reponse=0;

			while(reponse<1 || reponse>4){
				verifierType();
				reponse = sc.nextInt();	
				if(reponse<1 || reponse>4)
					System.out.println("Entrez 1, 2, 3, ou 4\nChoix :");
			}

			switch(reponse){			
			//GESTION DES TACHES
			case 1 :
				GestionTaches.gererTaches();
				break;
			//GESTION DES MEMBRES	
			case 2 :
				GestionMembres.gererMembres();
				break;
			//AFFICHAGE
			case 3 :
				AffichageSurEcran.affichage();
				break;		
			//SORTIR DU PROGRAMME
			case 4 :
				terminerProgramme();
				break;			
			}			
		}
	}

	//Fonction de verification de type 
	public static void verifierType(){
		if(!sc.hasNextInt()){
			System.out.print("Veuillez entrer un entier !!!\nChoix :");
			sc.nextLine();
			verifierType();
		}
	}
	
	//Mettre fin au programme
	private static void terminerProgramme(){
		System.out.println("\n\nAU REVOIR ET A BIENTOT !!!");
		base.fermerRessourceBaseDeDonnees();
		System.exit(0);		
	}


}
