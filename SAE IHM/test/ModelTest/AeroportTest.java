package ModelTest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import model.Aeroport;
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
public class AeroportTest {
    Aeroport aeroportTest;
    
    public AeroportTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        aeroportTest = new Aeroport("CDG", "Charles de Gaulle", 49.0097, 2.5479, 1000, 2000);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testConstructorAndGetters(){
        assertEquals("CDG", aeroportTest.getCode());
        assertEquals("Charles de Gaulle", aeroportTest.getNom());
        assertTrue(49.0097 == aeroportTest.getLatitude());
        assertTrue(2.5479 == aeroportTest.getLongitude());
        assertTrue(1000 == aeroportTest.getCordX());
        assertTrue(2000 == aeroportTest.getCordY());
    }
    
    @Test
    public void testSet(){
        aeroportTest.setCordX(1200);
        assertTrue(1200 == aeroportTest.getCordX());
        aeroportTest.setCordY(2100);
        assertTrue(2100 == aeroportTest.getCordY());
        
    }
}
