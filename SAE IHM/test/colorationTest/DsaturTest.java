package colorationTest;

import coloration.Dsatur;
import static coloration.Dsatur.indiceDansListColorNode;
import coloration.NodeList;
import coloration.NodePlus;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


public class DsaturTest {

    private Graph graph;
    private NodeList nodeList;

    @Before
    public void setUp() {
        
    }

    @Test
    public void testColorierDsaturVolTest() {
        graph = new SingleGraph("TestGraph");
        graph.addNode("A").setAttribute("numero", 0);
        graph.addNode("B").setAttribute("numero", 1);
        graph.addNode("C").setAttribute("numero", 2);
        graph.addNode("D").setAttribute("numero", 3);
        
        // Ajout des arêtes
        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("CD", "C", "D");
        graph.addEdge("AD", "A", "D");

        // Initialisation de NodeList à partir du graphe
        nodeList = new NodeList(graph);
        
        int kMax = 3;  // Le nombre maximum de couleurs autorisées
        int colorsUsed = Dsatur.colorierDsaturVolTest(nodeList, kMax);

        // Vérification des assertions
        assertTrue(colorsUsed > 0);
        assertTrue(colorsUsed <= kMax);
        
        // Vérifier que les nœuds adjacents ont des couleurs différentes
        for (NodePlus node : nodeList.getListNode()) {
            int color = node.getColor();
            for (NodePlus adj : node.getListAdj()) {
                if (Dsatur.getListNbNode().contains(adj.getNb())){
                    adj.setColor(Dsatur.getListColorNode().get(indiceDansListColorNode(Dsatur.getListNbNode(),adj.getNb())));
                }
                assertNotEquals(color, adj.getColor());
            }
        }
    }

    @Test
    public void testColorierDsaturGraphTest() {
        graph = new SingleGraph("TestGraph");
        graph.addNode("A").setAttribute("numero", 1);
        graph.addNode("B").setAttribute("numero", 2);
        graph.addNode("C").setAttribute("numero", 3);
        graph.addNode("D").setAttribute("numero", 4);
        
        // Ajout des arêtes
        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("CD", "C", "D");
        graph.addEdge("AD", "A", "D");

        // Initialisation de NodeList à partir du graphe
        nodeList = new NodeList(graph);
        int kMax = 3;  // Le nombre maximum de couleurs autorisées
        int colorsUsed = Dsatur.colorierDsaturGraphTest(nodeList, kMax);

        // Vérification des assertions
        assertTrue(colorsUsed > 0);
        assertTrue(colorsUsed <= kMax);
        
        // Vérifier que les nœuds adjacents ont des couleurs différentes
        for (NodePlus node : nodeList.getListNode()) {
            int color = node.getColor();
            for (NodePlus adj : node.getListAdj()) {
                if (Dsatur.getListNbNode().contains(adj.getNb()-1)){
                        adj.setColor(Dsatur.getListColorNode().get(indiceDansListColorNode(Dsatur.getListNbNode(),adj.getNb()-1)));
                    }
                assertNotEquals(color, adj.getColor());
            }
        }
    }

    @Test
    public void testAppliqueColor() {
        graph = new SingleGraph("TestGraph");
        graph.addNode("A").setAttribute("numero", 1);
        graph.addNode("B").setAttribute("numero", 2);
        graph.addNode("C").setAttribute("numero", 3);
        graph.addNode("D").setAttribute("numero", 4);
        
        // Ajout des arêtes
        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("CD", "C", "D");
        graph.addEdge("AD", "A", "D");

        // Initialisation de NodeList à partir du graphe
        nodeList = new NodeList(graph);
        
        int kMax = 3;  // Le nombre maximum de couleurs autorisées
        int colorsUsed = Dsatur.colorierDsaturGraphTest(nodeList, kMax);
        Dsatur.appliqueColor(colorsUsed, nodeList);

        // Vérification des couleurs appliquées
        for (NodePlus node : nodeList.getListNode()) {
            assertNotNull(node.getColorGraph());
        }
    }

    @Test
    public void testGetNbConflit() {
        graph = new SingleGraph("TestGraph");
        graph.addNode("A").setAttribute("numero", 1);
        graph.addNode("B").setAttribute("numero", 2);
        graph.addNode("C").setAttribute("numero", 3);
        graph.addNode("D").setAttribute("numero", 4);
        
        // Ajout des arêtes
        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("CD", "C", "D");
        graph.addEdge("AD", "A", "D");

        // Initialisation de NodeList à partir du graphe
        nodeList = new NodeList(graph);
        int kMax = 2;  // Le nombre maximum de couleurs autorisées
        Dsatur.colorierDsaturGraphTest(nodeList, kMax);
        int nbConflits = Dsatur.getNbConflit();

        // Vérification qu'il n'y a pas de conflits après la coloration
        assertEquals(1, nbConflits);
    }
}