package com.gestiontache.milorme.manager;

import java.sql.SQLException;
import java.util.List;
import com.gestiontache.milorme.entites.Tache;
import com.gestiontache.milorme.affichage.AffichageSurEcran;
import com.gestiontache.milorme.entites.Membre;
import com.gestiontache.milorme.app.Application;


public class GestionTaches implements Gestion{

	private static int reponse;

	public static void gererTaches(){
		
		AffichageSurEcran.afficherMenuGestionTaches();
		reponse=0;

		while(reponse<1 || reponse>6){
			Application.verifierType();
			reponse = sc.nextInt();		
			if(reponse<1 || reponse>6)
				System.out.println("Entrez un entier entre 1 et 5 inclus\n");
		}		
		
		switch(reponse){
			case 1 :			
				creerTache();
				break;
			case 2 :
				modifierTache();
				break;
			case 3 :
				supprimerTache();
				break;
			case 4 :
				assignerTache();
				break;
			case 5 :
				afficherTache();
				break;
			case 6 :
				System.out.println("\nOperation annulee\n");
				break;
		}
	}


	private static void creerTache(){
		reponse=1;
		System.out.println("\n---> CREATION DE TACHES\n");
		while(reponse==1){
			System.out.println("\n\n---> INFORMATIONS SUR LES MEMBRES\n");
			try {
				List<Membre> listeMembre=base.afficherMembres();
				for(Membre m : listeMembre){
					System.out.println("\nIDENTIFIANT : " + m.getId()+ "\t\tNOM : " + m.getNom());
					System.out.println();
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("\n---> Creation de tache\n");
			System.out.println("Nom tache  : \n");
			sc.nextLine();
			String nomTache=sc.nextLine();
			System.out.println("Description : \n");
			String descriptionTache=sc.nextLine();
			System.out.println("Identifiant du membre (0 si vous ne voulez pas assigner maintenant) : \n");
			Application.verifierType();
			int identifiant=sc.nextInt();	
			System.out.println("Ajouter la tache creee ?\n1 : OUI\t2 : NON");
			reponse=0;
			while(reponse<1 || reponse>2){
				Application.verifierType();
				reponse=sc.nextInt();
			}
			if(reponse==1){
				base.creerTache(new Tache(0,nomTache,descriptionTache,"",identifiant));
			}	

			System.out.println("Voulez-vous creer une autre tache ?\n1 : OUI\t2 : NON");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
			}
		}
	}

	private static void modifierTache(){
		reponse=1;

		while(reponse==1){
			System.out.println("\n\n---> LISTE DES TACHES\n");
			try {
				List<Tache> listeTache=base.afficherTaches();
				for(Tache t : listeTache){
					System.out.println("\nIDENTIFIANT : " + t.getId()+ "\n\tNOM : " + t.getNom() + "\n\tDESCRIPTION : "+ t.getDescription() + "\n\tSTATUS : " +
							t.getStatus() + "\n\tIDENTIFIANT MEMBRE : "+ t.getIdMembre());
					System.out.println();
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("\n---> Identifiant de la tache a modifier\nID :");
			int identifiantTacheAModifier=sc.nextInt();

			System.out.println("\n---> Modification de la tache\n");
			System.out.println("Nom tache  : \n");
			sc.nextLine();
			String nomTacheAModifier=sc.nextLine();
			System.out.println("Description : \n");
			String descriptionTacheAModifier=sc.nextLine();
			//Verifier que l'utilisateur a entrer un entier
			System.out.println("Identifiant du membre auquel assigner la tache : \n");
			int identifiantDuMembre=sc.nextInt();
			System.out.println("Modification du statut : \n");
			sc.nextLine();
			String statusAModifier = sc.nextLine();
			System.out.println("Voulez-vous proceder a la modification ?\n1 : OUI\t2 : NON");
			reponse=0;
			while(reponse<1 || reponse>2){
				Application.verifierType();
				reponse=sc.nextInt();
			}
			if(reponse==1){
				base.modifierTache(new Tache(identifiantTacheAModifier,nomTacheAModifier,descriptionTacheAModifier,statusAModifier,identifiantDuMembre));
			}			
			System.out.println("Voulez-vous modifier un autre membre ?\n1 : OUI\t2 : NON");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
			}
		}
	}

	private static void supprimerTache(){
		System.out.println("\n\n---> LISTE DES TACHES\n");
		try {
			List<Tache> listeTache=base.afficherTaches();
			for(Tache t : listeTache){
				System.out.println("\nIDENTIFIANT : " + t.getId()+ "\nNOM : " + t.getNom() + "\nDESCRIPTION"+ t.getDescription() + "\nSTATUS" +
						t.getStatus() + "\nIDENTIFIANT MEMBRE"+ t.getIdMembre());
				System.out.println();
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("\n---> Identifiant de la tache a supprimer\n");
		int identifiantTacheASupprimer=sc.nextInt();							
		base.supprimerTache(identifiantTacheASupprimer);
	} 

	private static void assignerTache(){
		//Assigner tache a un membre code here
		System.out.println("\n\n---> LISTE DES TACHES\n");
		try {
			//Affichage des taches
			List<Tache> listeTache=base.afficherTaches();
			for(Tache t : listeTache){
				System.out.println("\nIDENTIFIANT : " + t.getId()+ "\nNOM : " + t.getNom() + "\nDESCRIPTION"+ t.getDescription() + "\nSTATUS" +
						t.getStatus() + "\nIDENTIFIANT MEMBRE"+ t.getIdMembre());
				System.out.println();
			}	

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
		System.out.print("\nIdentifiant de la tache\nIdentifiantTache : ");
		int identifiantTacheAAssigner=sc.nextInt();
		System.out.print("\nIdentifiant du membre\nIdentifiantMembre : ");
		int identifiantMembreAAssigner=sc.nextInt();
		base.assignerTacheAUnMembre(identifiantTacheAAssigner, identifiantMembreAAssigner);
	}

	private static void afficherTache(){
		try {
			AffichageSurEcran.afficherTaches(base.afficherTaches());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
