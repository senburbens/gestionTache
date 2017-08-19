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
		System.out.println("\n\t\t\t\t---> CREATION MEMBRE");
		while(reponse==1){
			System.out.print("\n\t\t\t\t\t\tNom membre  : ");
			sc.nextLine();
			String nomMembre=sc.nextLine();
			System.out.print("\n\t\t\t\t\t\tAjouter le membre cree ?\n\t\t\t\t\t\t1 : OUI\t2 : NON\n\t\t\t\t\t\tChoix : ");
			reponse=0;
			while(reponse<1 || reponse>2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.print("\t\t\t\t\t\tEntrez 1 ou 2\n\t\t\t\t\t\tChoix : ");
				}
			}
			if(reponse==1){
				base.creerMembre(new Membre(0,nomMembre));
			}						
			System.out.print("\n\t\t\t\t\t\tVoulez-vous creer un autre membre ?\n\t\t\t\t\t\t1 : OUI\t2 : NON\n\t\t\t\t\t\tChoix : ");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.println("\t\t\t\t\t\tEntrez 1 ou 2\n\t\t\t\t\t\tChoix : ");
				}
			}
		}		
	}


	private static void modifierMembre(){
		reponse=1;
		List<Membre> listeMembre;
		
		while(reponse==1){

			try {
				//Affichage des membres
				System.out.println("\n\n\t\t\t\t---> LISTE DES MEMBRES");
				listeMembre=base.afficherMembres();
				for(Membre t : listeMembre){
					System.out.println("\n\t\t\t\tIDENTIFIANT : " + t.getId()+ "\n\t\t\t\tNOM : " + t.getNom());
					System.out.println();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			System.out.print("\n\t\t\t\t---> Identifiant du membre a modifier\n\t\t\t\tID : ");
			Application.verifierType();
			int identifiantMembreAModifier=sc.nextInt();							
			System.out.println("\n\t\t\t\t---> Modification du membre\n");
			System.out.print("\t\t\t\tNom du membre  : ");
			sc.nextLine();
			String nomMembreAModifier=sc.nextLine();
			System.out.print("\t\t\t\tVoulez-vous proceder a la modification ?\n\t\t\t\t1 : OUI\t2 : NON\n\t\t\t\tChoix : ");
			reponse=0;
			while(reponse<1 || reponse>2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.print("\t\t\t\tEntrez 1 ou 2\n\t\t\t\tChoix : ");
				}
			}
			if(reponse==1){
				base.modifierMembre(new Membre(identifiantMembreAModifier,nomMembreAModifier));
			}					
			System.out.println("\t\t\t\tVoulez-vous modifier un autre membre ?\n\t\t\t\t1 : OUI\t2 : NON");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.println("\t\t\t\tEntrez 1 ou 2\nChoix : ");
				}
			}
		}

	}

	private static void supprimerMembre(){
		reponse=1;
		List<Membre> listeMembre;
		
		while(reponse==1){
			System.out.println("\n\n\t\t\t\t---> LISTE DES MEMBRES");
			try {
				listeMembre=base.afficherMembres();
				for(Membre t : listeMembre){
					System.out.println("\n\t\t\t\tID : " + t.getId()+ "\n\t\t\t\tNOM : " + t.getNom());
					System.out.println();
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.print("\n\t\t\t\t---> Identifiant du membre a supprimer : ");
			Application.verifierType();
			int identifiantMembre=sc.nextInt();	
			System.out.println("\t\t\t\tVoulez-vous proceder a la suppression ?\n\t\t\t\t1 : OUI\t2 : NON");
			reponse=0;
			while(reponse<1 || reponse>2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.println("\t\t\t\tEntrez 1 ou 2\n\t\t\t\tChoix : ");
				}
			}
			if(reponse==1){
				base.supprimerMembre(identifiantMembre);
			}	
			System.out.print("\t\t\t\tVoulez-vous supprimer un autre membre ?\n\t\t\t\t1 : OUI\t2 : NON\n\t\t\t\tChoix : ");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.println("\t\t\t\tEntrez 1 ou 2\n\t\t\t\tChoix : ");
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
	
	private static boolean verificationID(){
		
		return true;
	}
}
