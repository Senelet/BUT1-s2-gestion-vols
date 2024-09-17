package ModelTest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import model.Aeroport;
import model.ListeAeroport;
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
public class ListeAeroportTest {
    private ListeAeroport listeAeroport;
    private Aeroport aeroportTest1;
    private Aeroport aeroportTest2;
    
    public ListeAeroportTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        listeAeroport = new ListeAeroport();
        aeroportTest1 = new Aeroport("CDG", "Charles de Gaulle", 49.0097, 2.5479, 651.2, 1874.5);
        aeroportTest2 = new Aeroport("JFK", "John F. Kennedy", 40.6413, -73.7781, 1280.1, 3450.3);
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testAjAeroport() {
        assertTrue(listeAeroport.ajAeroport(aeroportTest1));
        assertFalse(listeAeroport.ajAeroport(aeroportTest1)); // Ne doit pas ajouter deux fois le même aéroport
        assertEquals(1, listeAeroport.tailleList());
    }
    @Test
    public void testSupAeroport() {
        listeAeroport.ajAeroport(aeroportTest1);
        assertTrue(listeAeroport.supAeroport(aeroportTest1));
        assertFalse(listeAeroport.supAeroport(aeroportTest1)); // Ne doit pas supprimer si l'aéroport n'est pas présent
        assertEquals(0, listeAeroport.tailleList());
    }
    
    @Test
    public void testAccesAeroportInd() {
        listeAeroport.ajAeroport(aeroportTest1);
        assertEquals(aeroportTest1, listeAeroport.accesAeroportInd(0));
        assertNull(listeAeroport.accesAeroportInd(1)); // Indice non pertinent
    }
    
    @Test
    public void testAccesAeroportCode() {
        listeAeroport.ajAeroport(aeroportTest1);
        listeAeroport.ajAeroport(aeroportTest2);
        assertEquals(aeroportTest1, listeAeroport.accesAeroportCode("CDG"));
        assertEquals(aeroportTest2, listeAeroport.accesAeroportCode("JFK"));
        assertNull(listeAeroport.accesAeroportCode("LAX")); // Code non présent
    }
    
    @Test
    public void testIndicePertinent() {
        listeAeroport.ajAeroport(aeroportTest1);
        assertTrue(listeAeroport.indicePertinent(0));
        assertFalse(listeAeroport.indicePertinent(1)); // Indice non pertinent
    }
    
    @Test
    public void testConvertToDecimalDegrees() {
        double latitude = ListeAeroport.convertToDecimalDegrees("49", "0", "35.7", "N");
        double longitude = ListeAeroport.convertToDecimalDegrees("2", "32", "52.4", "E");
        assertEquals(49.009917, latitude, 0.000001);
        assertEquals(2.547889, longitude, 0.000001);
    }
    
    @Test
    public void testConvertX() {
        double coordX = listeAeroport.convertX(0.0, 90.0); 
        assertEquals(6371.0, coordX, 0.0001);
    }

    @Test
    public void testConvertY() {
        double coordY = listeAeroport.convertY(0.0, 0.0); 
        assertEquals(6371.0, coordY, 0.0001);
    }
}
