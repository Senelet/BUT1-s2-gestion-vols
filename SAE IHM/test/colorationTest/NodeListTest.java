/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package colorationTest;

import coloration.NodeList;
import coloration.NodePlus;
import java.util.Map;
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
public class NodeListTest {
    private SingleGraph graph;
    private NodeList nodeList;
    
    public NodeListTest() {
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

        nodeList = new NodeList(graph);
    }
    
    
    
    @Test
    public void testTaille() {
        assertEquals(3, nodeList.taille());
    }
    
    @Test
    public void testGetNodeAt() {
        NodePlus node = nodeList.getNodeAt(0);
        assertEquals("1", node.getId());
    }
    
    @Test
    public void testGetNodeSaturationMap() {
        Map<NodePlus, Integer> saturationMap = nodeList.getNodeSaturationMap();
        assertEquals(3, saturationMap.size());
        assertTrue(saturationMap.containsKey(nodeList.getNodeAt(0)));
    }
    
    @Test
    public void testCreateDegreeList() {
        int[] degreeList = nodeList.getListDegree();
        assertEquals(1, degreeList[0]);
        assertEquals(2, degreeList[1]);
        assertEquals(1, degreeList[2]);
    }
    
    

}
