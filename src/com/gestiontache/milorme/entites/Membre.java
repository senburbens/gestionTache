package com.gestiontache.milorme.entites;

public class Membre {	
	//Constructeur
	public Membre(int id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	//Getters et Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	//Variables d'instances
	private int id;
	private String nom;	
}
