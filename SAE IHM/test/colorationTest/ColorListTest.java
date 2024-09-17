/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package colorationTest;

import coloration.ColorList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author Amadis
 */
public class ColorListTest {
    private ColorList colorList1;
    
    public ColorListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Before
    public void setUp() {
        colorList1 = new ColorList(2);
    }
    
    @Test
    public void testConstructeur(){
        assertTrue(colorList1.getNbColors() == 2);
        assertTrue(colorList1.getColorList().length == 2);
    }
    
    @Test
    public void testGenerateRandomColorList(){
        colorList1.generateRandomColorList();
        assertTrue(colorList1.getColorList()[0].getBlue() >= 0 && colorList1.getColorList()[0].getBlue() < 256);
        assertTrue(colorList1.getColorList()[0].getGreen() >= 0 && colorList1.getColorList()[0].getGreen() < 256);
        assertTrue(colorList1.getColorList()[0].getRed() >= 0 && colorList1.getColorList()[0].getRed() < 256);
    }

    
}
