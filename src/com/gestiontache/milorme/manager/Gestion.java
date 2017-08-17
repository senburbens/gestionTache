package com.gestiontache.milorme.manager;

import java.util.Scanner;

import com.gestiontache.milorme.basededonnees.ConnexionBaseDeDonnees;

public interface Gestion {	
	public Scanner sc = new Scanner(System.in);
	public ConnexionBaseDeDonnees base = ConnexionBaseDeDonnees.recupererObjetSingleton();
}
