/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package colorationTest;

import coloration.ColorPlus;
import coloration.NodePlus;
import java.util.Set;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author Amadis
 */
public class NodePlusTest {
    private SingleGraph graph;
    private NodePlus node1;
    private NodePlus node2;
    private NodePlus node3;
    
    public NodePlusTest() {
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
        graph = new SingleGraph("TestGraph");

        Node n1 = graph.addNode("1");
        Node n2 = graph.addNode("2");
        Node n3 = graph.addNode("3");

        n1.setAttribute("numero", 1);
        n2.setAttribute("numero", 2);
        n3.setAttribute("numero", 3);

        graph.addEdge("1-2", "1", "2");
        graph.addEdge("2-3", "2", "3");

        node1 = new NodePlus(n1);
        node2 = new NodePlus(n2);
        node3 = new NodePlus(n3);
    }
    
   
    
    @Test
    public void testGetListAdj() {
        NodePlus[] adjacents = node2.getListAdj();
        assertEquals(2, adjacents.length);
        assertTrue((adjacents[0].getId().equals("1") && adjacents[1].getId().equals("3")) || 
                (adjacents[0].getId().equals("3") && adjacents[1].getId().equals("1")));
    }
    
    
    @Test
    public void testSetColorGraph() {
        ColorPlus color = new ColorPlus(255, 0, 0);
        node1.setColorGraph(color);
        assertEquals(color, node1.getColorGraph());
    }

    
}
