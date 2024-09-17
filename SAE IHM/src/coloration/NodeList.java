package coloration;

import org.graphstream.graph.Graph;


import java.util.HashMap;
import java.util.Map;

/**
 * Classe NodeList pour gérer une liste de nœuds dans un graphe et leurs degrés de saturation.
 * Cette classe permet de charger les nœuds à partir d'un graphe GraphStream et de calculer leurs degrés.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 *   <li>{@code listNode} - Liste des NodePlus</li>
 *   <li>{@code degreeList} - Liste des degrés des noeuds</li>
 * </ul>
 */
public class NodeList {
    
    private NodePlus[] listNode; //Liste des NodePlus
    private int[] degreeList;// Liste des degrés des noeuds

    /**
     * Constructeur de la classe NodeList. Initialise la liste des nœuds et leurs degrés à partir d'un graphe donné.
     * 
     * @param graph Le graphe GraphStream à partir duquel les nœuds seront chargés.
     */
    public NodeList(Graph graph) {
        chargerListNode(graph);
        degreeList = createDegreeList();
    }

    /**
     * Charge les nœuds du graphe dans la liste des nœuds.
     * 
     * @param graph Le graphe GraphStream à partir duquel les nœuds seront chargés.
     */
    private void chargerListNode(Graph graph) {
        int taille = graph.getNodeCount();
        listNode = new NodePlus[taille];
        for (int i = 0; i < taille; i++) {
            listNode[i] = new NodePlus(graph.getNode(i));
        }
    }

    /**
     * Retourne le nombre de nœuds dans la liste.
     * 
     * @return Le nombre de nœuds dans la liste.
     */
    public int taille() {
        return listNode.length;
    }

    /**
     * Retourne le nœud à l'index spécifié.
     * 
     * @param ind L'index du nœud à retourner.
     * @return Le nœud à l'index spécifié.
     */
    public NodePlus getNodeAt(int ind) {
        return listNode[ind];
    }

    /**
     * Retourne une map des nœuds et de leurs degrés de saturation.
     * 
     * @return Une map des nœuds et de leurs degrés de saturation.
     */
    public Map<NodePlus, Integer> getNodeSaturationMap() {
        Map<NodePlus, Integer> nodeSaturationMap = new HashMap<>();
        for (NodePlus node : listNode) {
            nodeSaturationMap.put(node, node.getDsat());
        }
        return nodeSaturationMap;
    }
    
    /**
     * Retourne la liste des nœuds.
     * 
     * @return Un tableau de nœuds NodePlus.
     */
    public NodePlus[] getListNode() {
        return listNode;
    }
    
    /**
     * Crée une liste des degrés des nœuds.
     * 
     * @return Un tableau contenant les degrés des nœuds.
     */
    private int[] createDegreeList() {
        int size = listNode.length;
        degreeList = new int[size];
        for (int i = 0; i < size; i++) {
            degreeList[i] = listNode[i].getDegree();
        }
        return degreeList;
    }
    
    /**
     * Retourne la liste des degrés des nœuds.
     * 
     * @return Un tableau contenant les degrés des nœuds.
     */
    public int[] getListDegree() {
        return degreeList;
    }
}

