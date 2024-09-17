package coloration;

import static coloration.Dsatur.indiceDansListColorNode;
import java.util.ArrayList;
import java.util.Arrays;
import org.graphstream.graph.Graph;


/**
 * Classe WelshPowell pour colorier un graphe en utilisant l'algorithme de
 * Welsh-Powell. L'algorithme de Welsh-Powell colore les nœuds d'un graphe de
 * manière à minimiser les conflits de couleur entre nœuds adjacents. Les nœuds
 * sont triés par ordre décroissant de degré avant d'être coloriés.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 *   <li>{@code nodeList} - Liste des noeuds</li>
 *   <li>{@code sortedIndexes} - Tableau d'entier qui comprend les indices des noeuds ordonnés par degrés</li>
 *   <li>{@code nbNodes} - Nombre de noeuds totales</li>
 *   <li>{@code nbConflit} - Nombre de conflit de la coloration</li>
 * </ul>
 */
public class WelshPowell {

    final NodeList nodeList;//Liste des noeuds
    int[] sortedIndexes;//Tableau d'entier qui comprend les indices des noeuds ordonnés par degrés
    final int nbNodes;//Nombre de noeuds totales
    private static int nbConflit = 0;//Nombre de conflit de la coloration

    /**
     * Constructeur de la classe WelshPowell.
     *
     * @param graph Le graphe à colorier.
     */
    public WelshPowell(Graph graph) {
        nodeList = new NodeList(graph);
        sortedIndexes = createSortedIndexes();
        nbNodes = nodeList.taille();
    }

    /**
     * Crée une liste triée des indices des nœuds par ordre décroissant de leur
     * degré.
     *
     * @return Un tableau d'indices triés.
     */
    private int[] createSortedIndexes() {
        int nbNodes = nodeList.taille();
        sortedIndexes = new int[nbNodes];
        for (int ii = 0; ii < nbNodes; ii++) {
            sortedIndexes[ii] = ii;
        }
        sortedIndexes = mergeSort(sortedIndexes);
        return sortedIndexes;
    }

    /**
     * Trie les indices des nœuds par ordre décroissant de leur degré en
     * utilisant le tri fusion.
     *
     * @param sortedIndexes Le tableau d'indices à trier.
     * @return Le tableau trié d'indices.
     */
    public int[] mergeSort(int[] sortedIndexes) {
        int nbNodesIndexes = sortedIndexes.length;
        if (nbNodesIndexes > 1) {
            int mid = nbNodesIndexes / 2;
            int[] sortedIndexesL = new int[mid];
            int[] sortedIndexesR = new int[nbNodesIndexes - mid];

            System.arraycopy(sortedIndexes, 0, sortedIndexesL, 0, mid);
            System.arraycopy(sortedIndexes, mid, sortedIndexesR, 0, nbNodesIndexes - mid);

            mergeSort(sortedIndexesL);
            mergeSort(sortedIndexesR);
            merge(sortedIndexes, sortedIndexesL, sortedIndexesR);
        }
        return sortedIndexes;
    }

    /**
     * Fusionne deux tableaux d'indices triés en un seul tableau trié.
     *
     * @param indexes Le tableau d'indices à remplir.
     * @param indexesL Le tableau gauche d'indices triés.
     * @param indexesR Le tableau droit d'indices triés.
     */
    private void merge(int[] indexes, int[] indexesL, int[] indexesR) {
        int nbNodesL = indexesL.length, nbNodesR = indexesR.length;
        int indL = 0, indR = 0, ind = 0;

        while (indL < nbNodesL && indR < nbNodesR) {
            if (nodeList.getListDegree()[indexesL[indL]] >= nodeList.getListDegree()[indexesR[indR]]) {
                indexes[ind++] = indexesL[indL++];
            } else {
                indexes[ind++] = indexesR[indR++];
            }
        }

        while (indL < nbNodesL) {
            indexes[ind++] = indexesL[indL++];
        }

        while (indR < nbNodesR) {
            indexes[ind++] = indexesR[indR++];
        }
    }

    /**
     * Applique l'algorithme de Welsh-Powell pour colorier les nœuds du graphe
     * de type graph-test.
     *
     * @param kMax Le nombre maximum de couleurs autorisées.
     * @return Un tableau d'indices de couleurs pour chaque nœud.
     */
    public int[] welshPowellAlgo(int kMax) {
        int colorInd;
        nbConflit = 0;
        ArrayList<Integer> listNbNode = new ArrayList<>();
        ArrayList<Integer> listColorNode = new ArrayList<>();
        int[] nodeColorIndexList = new int[nbNodes];
        Arrays.fill(nodeColorIndexList, -1);
        nodeColorIndexList[0] = 0;
        listColorNode.add(0);
        int[] listColor = new int[kMax];
        listNbNode.add(nodeList.getNodeAt(0).getNb() - 1);
        nodeList.getNodeAt(0).setColor(0);

        boolean[] availableColors = new boolean[kMax];  // Liste temporaire des couleurs disponibles pour le nœud courant.
        Arrays.fill(availableColors, true);

        for (int indNode = 1; indNode < nbNodes; indNode++) {
            listNbNode.add(indNode);
            availableColors = setAvailableColors(indNode, nodeColorIndexList, listNbNode, listColorNode, kMax);
            colorInd = getFirstAvailableColor(availableColors);
            if (colorInd > kMax - 1) {
                listColor = new int[kMax];
                for (NodePlus adj : nodeList.getNodeAt(indNode).getListAdj()) {
                    if (listNbNode.contains(adj.getNb() - 1)) {
                        adj.setColor(listColorNode.get(indiceDansListColorNode(listNbNode, adj.getNb() - 1)));
                    }
                    if (adj.getColor() != -1) {
                        listColor[adj.getColor()] = listColor[adj.getColor()] + 1;
                    }
                }
                int minConflit = nodeList.getNodeAt(indNode).getDegree();
                int repèreConflit = 0;
                for (int j = 0; j < kMax; j++) {
                    if (listColor[j] < minConflit) {
                        minConflit = listColor[j];
                        repèreConflit = j;
                    }
                }
                colorInd = repèreConflit;
                nbConflit += minConflit;
            }
            nodeList.getNodeAt(indNode).setColor(colorInd);
            listColorNode.add(colorInd);
            nodeColorIndexList[indNode] = colorInd;
        }
        return nodeColorIndexList;
    }

    /**
     * Définit les couleurs disponibles pour un nœud donné en fonction des
     * couleurs de ses nœuds adjacents pour un graph de type graph-test.
     *
     * @param indNode L'indice du nœud pour lequel les couleurs disponibles
     * doivent être définies.
     * @param nodeColorIndexList Le tableau des couleurs des nœuds.
     * @param listNbNode La liste des numéros de nœuds.
     * @param listColorNode La liste des couleurs des nœuds.
     * @param kMax Le nombre maximum de couleurs autorisées.
     * @return Un tableau de booléens représentant les couleurs disponibles.
     */
    private boolean[] setAvailableColors(int indNode, int[] nodeColorIndexList, ArrayList<Integer> listNbNode, ArrayList<Integer> listColorNode, int kMax) {
        int nbAdjacentNodes = nodeList.getNodeAt(indNode).getDegree();
        NodePlus[] adjNodesList = nodeList.getNodeAt(indNode).getListAdj();
        for (NodePlus adj : nodeList.getNodeAt(indNode).getListAdj()) {
            if (listNbNode.contains(adj.getNb() - 1)) {
                adj.setColor(listColorNode.get(indiceDansListColorNode(listNbNode, adj.getNb() - 1)));
            }
        }

        boolean[] availableColors = new boolean[kMax];  // availableColors[ii] == false  ->  un des nœuds adjacents du nœud à l'indice colorInd a déjà la même couleur
        Arrays.fill(availableColors, true);            // disponible pour colorier le nœud actuel.

        for (int ii = 0; ii < nbAdjacentNodes; ii++) {
            int indNodeAdj = adjNodesList[ii].getIndex();
            if (nodeColorIndexList[indNodeAdj] != -1) {
                availableColors[nodeColorIndexList[indNodeAdj]] = false;
            }
        }

        return availableColors;
    }

    /**
     * Renvoie le nombre de couleurs utilisées pour colorier le graphe.
     *
     * @param nodeColorIndexList Le tableau des couleurs des nœuds.
     * @return Le nombre de couleurs utilisées.
     */
    public int getNbColors(int[] nodeColorIndexList) {
        ArrayList<Integer> passedColors = new ArrayList<>();
        for (int ii = 0; ii < nbNodes; ii++) {
            if (!passedColors.contains(nodeColorIndexList[ii])) {
                passedColors.add(nodeColorIndexList[ii]);
            }
        }
        return passedColors.size();
    }

    /**
     * Renvoie l'indice de la première couleur disponible.
     *
     * @param colorIsAvailable Le tableau de booléens représentant les couleurs
     * disponibles.
     * @return L'indice de la première couleur disponible.
     */
    public int getFirstAvailableColor(boolean[] colorIsAvailable) {
        int listSize = colorIsAvailable.length;
        int colorInd = 0;
        while (colorInd < listSize && !colorIsAvailable[colorInd]) {    // availableColors[ii] == false  ->  un des nœuds adjacents du nœud à l'indice colorInd a déjà la même couleur
            colorInd++;                                                 // continue de chercher une couleur disponible
        }
        return colorInd;
    }

    /**
     * Colore le graphe en utilisant l'algorithme de Welsh-Powell pour un graph de type graph-test.
     *
     * @param kMax Le nombre maximum de couleurs autorisées.
     * @return le nombre de couleurs utilisées pour la coloration
     */
    public int colorGraphWelshPowell(int kMax) {
        int[] nodeColorIndexList = welshPowellAlgo(kMax);
        ColorPlus[] colorsList = new ColorList(getNbColors(nodeColorIndexList)).generateRandomColorList();

        for (int ii = 0; ii < nbNodes; ii++) {
            ColorPlus colorInt = colorsList[nodeColorIndexList[ii]];
            nodeList.getNodeAt(ii).setColorGraph(colorInt);
        }
        return this.getNbColors(nodeColorIndexList);
    }

    /**
     * Applique l'algorithme de Welsh-Powell pour colorier les nœuds du graphe
     * de type vol-test.
     *
     * @param kMax Le nombre maximum de couleurs autorisées.
     * @return Un tableau d'indices de couleurs pour chaque nœud.
     */
    public int[] welshPowellAlgoVol(int kMax) {
        int colorInd;
        nbConflit = 0;
        ArrayList<Integer> listNbNode = new ArrayList<>();
        ArrayList<Integer> listColorNode = new ArrayList<>();
        int[] nodeColorIndexList = new int[nbNodes];
        Arrays.fill(nodeColorIndexList, -1);
        nodeColorIndexList[0] = 0;
        listColorNode.add(0);
        int[] listColor = new int[kMax];
        listNbNode.add(nodeList.getNodeAt(0).getNb());
        nodeList.getNodeAt(0).setColor(0);

        boolean[] availableColors = new boolean[kMax];  // Liste temporaire des couleurs disponibles pour le nœud courant.
        Arrays.fill(availableColors, true);

        for (int indNode = 1; indNode < nbNodes; indNode++) {
            listNbNode.add(indNode);
            availableColors = setAvailableColorsVol(indNode, nodeColorIndexList, listNbNode, listColorNode, kMax);
            colorInd = getFirstAvailableColor(availableColors);
            if (colorInd > kMax - 1) {
                listColor = new int[kMax];
                for (NodePlus adj : nodeList.getNodeAt(indNode).getListAdj()) {
                    if (listNbNode.contains(adj.getNb())) {
                        adj.setColor(listColorNode.get(indiceDansListColorNode(listNbNode, adj.getNb())));
                    }
                    if (adj.getColor() != -1) {
                        listColor[adj.getColor()] = listColor[adj.getColor()] + 1;
                    }
                }
                int minConflit = nodeList.getNodeAt(indNode).getDegree();
                int repèreConflit = 0;
                for (int j = 0; j < kMax; j++) {
                    if (listColor[j] < minConflit) {
                        minConflit = listColor[j];
                        repèreConflit = j;
                    }
                }
                colorInd = repèreConflit;
                nbConflit += minConflit;
            }
            nodeList.getNodeAt(indNode).setColor(colorInd);
            listColorNode.add(colorInd);
            nodeColorIndexList[indNode] = colorInd;
        }
        return nodeColorIndexList;
    }
    
    /**
     * Colore le graphe en utilisant l'algorithme de Welsh-Powell pour un graph de type vol-test.
     *
     * @param kMax Le nombre maximum de couleurs autorisées.
     * @return le nombre de couleurs utilisées pour la coloration
     */
    public int colorGraphWelshPowellVol(int kMax) {
        int[] nodeColorIndexList = welshPowellAlgoVol(kMax);
        ColorPlus[] colorsList = new ColorList(getNbColors(nodeColorIndexList)).generateRandomColorList();

        for (int ii = 0; ii < nbNodes; ii++) {
            ColorPlus colorInt = colorsList[nodeColorIndexList[ii]];
            nodeList.getNodeAt(ii).setColorGraph(colorInt);
        }
        return this.getNbColors(nodeColorIndexList);
    }

    /**
     * Définit les couleurs disponibles pour un nœud donné en fonction des
     * couleurs de ses nœuds adjacents pour un graph de type vol-test.
     *
     * @param indNode L'indice du nœud pour lequel les couleurs disponibles
     * doivent être définies.
     * @param nodeColorIndexList Le tableau des couleurs des nœuds.
     * @param listNbNode La liste des numéros de nœuds.
     * @param listColorNode La liste des couleurs des nœuds.
     * @param kMax Le nombre maximum de couleurs autorisées.
     * @return Un tableau de booléens représentant les couleurs disponibles.
     */
    private boolean[] setAvailableColorsVol(int indNode, int[] nodeColorIndexList, ArrayList<Integer> listNbNode, ArrayList<Integer> listColorNode, int kMax) {
        int nbAdjacentNodes = nodeList.getNodeAt(indNode).getDegree();
        NodePlus[] adjNodesList = nodeList.getNodeAt(indNode).getListAdj();
        for (NodePlus adj : nodeList.getNodeAt(indNode).getListAdj()) {
            if (listNbNode.contains(adj.getNb())) {
                adj.setColor(listColorNode.get(indiceDansListColorNode(listNbNode, adj.getNb())));
            }
        }

        boolean[] availableColors = new boolean[kMax];  // availableColors[ii] == false  ->  un des nœuds adjacents du nœud à l'indice colorInd a déjà la même couleur
        Arrays.fill(availableColors, true);            // disponible pour colorier le nœud actuel.

        for (int ii = 0; ii < nbAdjacentNodes; ii++) {
            int indNodeAdj = adjNodesList[ii].getIndex();
            if (nodeColorIndexList[indNodeAdj] != -1) {
                availableColors[nodeColorIndexList[indNodeAdj]] = false;
            }
        }

        return availableColors;
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères de la liste
     * des nœuds triés par ordre décroissant de degré.
     *
     * @return Une chaîne de caractères représentant la liste des nœuds triés.
     */
    @Override
    public String toString() {
        String result = "[";
        if (nbNodes > 0) {
            result += String.valueOf(nodeList.getNodeAt(sortedIndexes[0]));
            for (int ii = 1; ii < nbNodes; ii++) {
                result = String.format("%s, %s", result, nodeList.getNodeAt(sortedIndexes[ii]).toString());
            }
        }
        return result + "]";
    }

    /**
     * Renvoie le nombre de conflits de couleur dans le graphe.
     *
     * @return Le nombre de conflits de couleur.
     */
    public int getNbConflits() {
        return nbConflit;
    }

    public NodeList getNodeList() {
        return nodeList;
    }
}
