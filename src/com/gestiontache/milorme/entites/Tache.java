package com.gestiontache.milorme.entites;

public class Tache {
	
	//Constructeur
	public Tache(int id, String nom, String description, String status, int idMembre) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.status = status;
		this.idMembre = idMembre;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getIdMembre() {
		return idMembre;
	}

	public void setIdMembre(int idMembre) {
		this.idMembre = idMembre;
	}

	//Variables d'instances 
	private int id;
	private String nom;
	private String description;
	private String status;
	private int idMembre;//Identifiant du membre associe a la tache
	
}
