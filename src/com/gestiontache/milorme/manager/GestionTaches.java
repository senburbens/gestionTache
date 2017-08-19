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
			try {
				modifierTache();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 3 :
			supprimerTache();
			break;
		case 4 :
			assignerTache();
			break;
		case 5 :
			afficherTaches();
			break;
		case 6 :
			System.out.println("\nOperation annulee\n");
			break;
		}
	}


	private static void creerTache(){
		reponse=1;
		System.out.println("\n\t\t\t\t---> CREATION DE TACHES\n");
		while(reponse==1){
			System.out.println("\n\n\t\t\t\tINFORMATIONS SUR LES MEMBRES\n");
			try {
				List<Membre> listeMembre=base.afficherMembres();
				for(Membre m : listeMembre){
					System.out.println("\n\t\t\t\tIDENTIFIANT : " + m.getId()+ "\t\tNOM : " + m.getNom());
					System.out.println();
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("\n\t\t\t\t---> Creation de tache\n");
			System.out.print("\t\t\t\tNom tache  : ");
			sc.nextLine();
			String nomTache=sc.nextLine();
			System.out.print("\t\t\t\tDescription : ");
			String descriptionTache=sc.nextLine();
			System.out.print("\t\t\t\tIdentifiant du membre (0 si vous ne voulez pas assigner maintenant) : ");
			Application.verifierType();
			int identifiant=sc.nextInt();	
			System.out.print("\t\t\t\tAjouter la tache creee ?\n\t\t\t\t1 : OUI\t2 : NON\n\t\t\t\tReponse : ");
			reponse=0;
			while(reponse<1 || reponse>2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.println("\t\t\t\tEntrez 1 ou 2\n\t\t\t\tChoix : ");
				}
			}
			if(reponse==1){
				base.creerTache(new Tache(0,nomTache,descriptionTache,"",identifiant));
			}	

			System.out.print("\t\t\t\tVoulez-vous creer une autre tache ?\n\t\t\t\t1 : OUI\t2 : NON\n\t\t\t\tChoix : ");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.print("\t\t\t\tEntrez 1 ou 2\n\t\t\t\tChoix : ");
				}
			}
		}
	}

	private static void modifierTache() throws SQLException{
		reponse=1;
		List<Tache> listeTache;
		while(reponse==1){
			System.out.println("\n\n\t\t\t\tLISTE DES TACHES\n");
			try {
				listeTache=base.afficherTaches();
				for(Tache t : listeTache){
					System.out.println("\n\t\t\t\tIDENTIFIANT : " + t.getId()+ "\n\t\t\t\tNOM : " + t.getNom() + "\n\t\t\t\tDESCRIPTION : "+ t.getDescription() + "\n\t\t\t\tSTATUS : " +
							t.getStatus() + "\n\t\t\t\tIDENTIFIANT MEMBRE : "+ t.getIdMembre());
					System.out.println();
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.print("\n\t\t\t\tIdentifiant de la tache a modifier\n\t\t\t\tID :");
			Application.verifierType();
			int identifiantTacheAModifier=sc.nextInt();

			System.out.println("\n\t\t\t\t---> Modification de la tache\n");
			System.out.print("\t\t\t\tNom tache  : ");
			sc.nextLine();
			String nomTacheAModifier=sc.nextLine();
			System.out.print("\t\t\t\tDescription : ");
			String descriptionTacheAModifier=sc.nextLine();
			//Verifier que l'utilisateur a entrer un entier
			System.out.print("\t\t\t\tIdentifiant du membre auquel assigner la tache : ");
			AffichageSurEcran.afficherMembres(base.afficherMembres());
			System.out.print("\t\t\t\tChoix : ");
			Application.verifierType();
			int identifiantDuMembre=sc.nextInt();

			String statusAModifier="";
			sc.nextLine();
			while(!statusAModifier.equalsIgnoreCase("nouveau") && !statusAModifier.equalsIgnoreCase("en-progres") && !statusAModifier.equalsIgnoreCase("termine")){
				System.out.print("\t\t\t\tModification du statut : (nouveau, en-progres ou termine) :\n\t\t\t\t");
				statusAModifier=sc.nextLine();
				if(!statusAModifier.equalsIgnoreCase("nouveau") && !statusAModifier.equalsIgnoreCase("en-progres") && !statusAModifier.equalsIgnoreCase("termine")){
					System.out.println("\t\t\t\tMauvaise saisie. Entrez : nouveau ou en-progres ou termine");
				}
			}

			System.out.print("\t\t\t\tVoulez-vous proceder a la modification ?\n\t\t\t\t1 : OUI\t2 : NON\n\t\t\t\tChoix : ");
			reponse=0;
			while(reponse<1 || reponse>2){
				Application.verifierType();
				reponse=sc.nextInt();
				System.out.print("\t\t\t\tEntrez 1 ou 2\n\t\t\t\tChoix : ");
			}
			if(reponse==1){
				base.modifierTache(new Tache(identifiantTacheAModifier,nomTacheAModifier,descriptionTacheAModifier,statusAModifier,identifiantDuMembre));
			}			
			System.out.print("\t\t\t\tVoulez-vous modifier un autre membre ?\n\t\t\t\t1 : OUI\t2 : NON\n\t\t\t\tChoix : ");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
				System.out.print("\t\t\t\tEntrez 1 ou 2\nChoix : ");
			}
		}
	}

	private static void supprimerTache(){
		reponse=1;
		List<Tache> listeTache;
		while(reponse==1){
			System.out.println("\n\n\t\t\t\t---> LISTE DES TACHES\n");
			try {
				listeTache=base.afficherTaches();
				for(Tache t : listeTache){
					System.out.println("\n\t\t\t\tIDENTIFIANT : " + t.getId()+ "\n\t\t\t\tNOM : " + t.getNom() + "\n\t\t\t\tDESCRIPTION"+ t.getDescription() + "\n\t\t\t\tSTATUS" +
							t.getStatus() + "\n\t\t\t\tIDENTIFIANT MEMBRE"+ t.getIdMembre());
					System.out.println();
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.print("\n\t\t\t\t---> ID : ");
			Application.verifierType();
			int identifiantTacheASupprimer=sc.nextInt();	
			System.out.print("\t\t\t\tVoulez-vous proceder a la suppression ?\n\t\t\t\t1 : OUI\t2 : NON\n\t\t\t\tChoix : ");
			reponse=0;
			while(reponse<1 || reponse>2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.print("\t\t\t\tEntrez 1 ou 2\n\t\t\t\tChoix : ");
				}
			}
			if(reponse==1){
				base.supprimerTache(identifiantTacheASupprimer);
			}
			System.out.print("\t\t\t\tVoulez-vous supprimer une autre tache ?\n\t\t\t\t1 : OUI\t2 : NON\n\t\t\t\tChoix : ");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.print("\t\t\t\tEntrez 1 ou 2\n\t\t\t\tChoix : ");
				}
			}
		}
	} 

	private static void assignerTache(){
		//Assigner tache a un membre code here
		reponse=1;
		List<Tache> listeTache;
		while(reponse==1){
			System.out.println("\n\n\t\t\t\t---> LISTE DES TACHES\n");
			try {
				//Affichage des taches
				listeTache=base.afficherTaches();
				for(Tache t : listeTache){
					System.out.println("\n\t\t\t\tIDENTIFIANT : " + t.getId()+ "\n\t\t\t\tNOM : " + t.getNom() + "\n\t\t\t\tDESCRIPTION"+ t.getDescription() + "\n\t\t\t\tSTATUS" +
							t.getStatus() + "\n\t\t\t\tIDENTIFIANT MEMBRE"+ t.getIdMembre());
					System.out.println();
				}	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.print("\n\t\t\t\tID Tache : ");
			Application.verifierType();
			int identifiantTacheAAssigner=sc.nextInt();

			//Affichage des membres
			System.out.println("\n\n\t\t\t\t---> LISTE DES MEMBRES");
			List<Membre> listeMembre;
			try {
				listeMembre = base.afficherMembres();
				for(Membre t : listeMembre){
					System.out.println("\n\t\t\t\tIDENTIFIANT : " + t.getId()+ "\n\t\t\t\tNOM : " + t.getNom());
					System.out.println();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.print("\n\t\t\t\tID membre : ");
			Application.verifierType();
			int identifiantMembreAAssigner=sc.nextInt();
			System.out.print("\t\t\t\tVoulez-vous proceder a l'assignation ?\n\t\t\t\t1 : OUI\t2 : NON\n\t\t\t\tChoix : ");
			reponse=0;
			while(reponse<1 || reponse>2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.print("\t\t\t\tEntrez 1 ou 2\n\t\t\t\tChoix : ");
				}
			}
			if(reponse==1){
				base.assignerTacheAUnMembre(identifiantTacheAAssigner, identifiantMembreAAssigner);
			}

			System.out.println("\t\t\t\tVoulez-vous assigner une autre tache ?\n\t\t\t\t1 : OUI\t2 : NON\n\t\t\t\tChoix : ");
			reponse=0;
			while(reponse<1 || reponse >2){
				Application.verifierType();
				reponse=sc.nextInt();
				if(reponse<1 || reponse>2){
					System.out.print("\t\t\t\tEntrez 1 ou 2\n\t\t\t\tChoix : ");
				}
			}
		}
	}

	private static void afficherTaches(){
		try {
			AffichageSurEcran.afficherTaches(base.afficherTaches());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private static boolean verificationID(){

		return true;
	}

}
