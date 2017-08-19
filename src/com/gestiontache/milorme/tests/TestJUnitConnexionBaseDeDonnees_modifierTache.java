package com.gestiontache.milorme.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.gestiontache.milorme.basededonnees.ConnexionBaseDeDonnees;
import com.gestiontache.milorme.entites.Membre;
import com.gestiontache.milorme.entites.Tache;

public class TestJUnitConnexionBaseDeDonnees_modifierTache {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ConnexionBaseDeDonnees base = ConnexionBaseDeDonnees.recupererObjetSingleton();
		Tache tache = new Tache(1,"tacheTest","Test de la methode modifierTache","nouveau",1);
		boolean modifierTacheReussie=base.modifierTache(tache);
		assertEquals(true,modifierTacheReussie);
	}
}
