/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package CollisionsTest;

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
public class PointTest {
    private Point point;
    
    public PointTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        point = new Point();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testConstructeurParDefaut() {
        assertTrue(0  ==  point.getX());
        assertTrue(0 ==point.getY());
    }

    @Test
    public void testConstructeurAvecParametres() {
        Point pointParam = new Point(3, 4);
        assertEquals(3, pointParam.getX(), 0.0001);
        assertEquals(4, pointParam.getY(), 0.0001);
    }

    @Test
    public void testGetX() {
        point = new Point(5, 6);
        assertEquals(5, point.getX(), 0.0001);
    }

    @Test
    public void testGetY() {
        point = new Point(5, 6);
        assertEquals(6, point.getY(), 0.0001);
    }

    @Test
    public void testSetX() {
        point.setX(7);
        assertEquals(7, point.getX(), 0.0001);
    }

    @Test
    public void testSetY() {
        point.setY(8);
        assertEquals(8, point.getY(), 0.0001);
    }

    @Test
    public void testEquals() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(1, 2);
        Point point3 = new Point(2, 3);
        assertTrue(point1.equals(point2));
        assertFalse(point1.equals(point3));
    }

    @Test
    public void testCalculerDistance() {
        Point point1 = new Point(0, 0);
        Point point2 = new Point(3, 4);
        assertEquals(5, Point.calculerDistance(point1, point2), 0.0001);
    }
}
