package coloration;

import java.util.*;
import model.ListeVol;
import model.Vol;

/**
 * Classe Dsatur pour colorier un graphe en utilisant l'algorithme DSATUR.
 * L'algorithme DSATUR colore les nœuds d'un graphe de manière à minimiser les
 * conflits de couleur entre nœuds adjacents. L'algorithme utilise un concept
 * appelé saturation degré (dsat) pour décider l'ordre dans lequel les nœuds
 * sont colorés.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 *   <li>{@code nbConflit} - Nombre de conflit de la coloration</li>
 *   <li>{@code listNbNode} - Liste des numéros des noeuds coloriés</li>
 *   <li>{@code listColorNode} - Liste des couleurs des noeuds coloriés</li>
 * </ul>
 * @author Mejdi, Amadis et Zakary
 */
public class Dsatur {

    private static int nbConflit = 0;//Nombre de conflit de la coloration
    private static ArrayList<Integer> listNbNode = new ArrayList<>();//Liste des numéros des noeuds coloriés
    private static ArrayList<Integer> listColorNode = new ArrayList<>();//Liste des couleurs des noeuds coloriés

    /**
     * Constructeur par défaut de la classe Dsatur.
     */
    public Dsatur() {

    }

    /**
     * Obtient le nombre de conflits détectés lors de la coloration.
     *
     * @return Le nombre de conflits.
     */
    public static int getNbConflit() {
        return nbConflit;
    }

    /**
     * Colore les nœuds d'un graphe en utilisant l'algorithme DSATUR pour un
     * fichier de type vol-test.
     *
     * @param nodeList La liste des nœuds à colorier.
     * @param kMax Le nombre maximum de couleurs autorisées.
     * @return Le nombre de couleurs utilisées pour colorier le graphe.
     */
    public static int colorierDsaturVolTest(NodeList nodeList, int kMax) {
        // Initialisation des structures de données
        nbConflit = 0;
        PriorityQueue<NodePlus> nodes = new PriorityQueue<>(new NodeComparator());
        ArrayList<Boolean> colorUsed = new ArrayList<>(Collections.nCopies(nodeList.taille(), false));
        ArrayList<Integer> colors = new ArrayList<>(Collections.nCopies(nodeList.taille(), -1));
        ArrayList<Set<Integer>> colorSet = new ArrayList<>(nodeList.taille());
        int[] listColor = new int[kMax];

        // Initialisation des nœuds
        for (int i = 0; i < nodeList.taille(); i++) {
            NodePlus node = nodeList.getNodeAt(i);
            node.setDsat(node.getDegree());
            nodes.add(node);
            colorSet.add(new HashSet<>());
            //System.out.println(node.getNb());
            //System.out.println("dsat : " + node.getDsat());
        }

        listNbNode = new ArrayList<>();
        listColorNode = new ArrayList<>();
        listNbNode.add(nodes.element().getNb());

        NodePlus node = nodes.poll();

        while (node != null) {
            //System.out.println("Coloring node " + node.getNb() + " " + node.getDsat());

            // Récupérer les couleurs utilisées par les nœuds adjacents
            for (NodePlus adj : node.getListAdj()) {
                if (listNbNode.contains(adj.getNb())) {
                    adj.setColor(listColorNode.get(indiceDansListColorNode(listNbNode, adj.getNb())));
                }

                if (adj.getColor() != -1) {
                    colorUsed.set(adj.getColor(), true);
                }
            }

            // Trouver la plus petite couleur qui peut être utilisée
            int minColor;
            for (minColor = 0; minColor < nodeList.taille(); minColor++) {
                if (!colorUsed.get(minColor)) {
                    break;
                }
            }

            // Réinitialiser la liste des couleurs utilisées pour l'itération suivante
            for (NodePlus adj : node.getListAdj()) {
                if (listNbNode.contains(adj.getNb())) {
                    adj.setColor(listColorNode.get(indiceDansListColorNode(listNbNode, adj.getNb())));
                }

                if (adj.getColor() != -1) {
                    colorUsed.set(adj.getNb(), false);
                }
            }

            // Gestion des conflits de couleur
            if (minColor > kMax - 1) {
                listColor = new int[kMax];
                for (NodePlus adj : node.getListAdj()) {
                    if (listNbNode.contains(adj.getNb())) {
                        adj.setColor(listColorNode.get(indiceDansListColorNode(listNbNode, adj.getNb())));
                    }
                    if (adj.getColor() != -1) {
                        listColor[adj.getColor()] = listColor[adj.getColor()] + 1;
                    }
                }
                int minConflit = node.getDegree();
                int repèreConflit = 0;
                for (int j = 0; j < kMax; j++) {
                    if (listColor[j] < minConflit) {
                        minConflit = listColor[j];
                        repèreConflit = j;
                    }
                }
                minColor = repèreConflit;
                nbConflit = nbConflit + minConflit;
            }

            // Assigner la couleur au nœud
            node.setColor(minColor);
            nodeList.getNodeAt(node.getNb()).setColor(minColor);
            colors.set(node.getColor(), minColor);
            listColorNode.add(node.getColor());

            // Mettre à jour les valeurs DSAT
            for (NodePlus adj : node.getListAdj()) {
                if (listNbNode.contains(adj.getNb())) {
                    adj.setColor(listColorNode.get(indiceDansListColorNode(listNbNode, adj.getNb())));
                }
                if (adj.getColor() == -1) {
                    colorSet.get(adj.getNb()).add(minColor);
                    elementCherché(adj, nodes);
                    adj.setDsat(colorSet.get(adj.getNb()).size());
                    nodes.add(adj);
                } else {
                    nodes.remove(adj);
                }
            }

            // Retirer le nœud courant de la file de priorité
            node = nodes.poll();
            if (node == null) {
                break;
            }
            listNbNode.add(node.getNb());
        }

        // Retourner le nombre de couleurs utilisées
        if (colors.isEmpty()) {
            return 0;
        }
        return Collections.max(colors) + 1;
    }

    /**
     * Colore les nœuds d'un graphe en utilisant l'algorithme DSATUR pour un
     * fichier de type graph-test.
     *
     * @param nodeList La liste des nœuds à colorier.
     * @param kMax Le nombre maximum de couleurs autorisées.
     * @return Le nombre de couleurs utilisées pour colorier le graphe.
     */
    public static int colorierDsaturGraphTest(NodeList nodeList, int kMax) {
        // Initialisation des structures de données
        PriorityQueue<NodePlus> nodes = new PriorityQueue<>(new NodeComparator());
        ArrayList<Boolean> colorUsed = new ArrayList<>(Collections.nCopies(nodeList.taille(), false));
        ArrayList<Integer> colors = new ArrayList<>(Collections.nCopies(nodeList.taille(), -1));
        ArrayList<Set<Integer>> colorSet = new ArrayList<>(nodeList.taille());
        int[] listColor = new int[kMax];
        nbConflit = 0;

        // Initialisation des nœuds
        for (int i = 0; i < nodeList.taille(); i++) {
            NodePlus node = nodeList.getNodeAt(i);
            node.setDsat(node.getDegree());
            nodes.add(node);
            colorSet.add(new HashSet<>());
        }

        listNbNode = new ArrayList<>();
        listColorNode = new ArrayList<>();
        listNbNode.add(nodes.element().getNb() - 1);

        NodePlus node = nodes.poll();

        while (node != null) {
            //System.out.println("Coloring node " + node.getNb() + " " + node);

            // Récupérer les couleurs utilisées par les nœuds adjacents
            for (NodePlus adj : node.getListAdj()) {
                if (listNbNode.contains(adj.getNb() - 1)) {
                    adj.setColor(listColorNode.get(indiceDansListColorNode(listNbNode, adj.getNb() - 1)));
                }

                if (adj.getColor() != -1) {
                    colorUsed.set(adj.getColor(), true);
                }
            }

            // Trouver la plus petite couleur qui peut être utilisée
            int minColor;
            for (minColor = 0; minColor < nodeList.taille(); minColor++) {
                if (!colorUsed.get(minColor)) {
                    break;
                }
            }

            // Réinitialiser la liste des couleurs utilisées pour l'itération suivante
            for (NodePlus adj : node.getListAdj()) {
                if (listNbNode.contains(adj.getNb() - 1)) {
                    adj.setColor(listColorNode.get(indiceDansListColorNode(listNbNode, adj.getNb() - 1)));
                }

                if (adj.getColor() != -1) {
                    colorUsed.set(adj.getColor(), false);
                }
            }

            // Gestion des conflits de couleur
            if (minColor > kMax - 1) {
                listColor = new int[kMax];
                for (NodePlus adj : node.getListAdj()) {
                    if (listNbNode.contains(adj.getNb() - 1)) {
                        adj.setColor(listColorNode.get(indiceDansListColorNode(listNbNode, adj.getNb() - 1)));
                    }
                    if (adj.getColor() != -1) {
                        listColor[adj.getColor()] = listColor[adj.getColor()] + 1;
                    }
                }
                int minConflit = node.getDegree();
                int repèreConflit = 0;
                for (int j = 0; j < kMax; j++) {
                    if (listColor[j] < minConflit) {
                        minConflit = listColor[j];
                        repèreConflit = j;
                    }
                }
                minColor = repèreConflit;
                nbConflit = nbConflit + minConflit;
            }

            // Assigner la couleur au nœud
            node.setColor(minColor);
            nodeList.getNodeAt(node.getNb() - 1).setColor(minColor);
            colors.set(node.getNb() - 1, minColor);
            listColorNode.add(node.getColor());

            // Mettre à jour les valeurs DSAT
            updateDsatValues(node, listColorNode, listNbNode, nodes, minColor, colorSet);

            // Retirer le nœud courant de la file de priorité
            node = nodes.poll();
            if (node == null) {
                break;
            }
            listNbNode.add(node.getNb() - 1);
        }

        // Retourner le nombre de couleurs utilisées
        if (colors.isEmpty()) {
            return 0;
        }
        return Collections.max(colors) + 1;
    }

    /**
     * Applique une couleur à un graphe en fonction du nombre de couleurs
     * utilisées.
     *
     * @param colorUse Le nombre de couleurs utilisées.
     * @param nodeList La liste des nœuds du graphe.
     */
    public static void appliqueColor(int colorUse, NodeList nodeList) {
        ColorList colorList = new ColorList(colorUse);

        ColorPlus[] colors = colorList.generateRandomColorList();
        for (int i = 0; i < nodeList.taille(); i++) {
            NodePlus nodeInter = nodeList.getNodeAt(i);
            int indiceColor = nodeInter.getColor();
            if (indiceColor != -1) {
                ColorPlus colorNode = colors[indiceColor];
                nodeInter.setColorGraph(colorNode);
            }
        }
    }

    /**
     * Cherche et supprime un élément spécifique d'une file de priorité.
     *
     * @param elt L'élément à chercher.
     * @param nodes La file de priorité contenant les nœuds.
     */
    private static void elementCherché(NodePlus elt, PriorityQueue<NodePlus> nodes) {
        PriorityQueue<NodePlus> nodes1 = new PriorityQueue<>(new NodeComparator());
        while (!nodes.isEmpty()) {
            NodePlus nodeCourant = nodes.poll();
            if (nodeCourant.getNb() == elt.getNb()) {
                empiler(nodes1, nodes);
                break;
            } else {
                nodes1.add(nodeCourant);
            }
        }
    }

    /**
     * Empile les éléments d'une file de priorité dans une autre.
     *
     * @param nodes1 La file de priorité source.
     * @param nodes La file de priorité destination.
     */
    private static void empiler(PriorityQueue<NodePlus> nodes1, PriorityQueue<NodePlus> nodes) {
        for (NodePlus node : nodes1) {
            nodes.add(node);
        }
    }

    /**
     * Trouve l'indice d'un nœud dans une liste de nœuds par son numéro.
     *
     * @param listNbNode La liste des numéros de nœuds.
     * @param nb Le numéro du nœud à chercher.
     * @return L'indice du nœud dans la liste.
     */
    public static int indiceDansListColorNode(ArrayList<Integer> listNbNode, int nb) {
        int res = -1;
        for (int i = 0; i < listNbNode.size(); i++) {
            if (listNbNode.get(i) == nb) {
                res = i;
            }
        }
        return res;
    }

    /**
     * Met à jour les valeurs DSAT des nœuds adjacents après la coloration d'un
     * nœud.
     *
     * @param node Le nœud coloré.
     * @param listColorNode La liste des couleurs des nœuds.
     * @param listNbNode La liste des numéros de nœuds.
     * @param nodes La file de priorité contenant les nœuds.
     * @param minColor La couleur assignée au nœud.
     * @param colorSet Les ensembles de couleurs utilisées par chaque nœud.
     */
    private static void updateDsatValues(NodePlus node, ArrayList<Integer> listColorNode, ArrayList<Integer> listNbNode, PriorityQueue<NodePlus> nodes, int minColor, ArrayList<Set<Integer>> colorSet) {
        for (NodePlus adj : node.getListAdj()) {
            if (listNbNode.contains(adj.getNb() - 1)) {
                adj.setColor(listColorNode.get(indiceDansListColorNode(listNbNode, adj.getNb() - 1)));
            }
            if (adj.getColor() == -1) {
                colorSet.get(adj.getNb() - 1).add(minColor);
                elementCherché(adj, nodes);
                adj.setDsat(colorSet.get(adj.getNb() - 1).size());
                nodes.add(adj);
            } else {
                nodes.remove(adj);
            }
        }
    }

    /**
     * Obtient la liste des couleurs des nœuds.
     *
     * @return une {@code ArrayList<Integer>} contenant les couleurs des nœuds
     */
    public static ArrayList<Integer> getListColorNode() {
        return listColorNode;
    }

    /**
     * Obtient la liste des nombres de nœuds.
     *
     * @return une {@code ArrayList<Integer>} contenant les nombres de nœuds
     */
    public static ArrayList<Integer> getListNbNode() {
        return listNbNode;
    }

    /**
     * Applique l'altitude aux vols en fonction des couleurs des nœuds.
     *
     * @param nodeList la liste des nœuds
     * @param listVol la liste des vols
     */
    public static void appliqueAltitudetoVol(NodeList nodeList, ListeVol listVol) {
        for (int i = 0; i < nodeList.taille(); i++) {
            NodePlus node;
            node = nodeList.getNodeAt(i);
            for (Vol vol : listVol.getListeVol()) {
                if (node.getNumero().equals(vol.getNumero())) {
                    int altitude = 500 + node.getColor() * 250;
                    vol.setNiveau(altitude);
                }
            }
        }
    }
}
