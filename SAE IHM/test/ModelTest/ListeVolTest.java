package ModelTest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import Exceptions.InvalidHourException;
import Exceptions.InvalidLineFormatException;
import Exceptions.InvalidMinuteException;
import java.io.IOException;
import model.ListeVol;
import model.Vol;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mèjdi
 */
public class ListeVolTest {
    private ListeVol listeVol;
    private Vol vol1;
    private Vol vol2;
    
    public ListeVolTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        listeVol = new ListeVol();
        vol1 = new Vol("AF123", "Paris", "New York", 10, 30, 480); // Vol de Paris à New York
        vol2 = new Vol("BA456", "London", "Tokyo", 14, 0, 720); // Vol de Londres à Tokyo
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAjVol() {
        assertTrue(listeVol.ajVol(vol1));
        assertFalse(listeVol.ajVol(vol1)); // Ne doit pas ajouter deux fois le même vol
        assertEquals(1, listeVol.tailleList());
    }

    @Test
    public void testSupAeroport() {
        listeVol.ajVol(vol1);
        assertTrue(listeVol.supVol(vol1));
        assertFalse(listeVol.supVol(vol1)); // Ne doit pas supprimer si le vol n'est pas présent
        assertEquals(0, listeVol.tailleList());
    }

    @Test
    public void testAccesVol() {
        listeVol.ajVol(vol1);
        assertEquals(vol1, listeVol.accesVol(0));
        assertNull(listeVol.accesVol(1)); // Indice non pertinent
    }

    @Test
    public void testIndicePertinent() {
        listeVol.ajVol(vol1);
        assertTrue(listeVol.indicePertinent(0));
        assertFalse(listeVol.indicePertinent(1)); // Indice non pertinent
    }
    
    @Test
    public void testExtractionVol() throws InvalidHourException, InvalidMinuteException, InvalidLineFormatException {
        String cheminFichier = "test\\ModelTest\\ficVolTest.csv"; // Chemin du fichier de test
        listeVol.extractionVol(cheminFichier);
        assertEquals(2, listeVol.tailleList());
    }
    


}
