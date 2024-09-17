package coloration;

import org.graphstream.graph.Node;

/**
 * Classe NodePlus représente un nœud dans un graphe avec des attributs
 * supplémentaires tels que la couleur et le degré de saturation (DSAT). Cette
 * classe étend la fonctionnalité de la classe Node de GraphStream pour inclure
 * des informations supplémentaires nécessaires pour les algorithmes de
 * coloration de graphe.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 * <li>{@code node} - Le nœud représenté par cette instance</li>
 * <li>{@code nb} - Un nombre associé au nœud (peut représenter son identifiant
 * ou son rang)</li>
 * <li>{@code color} - La couleur actuelle du nœud</li>
 * <li>{@code degree} - Le degré du nœud dans le graphe</li>
 * <li>{@code dsat} - Le degré de saturation du nœud</li>
 * </ul>
 */
public class NodePlus  {

    private final Node node;//Le nœud représenté par cette instance
    private int nb;//Un nombre associé au nœud (peut représenter son identifiant ou son rang)
    private int color;//La couleur actuelle du nœud
    private int degree;//Le degré du nœud dans le graphe
    private int dsat;//Le degré de saturation du nœud

    /**
     * Constructeur de la classe NodePlus. Initialise un nœud avec ses attributs
     * de base.
     *
     * @param newNode Le nœud de base de GraphStream.
     */
    public NodePlus(Node newNode) {
        this.node = newNode;
        this.color = -1; // -1 pour représenter une couleur non assignée
        this.dsat = newNode.getDegree();
        this.degree = newNode.getDegree();
        this.nb = (Integer) newNode.getAttribute("numero");
    }

    /**
     * Retourne la liste des nœuds adjacents.
     *
     * @return Un tableau de NodePlus représentant les nœuds adjacents.
     */
    public NodePlus[] getListAdj() {
        int nbAdj = this.node.getDegree();
        NodePlus[] listAdj = new NodePlus[nbAdj];
        for (int i = 0; i < nbAdj; i++) {
            listAdj[i] = new NodePlus(node.getEdge(i).getOpposite(node));
            listAdj[i].setNb((Integer) node.getEdge(i).getOpposite(node).getAttribute("numero"));
        }
        return listAdj;
    }

    /**
     * Retourne le degré du nœud.
     *
     * @return Le degré du nœud.
     */
    public int getDegree() {
        return node.getDegree();
    }

    /**
     * Retourne l'identifiant du nœud.
     *
     * @return L'identifiant du nœud.
     */
    public String getId() {
        return node.getId();
    }

    /**
     * Retourne la couleur du nœud.
     *
     * @return La couleur du nœud.
     */
    public int getColor() {
        return color;
    }

    /**
     * Obtient le numéro du noeud
     *
     * @return le numéro du noeud
     */
    public String getNumero() {
        return (String) node.getAttribute("label");
    }

    /**
     * Définit la couleur du nœud.
     *
     * @param color La couleur à définir pour le nœud.
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Retourne le degré de saturation (DSAT) du nœud.
     *
     * @return Le degré de saturation du nœud.
     */
    public int getDsat() {
        return dsat;
    }

    /**
     * Retourne le numéro du nœud.
     *
     * @return Le numéro du nœud.
     */
    public int getNb() {
        return nb;
    }

    /**
     * Définit le numéro du nœud.
     *
     * @param newNb Le nouveau numéro du nœud.
     */
    public void setNb(int newNb) {
        this.nb = newNb;
    }

    /**
     * Définit le degré de saturation (DSAT) du nœud.
     *
     * @param newDsat Le nouveau degré de saturation du nœud.
     */
    public void setDsat(int newDsat) {
        this.dsat = newDsat;
    }

    /**
     * Retourne le nœud de base de GraphStream.
     *
     * @return Le nœud de base.
     */
    public Node getNode() {
        return node;
    }

    /**
     * Retourne l'index du nœud.
     *
     * @return L'index du nœud.
     */
    public int getIndex() {
        return node.getIndex();
    }

    /**
     * Retourne un ensemble des couleurs des nœuds voisins.
     *
     * @return Un ensemble d'entiers représentant les couleurs des nœuds
     * voisins.
     */
    /*
    public Set<Integer> getNeighborColors() {
        Set<Integer> neighborColors = new HashSet<>();
        NodePlus[] listAdj = this.getListAdj();
        for (NodePlus neighbor : listAdj) {
            int neighborColor = neighbor.getColor();
            if (neighborColor != -1) {
                neighborColors.add(neighborColor);
            }
        }
        return neighborColors;
    }*/
    /**
     * Définit un attribut pour le nœud.
     *
     * @param var1 Le nom de l'attribut.
     * @param var2 Les valeurs de l'attribut.
     */
    public void setAttribute(String var1, Object... var2) {
        node.setAttribute(var1, var2);
    }

    /**
     * Définit la couleur du nœud dans le graphe en utilisant un objet
     * ColorPlus.
     *
     * @param color L'objet ColorPlus contenant les valeurs RGB de la couleur.
     */
    public void setColorGraph(ColorPlus color) {
        String rgbColor = "rgb(" + color.red + "," + color.green + "," + color.blue + ")";
        setAttribute("ui.style", "fill-color: " + rgbColor + ";");
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du nœud.
     *
     * @return Une chaîne de caractères représentant le nœud.
     */
    @Override
    public String toString() {
        return "Num :" + nb + "   degree  :" + dsat;
    }

    /**
     * Obtient la couleur d'un nœud du graphe sous forme d'un objet
     * {@code ColorPlus}. Cette méthode extrait le style CSS du nœud pour
     * obtenir les valeurs RGB de la couleur de remplissage.
     *
     * @return Un objet {@code ColorPlus} représentant la couleur du nœud, ou
     * null si la couleur n'a pas pu être déterminée.
     */
    public ColorPlus getColorGraph() {
        String style = (String) node.getAttribute("ui.style");
        if (style != null && style.startsWith("fill-color: rgb(")) {
            String rgb = style.substring(16, style.length() - 2);
            String[] parts = rgb.split(",");
            int red = Integer.parseInt(parts[0].trim());
            int green = Integer.parseInt(parts[1].trim());
            int blue = Integer.parseInt(parts[2].trim());
            return new ColorPlus(red, green, blue);
        }
        return null; // Couleur non définie
    }
}
