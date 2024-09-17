/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package CollisionsTest;

import collisions.Droite;
import collisions.Point;
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
public class DroiteTest {
    private Droite droite;
    private Point pointA;
    private Point pointB;
    
    public DroiteTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        droite = new Droite();
        pointA = new Point(0, 0);
        pointB = new Point(2, 2);
    }
    
    @After
    public void tearDown() {
    }

     @Test
    public void testConstructeurParDefaut() {
        assertEquals(0, droite.getPente(), 0.0001);
        assertEquals(0, droite.getOrdOrigine(), 0.0001);
        assertFalse(droite.isDroiteVerticale());
        assertFalse(droite.isDroiteHorizontale());
    }

    @Test
    public void testConstructeurAvecParametres() {
        Droite droiteParam = new Droite(1, 0);
        assertEquals(1, droiteParam.getPente(), 0.0001);
        assertEquals(0, droiteParam.getOrdOrigine(), 0.0001);
        assertFalse(droiteParam.isDroiteVerticale());
        assertFalse(droiteParam.isDroiteHorizontale());
    }

    @Test
    public void testCalcDroiteNonVerticaleNonHorizontale() {
        droite.calcDroite(pointA, pointB);
        assertEquals(1, droite.getPente(), 0.0001);
        assertEquals(0, droite.getOrdOrigine(), 0.0001);
        assertFalse(droite.isDroiteVerticale());
        assertFalse(droite.isDroiteHorizontale());
    }

    @Test
    public void testCalcDroiteVerticale() {
        Point pointC = new Point(2, 0);
        droite.calcDroite(pointB, pointC);
        assertTrue(droite.isDroiteVerticale());
        assertEquals(1, droite.getPente(), 0.0001);
        assertEquals(2, droite.getOrdOrigine(), 0.0001);
    }

    @Test
    public void testCalcDroiteHorizontale() {
        Point pointD = new Point(0, 2);
        droite.calcDroite(pointB, pointD);
        assertTrue(droite.isDroiteHorizontale());
        assertEquals(0, droite.getPente(), 0.0001);
        assertEquals(2, droite.getOrdOrigine(), 0.0001); // voir avec Amadis
    }

    @Test
    public void testIntersectionNonVerticaleNonHorizontale() {
        Droite droite1 = new Droite(1, 0);
        Droite droite2 = new Droite(-1, 2);
        Point intersection = droite1.intersection(droite2);
        assertNotNull(intersection);
        assertEquals(1, intersection.getX(), 0.0001);
        assertEquals(1, intersection.getY(), 0.0001);
    }

    @Test
    public void testIntersectionVerticale() {
        Droite droiteVerticale = new Droite();
        droiteVerticale.setDroiteVerticale(true);
        droiteVerticale.setOrdOrigine(2);
        Droite droite2 = new Droite(1, 0);
        Point intersection = droiteVerticale.intersection(droite2);
        assertNotNull(intersection);
        assertEquals(2, intersection.getX(), 0.0001);
        assertEquals(2, intersection.getY(), 0.0001);
    }

    @Test
    public void testIntersectionHorizontale() {
        Droite droiteHorizontale = new Droite();
        droiteHorizontale.setDroiteHorizontale(true);
        droiteHorizontale.setOrdOrigine(2);
        Droite droite2 = new Droite(1, 0);
        Point intersection = droiteHorizontale.intersection(droite2);
        assertNotNull(intersection);
        assertEquals(2, intersection.getY(), 0.0001);
    }

    @Test
    public void testIntersectionParalleles() {
        Droite droite1 = new Droite(1, 0);
        Droite droite2 = new Droite(1, 1);
        Point intersection = droite1.intersection(droite2);
        assertNull(intersection);
    }

    @Test
    public void testIntersectionConfondus() {
        Droite droite1 = new Droite(1, 0);
        Droite droite2 = new Droite(1, 0);
        Point intersection = droite1.intersection(droite2);
        assertNotNull(intersection);
        assertEquals(0, intersection.getX(), 0.0001);
        assertEquals(0, intersection.getY(), 0.0001);
    }
}
