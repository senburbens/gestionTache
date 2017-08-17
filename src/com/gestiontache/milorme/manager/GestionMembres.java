package com.gestiontache.milorme.manager;

import java.sql.SQLException;
import java.util.List;

import com.gestiontache.milorme.affichage.AffichageSurEcran;
import com.gestiontache.milorme.entites.Membre;
import com.gestiontache.milorme.app.Application;

public class GestionMembres implements Gestion{

	private static int reponse;

	public static void gererMembres(){

		AffichageSurEcran.afficherMenuGestionMembres();		
		reponse=0;

		while(reponse<1 || reponse>5){
			Application.verifierType();
			reponse = sc.nextInt();		
			if(reponse<1 || reponse>5)
				System.out.println("Entrez un entier entre 1 et 5 inclus\n");
		}

		switch(reponse){			
			case 1 :
				creerMembre();
				break;
			case 2 :
				modifierMembre();
				break;
			case 3 :
				supprimerMembre();
				break;
			case 4 :
				afficherMembres();
				break;
			case 5 :
				System.out.println("\nOperation annulee\n");
				break;
		}
	}
	
	
	private static void creerMembre(){
		reponse=1;
		System.out.println("\n---> CREATION MEMBRE\n");
		while(reponse==1){
			System.out.println("\t\tNom membre  : \n");
			sc.nextLine();
			String nomMembre=sc.nextLine();
			System.out.println("Ajouter le membre cree ?\n1 : OUI\t2 : NON");
			reponse=0;
			while(reponse<1 || reponse>2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.println("Entrez 1 ou 2\nChoix : ");
				}
			}
			if(reponse==1){
				base.creerMembre(new Membre(0,nomMembre));
			}						
			System.out.println("Voulez-vous creer un autre membre ?\n1 : OUI\t2 : NON");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.println("Entrez 1 ou 2\nChoix : ");
				}
			}
		}		
	}


	private static void modifierMembre(){
		reponse=1;
		
		while(reponse==1){

			try {
				//Affichage des membres
				System.out.println("\n\n---> LISTE DES MEMBRES");
				List<Membre> listeMembre=base.afficherMembres();
				for(Membre t : listeMembre){
					System.out.println("\nIDENTIFIANT : " + t.getId()+ "\nNOM : " + t.getNom());
					System.out.println();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			System.out.println("\n---> Identifiant du membre a modifier\nID :");
			Application.verifierType();
			int identifiantMembreAModifier=sc.nextInt();							
			System.out.println("\n---> Modification du membre\n");
			System.out.println("Nom du membre  : \n");
			sc.nextLine();
			String nomMembreAModifier=sc.nextLine();
			System.out.println("Voulez-vous proceder a la modification ?\n1 : OUI\t2 : NON");
			reponse=0;
			while(reponse<1 || reponse>2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.println("Entrez 1 ou 2\nChoix : ");
				}
			}
			if(reponse==1){
				base.modifierMembre(new Membre(identifiantMembreAModifier,nomMembreAModifier));
			}					
			System.out.println("Voulez-vous modifier un autre membre ?\n1 : OUI\t2 : NON");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.println("Entrez 1 ou 2\nChoix : ");
				}
			}
		}

	}

	private static void supprimerMembre(){
		reponse=1;
		while(reponse==1){
			System.out.println("\n\n---> LISTE DES MEMBRES");
			try {
				List<Membre> listeMembre=base.afficherMembres();
				for(Membre t : listeMembre){
					System.out.println("\nID : " + t.getId()+ "\nNOM : " + t.getNom());
					System.out.println();
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("\n---> Identifiant du membre a supprimer\n");
			Application.verifierType();
			int identifiantMembre=sc.nextInt();	
			System.out.println("Voulez-vous proceder a la suppression ?\n1 : OUI\t2 : NON");
			reponse=0;
			while(reponse<1 || reponse>2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.println("Entrez 1 ou 2\nChoix : ");
				}
			}
			if(reponse==1){
				base.supprimerMembre(identifiantMembre);
			}	
			System.out.println("Voulez-vous supprimer un autre membre ?\n1 : OUI\t2 : NON");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.println("Entrez 1 ou 2\nChoix : ");
				}
			}
		}
	}

	private static void afficherMembres(){
		try {
			AffichageSurEcran.afficherMembres(base.afficherMembres());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}		
	}
}
