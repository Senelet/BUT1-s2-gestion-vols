package view;

import Exceptions.InvalidDataFormatException;
import Exceptions.NegativeKMaxException;
import Exceptions.NodeOutOfBoundsException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

/**
 * La classe {@code ChargerGraphe} représente un graphe qui peut être chargé à partir d'un fichier CSV.
 * Elle permet de définir le nombre de sommets et de charger les arêtes entre ces sommets.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 *   <li>{@code id} - Identifiant du graphe</li>
 *   <li>{@code kMax} - Nombre max de couleur différentes (soit d'altitudes différentes)</li>
 *   <li>{@code nbSommet} - Nombre de sommets dans le graphe</li>
 * </ul>
 */
public class ChargerGraphe extends MultiGraph {
    private String id; //Id du graphe
    private int kMax; // Nombre max de couleur différentes (soit d'altitudes différentes)
    private int nbSommet; //Nombre de sommets dans le graphe

    /**
     * Constructeur de la classe {@code ChargerGraphe}.
     *
     * @param id l'identifiant du graphe
     * @param kMax la valeur maximale de k pour le graphe
     * @param nbSommet le nombre de sommets dans le graphe
     */
    public ChargerGraphe(String id, int kMax, int nbSommet) {
        super(id);
        this.kMax = kMax;
        this.nbSommet = nbSommet;
    }

    /**
     * Constructeur par défaut de la classe {@code ChargerGraphe}.
     * Initialise le graphe avec des valeurs par défaut.
     */
    public ChargerGraphe() {
        super("Graphe vide");
        kMax = 0;
        nbSommet = 0;
    }

    //-------------------------------------------------------Getter-----------------------------------------------------------------

    /**
     * Méthode pour obtenir le nombre d'altitudes maximum (soit coloration) des vols
     *
     * @return kMax
     */
    public int getkMax() {
        return kMax;
    }

    
    /**
     * Méthode pour obtenir le nombre de sommet 
     *
     * @return Nombre de sommet
     */
    public int getNbSommet() {
        return nbSommet;
    }
    
    

    //-------------------------------------------------------Setter-----------------------------------------------------------------

    /**
     * Définit l'identifiant du graphe.
     *
     * @param id l'identifiant à définir
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Définit la valeur maximale de k pour le graphe.
     *
     * @param kMax la valeur maximale de k à définir
     */
    public void setkMax(int kMax) {
        this.kMax = kMax;
    }

    /**
     * Définit le nombre de sommets dans le graphe.
     *
     * @param nbSommet le nombre de sommets à définir
     */
    public void setNbSommet(int nbSommet) {
        this.nbSommet = nbSommet;
    }
    
    //-------------------------------------------------------Méthodes-----------------------------------------------------------------


    /**
     * Crée les sommets dans le graphe.
     *
     * @param nbSommet le nombre de sommets à créer
     */
    public void creerNbSommet(int nbSommet) {
        for (int i = 1; i <= nbSommet; i++) {
            Node node = this.addNode(String.valueOf(i));
            node.setAttribute("numero", i);
        }
        this.setAttribute("nbSommet", nbSommet);
    }

    /**
     * Charge le graphe à partir d'un fichier CSV.
     *
     * @param csvFile le chemin vers le fichier CSV
     * @return le nombre de lignes ignorées lors du chargement
     * @throws IOException en cas d'erreur lors de la lecture du fichier
     * @throws NegativeKMaxException si kMax est négatif
     * @throws InvalidDataFormatException si le format des données est incorrect
     * dans le fichier
     * @throws NodeOutOfBoundsException si l'ID d'un sommet est hors limites par
     * rapport au nombre de sommets défini
     */
    public int chargerGraph(String csvFile) throws IOException, NegativeKMaxException, InvalidDataFormatException, NodeOutOfBoundsException {
        String line;
        int lineCount = 0;
        int lignesIgnorées = 0; // Compteur pour les lignes ignorées

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] data = line.split(" ");
                try {
                    if (lineCount == 1) {
                        // Première ligne : kMax
                        int kMax = Integer.parseInt(data[0]);
                        if (kMax < 0) {
                            lignesIgnorées++;
                            throw new NegativeKMaxException("kMax ne peut pas être négatif : " + kMax);
                        }
                        this.setkMax(kMax);
                    } else if (lineCount == 2) {
                        // Deuxième ligne : nbSommet
                        if (data.length != 1) {
                            lignesIgnorées++;
                            throw new InvalidDataFormatException("Format de la ligne nbSommet incorrect : " + line);
                        }
                        int nbSommet = Integer.parseInt(data[0]);
                        if (nbSommet < 0) {
                            throw new IllegalArgumentException("Nombre de sommets invalide (négatif)");
                        }
                        this.setNbSommet(nbSommet);
                        this.creerNbSommet(nbSommet);
                    } else {
                        // Lignes suivantes : arêtes
                        if (data.length != 2) {
                            lignesIgnorées++;
                            throw new InvalidDataFormatException("Format de la ligne d'arête incorrect : " + line);
                        }

                        int idNode1 = Integer.parseInt(data[0]);
                        int idNode2 = Integer.parseInt(data[1]);

                        if (idNode1 > nbSommet || idNode2 > nbSommet) {
                            lignesIgnorées++;
                            throw new NodeOutOfBoundsException("Id du sommet hors limites : " + line);
                        }
                        if (idNode1 < 0 || idNode2 < 0) {
                            lignesIgnorées++;
                            throw new IllegalArgumentException("Nombre de sommets invalide (négatif)");
                        }

                        // Les noms des nœuds dans GraphStream sont des chaînes de caractères, donc nous les convertissons en chaînes
                        String nodeId1 = String.valueOf(idNode1);
                        String nodeId2 = String.valueOf(idNode2);

                        addEdge(nodeId1 + "_" + nodeId2, nodeId1, nodeId2);
                    }
                } catch (IllegalArgumentException | NegativeKMaxException | NodeOutOfBoundsException | InvalidDataFormatException e) {
                    System.out.println(e.getMessage());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lignesIgnorées;
    }
    
    
    //Méthode des stats du graphe
    
    /**
     * Calcule le degré moyen des nœuds du graphe. Le degré moyen est obtenu en
     * sommant les degrés de tous les nœuds et en divisant cette somme par le
     * nombre total de nœuds dans le graphe.
     *
     * @return Le degré moyen des nœuds du graphe.
     */
    public double getDegreMoyen() {
        int totalDegree = 0;
        for (Node node : this) {
            totalDegree += node.getDegree();
        }
        return (double) totalDegree / this.getNombreNoeuds();
    }

    /**
     * Calcule le nombre de composantes connexes dans le graphe. Une composante
     * connexe est un sous-ensemble maximal de nœuds tels que chaque paire de
     * nœuds dans le sous-ensemble est connectée par un chemin.
     *
     * @return Le nombre de composantes connexes dans le graphe.
     */
    public int getNombreComposantesConnexes() {
        ConnectedComponents cc = new ConnectedComponents();
        cc.init(this);
        return cc.getConnectedComponentsCount();
    }

    /**
     * Obtient le nombre de nœuds dans le graphe.
     *
     * @return Le nombre de nœuds dans le graphe.
     */
    public int getNombreNoeuds() {
        return this.getNodeCount();
    }

    /**
     * Obtient le nombre d'arêtes dans le graphe.
     *
     * @return Le nombre d'arêtes dans le graphe.
     */
    public int getNombreAretes() {
        return this.getEdgeCount();
    }

    /**
     * Calcule le diamètre du graphe. Le diamètre d'un graphe est la plus grande
     * distance (en termes de nombre d'arêtes) entre deux nœuds quelconques du
     * graphe.
     *
     * @return Le diamètre du graphe.
     */
    public double getDiametre() {
        return Toolkit.diameter(this);
    }
    
    /**
     * Affiche le graphe en utilisant la bibliothèque GraphStream. Si le
     * paramètre bool est vrai, la mise en page automatique est désactivée.
     *
     * @param bool indique si la mise en page automatique doit être désactivée
     * (true) ou non (false)
     */
    public void afficherGraphe(boolean bool) {
        System.setProperty("org.graphstream.ui", "swing");
        if (bool) {
            this.display().disableAutoLayout();
        } else {
            this.display();
        }
    }
}
