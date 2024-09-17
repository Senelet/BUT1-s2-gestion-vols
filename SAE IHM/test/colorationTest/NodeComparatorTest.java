/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package colorationTest;

import coloration.NodeComparator;
import coloration.NodePlus;
import java.util.PriorityQueue;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
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
public class NodeComparatorTest {
    private SingleGraph graph;
    private NodeComparator comparator;
    private NodePlus node1;
    private NodePlus node2;
    private NodePlus node3;
    
    
    public NodeComparatorTest() {
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
        comparator = new NodeComparator();
        graph = new SingleGraph("TestGraph");
        
        Node n1 = graph.addNode("1");
        Node n2 = graph.addNode("2");
        Node n3 = graph.addNode("3");
        n1.setAttribute("numero", 1);
        n2.setAttribute("numero", 2);
        n3.setAttribute("numero", 3);
        
        node1 = new NodePlus(n1);
        node2 = new NodePlus(n2);
        node3 = new NodePlus(n3);
        node1.setDsat(2);
        node2.setDsat(1);
        node3.setDsat(2);
    }
    
    
    
    @Test
    public void testCompareDifferentDsat() {
        assertTrue(comparator.compare(node1, node2) < 0); // node1 doit être classé avant node2
    }
    
    @Test
    public void testCompareEqualDsat() {
        // on va tester 10 fois le comparateur afin de voir si le cas ou deux DSAT sont identiques le comparateur les classes bien aléatoirement
        boolean resultDifferent = false;
        for (int i = 0; i < 10; i++) {
            if (comparator.compare(node1, node3) != 0) {
                resultDifferent = true;
                break;
            }
        }
        assertTrue(resultDifferent);
    }
    
    @Test
    public void testPriorityQueue() {
        PriorityQueue<NodePlus> priorityQueue = new PriorityQueue<>(comparator);
        priorityQueue.add(node1);
        priorityQueue.add(node2);
        priorityQueue.add(node3);

        NodePlus premier = priorityQueue.poll();
        NodePlus deuxieme = priorityQueue.poll();
        NodePlus troisieme = priorityQueue.poll();
        
        //on test ici afin de voir si la priorityQueue a bien trié comme on le voulait 
        assertTrue(premier == node1 || premier == node3);
        assertTrue(deuxieme == node1 || deuxieme == node3);
        assertTrue(troisieme == node2);
    }
    
}
