package coloration;

import java.util.Random;
import java.util.Comparator;

/**
 * Classe NodeComparator pour comparer deux nœuds de type NodePlus en utilisant les valeurs DSAT.
 * Si les valeurs DSAT sont égales, l'ordre est décidé aléatoirement.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 *   <li>{@code random} - Object de la class Random</li>
 * </ul>
 */
public class NodeComparator implements Comparator<NodePlus> {
    private final Random random = new Random();//Object de la class Random

    /**
     * Compare deux nœuds en utilisant leur valeur DSAT (Degree of Saturation).
     * Si les valeurs DSAT sont différentes, les nœuds sont comparés par ordre décroissant de DSAT.
     * Si les valeurs DSAT sont égales, l'ordre est décidé aléatoirement.
     * 
     * @param nodeA Le premier nœud à comparer.
     * @param nodeB Le deuxième nœud à comparer.
     * @return Un entier négatif, zéro ou un entier positif selon que le premier nœud est
     *         moins que, égal à, ou supérieur au deuxième nœud.
     */
    @Override
    public int compare(NodePlus nodeA, NodePlus nodeB) {
        // Compare les DSAT par ordre décroissant
        int dsatComparison = Integer.compare(nodeB.getDsat(), nodeA.getDsat());
        if (dsatComparison != 0) {
            return dsatComparison;
        } else {
            // Quand deux valeurs de DSAT sont égales, décide aléatoirement de comment ordonner les nœuds
            return random.nextBoolean() ? 1 : -1;
        }
    }
}

