/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewTest;

import Exceptions.InvalidDataFormatException;
import Exceptions.NegativeKMaxException;
import Exceptions.NodeOutOfBoundsException;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import view.ChargerGraphe;

/**
 *
 * @author Mèjdi
 */
public class ChargerGrapheTest {

    private ChargerGraphe graph;

    @Before
    public void setUp() {
        graph = new ChargerGraphe("testGraph", 5, 10);
    }

    @Test
    public void testConstructorWithParameters() {
        assertEquals("testGraph", graph.getId());
        assertEquals(5, graph.getkMax());
        assertEquals(10, graph.getNbSommet());
    }

    @Test
    public void testDefaultConstructor() {
        ChargerGraphe defaultGraph = new ChargerGraphe();
        assertEquals("Graphe vide",defaultGraph.getId());
        assertEquals(0, defaultGraph.getkMax());
        assertEquals(0, defaultGraph.getNbSommet());
    }

    

    @Test
    public void testSetAndGetkMax() {
        graph.setkMax(10);
        assertEquals(10, graph.getkMax());
    }

    @Test
    public void testSetAndGetNbSommet() {
        graph.setNbSommet(20);
        assertEquals(20, graph.getNbSommet());
    }

    @Test
    public void testCreerNbSommet() {
        graph.creerNbSommet(5);
        assertEquals(5, graph.getNodeCount());
        for (int i = 1; i <= 5; i++) {
            assertNotNull(graph.getNode(String.valueOf(i)));
        }
    }

    @Test
    public void testChargerGraph() throws IOException, NegativeKMaxException, InvalidDataFormatException, NodeOutOfBoundsException{ 
        // Créez un fichier CSV temporaire pour tester le chargement du graphe
        String csvContent = "3\n4\n1 2\n2 3\n3 4\n";
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("testGraph", ".csv");
        java.nio.file.Files.write(tempFile, csvContent.getBytes());

        int lignesIgnorees = graph.chargerGraph(tempFile.toString());

        // Vérifiez que les valeurs sont correctement chargées
        assertEquals(3, graph.getkMax());
        assertEquals(4, graph.getNbSommet());
        assertEquals(4, graph.getNodeCount());
        assertEquals(3, graph.getEdgeCount());

        // Vérifiez que les sommets et les arêtes sont correctement ajoutés
        for (int i = 1; i <= 4; i++) {
            assertNotNull(graph.getNode(String.valueOf(i)));
        }
        assertNotNull(graph.getEdge("1_2"));
        assertNotNull(graph.getEdge("2_3"));
        assertNotNull(graph.getEdge("3_4"));

        // Vérifiez qu'aucune ligne n'a été ignorée
        assertEquals(0, lignesIgnorees);

        // Supprimez le fichier temporaire après le test
        java.nio.file.Files.delete(tempFile);
    }

    @Test
    public void testGetDegreMoyen() {
        graph.creerNbSommet(3);
        graph.addEdge("1_2", "1", "2");
        graph.addEdge("2_3", "2", "3");
        double degreMoyen = graph.getDegreMoyen();
        assertEquals(1.333, degreMoyen, 0.001);
    }

    @Test
    public void testGetNombreComposantesConnexes() {
        graph.creerNbSommet(3);
        graph.addEdge("1_2", "1", "2");
        int composantesConnexes = graph.getNombreComposantesConnexes();
        assertEquals(2, composantesConnexes);
    }

    @Test
    public void testGetNombreNoeuds() {
        graph.creerNbSommet(5);
        assertEquals(5, graph.getNombreNoeuds());
    }

    @Test
    public void testGetNombreAretes() {
        graph.creerNbSommet(3);
        graph.addEdge("1_2", "1", "2");
        graph.addEdge("2_3", "2", "3");
        assertEquals(2, graph.getNombreAretes());
    }

    @Test
    public void testGetDiametre() {
        graph.creerNbSommet(3);
        graph.addEdge("1_2", "1", "2");
        graph.addEdge("2_3", "2", "3");
        double diametre = graph.getDiametre();
        assertEquals(2.0, diametre, 0.001);
    }
}
