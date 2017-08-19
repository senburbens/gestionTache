import static org.junit.Assert.*;

import java.awt.List;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.gestiontache.milorme.basededonnees.ConnexionBaseDeDonnees;
import com.gestiontache.milorme.entites.Membre;

public class TestJUnitConnexionBaseDeDonnees_creerMembre {

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
	public void test() throws SQLException {
		ConnexionBaseDeDonnees base = ConnexionBaseDeDonnees.recupererObjetSingleton();
		Membre membre = new Membre(0,"Cephas");
		boolean creationMembreReussie=base.creerMembre(membre);
		assertEquals(true,creationMembreReussie);	
		
	}
}
