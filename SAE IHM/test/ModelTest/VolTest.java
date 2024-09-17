package ModelTest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

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
public class VolTest {
    Vol volTest;
    
    public VolTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        volTest = new Vol("AF123", "CDG", "JFK", 14, 30, 480);
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testConstructorAndGetters() {
        // Vérification des valeurs initiales
        assertEquals("AF123", volTest.getNumero());
        assertEquals("CDG", volTest.getDepart());
        assertEquals("JFK", volTest.getArrivee());
        assertEquals(14, volTest.getHeureDepartHeure());
        assertEquals(30, volTest.getHeureDepartMinute());
        assertEquals(480, volTest.getDuree());
        assertEquals(-1, volTest.getNiveau());
    }
    
    @Test
    public void testHeureDepartToMinute() {
        assertTrue(870 == volTest.heureDeparttoMinute());
    }
    
     @Test
    public void testSetNiveau() {
        // Modification du niveau
        volTest.setNiveau(2);
        assertEquals(2, volTest.getNiveau());
        
    }
}
