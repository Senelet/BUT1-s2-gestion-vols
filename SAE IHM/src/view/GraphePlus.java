package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mèjdi
 */
import java.util.HashSet;
import java.util.Set;

import collisions.Droite;
import collisions.Point;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import model.*;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.Toolkit;



/**
 * La classe {@code GraphePlus} représente un graphe étendu pour gérer des vols et leurs intersections.
 * Elle hérite de la classe {@code MultiGraph} de la bibliothèque GraphStream.
 * Cette classe permet d'ajouter des sommets et des arêtes représentant des vols,
 * de calculer des statistiques sur le graphe, et de détecter des intersections entre les vols.
 * 
 * @see org.graphstream.graph.implementations.MultiGraph
 * @see model.ListeVol
 * @see model.ListeAeroport
 * @see model.Vol
 * @see collisions.Point
 * @see collisions.Droite
 * @see org.graphstream.algorithm.ConnectedComponents
 * @see org.graphstream.algorithm.Toolkit
 * 
 * @author Mejdi, Amadis et Zakary
 */
public class GraphePlus extends MultiGraph {

    /**
     * Constructeur de la classe GraphePlus.
     *
     * @param id L'identifiant du graphe.
     */
    public GraphePlus(String id) {
        super(id);
    }

    /**
     * Ajoute des arêtes représentant des vols au graphe.
     *
     * @param list La liste des vols à ajouter.
     */
    public void mettreVol(ListeVol list) {
        Vol vol;
        String id, sommet1, sommet2;
        Edge edge;
        for (int i = 0; i < list.tailleList(); i++) {
            vol = list.accesVol(i);
            sommet1 = vol.getDepart();
            sommet2 = vol.getArrivee();
            id = vol.getNumero();
            edge = this.addEdge(id, sommet1, sommet2);
            edge.setAttribute("duree", vol.getDuree());
            edge.setAttribute("depart", vol.getDepart());
            edge.setAttribute("arrive", vol.getArrivee());
            edge.setAttribute("heureDepartHeure", vol.getHeureDepartHeure());
            edge.setAttribute("heureDepartMinute", vol.getHeureDepartMinute());
        }
    }

    /**
     * Ajoute des sommets représentant des vols au graphe.
     *
     * @param list La liste des vols à ajouter.
     */
    public void mettreSommetVol(ListeVol list) {
        Vol vol;
        String sommet;
        Node node;
        Set<String> volAjoutes = new HashSet<>(); // Ensemble pour stocker les codes des aéroports ajoutés
        int taille = list.tailleList();
        for (int i = 0; i < taille; i++) {
            vol = list.accesVol(i);
            if (!volAjoutes.contains(vol.getNumero())) {
                volAjoutes.add(vol.getNumero());
                sommet = getSommetVol(vol);
                node = this.addNode(sommet);
                node.setAttribute("label", sommet);
                node.setAttribute("numero", i);
                node.setAttribute("duree", vol.getDuree());
                node.setAttribute("depart", vol.getDepart());
                node.setAttribute("arrive", vol.getArrivee());
                node.setAttribute("heureDepartHeure", vol.getHeureDepartHeure());
                node.setAttribute("heureDepartMinute", vol.getHeureDepartMinute());
                node.setAttribute("ui.style", "text-size: 10; text-offset: 0, 10;");
            }
        }
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
     * Obtient l'identifiant du sommet pour un vol donné.
     *
     * @param vol Le vol pour lequel obtenir l'identifiant du sommet.
     * @return L'identifiant du sommet correspondant au vol.
     */
    public String getSommetVol(Vol vol) {
        return (String.valueOf(vol.getNumero()));
    }

    /**
     * Obtient l'identifiant de la collision entre deux vols.
     *
     * @param vol1 Le premier vol.
     * @param vol2 Le deuxième vol.
     * @return L'identifiant de la collision entre les deux vols.
     */
    public String getIdCollision(Vol vol1, Vol vol2) {
        return (getSommetVol(vol1) + "|" + getSommetVol(vol2));
    }

    /**
     * Ajoute des arêtes représentant les intersections (collisions) entre les
     * vols au graphe.
     *
     * @param listeVol La liste des vols pour lesquels détecter les
     * intersections.
     * @param listeAeroports La liste des aéroports pour lesquels vérifier les
     * vols.
     * @param margeSecu Marge de sécurité de deux avions potentiellement en
     * collisions.
     */
    public void mettreIntersection(ListeVol listeVol, ListeAeroport listeAeroports, double margeSecu) {
        int cpt = 0;
        boolean intersectionValide = false;
        for (int i = 0; i < listeVol.tailleList(); i++) {
            Vol vol1 = listeVol.accesVol(i);
            for (int j = i + 1; j < listeVol.tailleList(); j++) {
                Vol vol2 = listeVol.accesVol(j);
                intersectionValide = calculerIntersectionVol(vol1, vol2, listeAeroports, margeSecu);
                if (intersectionValide) {
                    cpt++;
                    this.addEdge(getIdCollision(vol1, vol2), getSommetVol(vol1), getSommetVol(vol2));
                }
            }
        }
        System.out.println("Nombre de collision : " + cpt);
    }

    private boolean calculerIntersectionVol(Vol vol1, Vol vol2, ListeAeroport listeAeroports, double margeSecu) {
        boolean result = false;
        String depart1 = vol1.getDepart();
        String arrivee1 = vol1.getArrivee();
        String depart2 = vol2.getDepart();
        String arrivee2 = vol2.getArrivee();

        Aeroport aeroportDepart1 = listeAeroports.accesAeroportCode(depart1);
        Aeroport aeroportArrivee1 = listeAeroports.accesAeroportCode(arrivee1);
        Aeroport aeroportDepart2 = listeAeroports.accesAeroportCode(depart2);
        Aeroport aeroportArrivee2 = listeAeroports.accesAeroportCode(arrivee2);

        Point pointDepart1 = Point.creerPointAeroport(aeroportDepart1);
        Point pointArrivee1 = Point.creerPointAeroport(aeroportArrivee1);
        Point pointDepart2 = Point.creerPointAeroport(aeroportDepart2);
        Point pointArrivee2 = Point.creerPointAeroport(aeroportArrivee2);

        Droite droiteVol1 = new Droite();
        Droite droiteVol2 = new Droite();
        droiteVol1.calcDroite(pointDepart1, pointArrivee1);
        droiteVol2.calcDroite(pointDepart2, pointArrivee2);

        Point intersection = droiteVol1.intersection(droiteVol2);

        if (intersection != null && intersection.getX() == 0 && intersection.getY() == 0) {
            if ((vol1.heureDeparttoMinute() < vol2.heureDeparttoMinute() && (vol2.getDuree() + vol2.heureDeparttoMinute() < vol1.getDuree() + vol1.heureDeparttoMinute() + 15)) || (vol2.heureDeparttoMinute() < vol1.heureDeparttoMinute() && (vol1.getDuree() + vol1.heureDeparttoMinute() <= vol2.getDuree() + vol2.heureDeparttoMinute() + 15))) {
                return pointDepart1.equals(pointDepart2) && pointArrivee1.equals(pointArrivee2);
            } else if (vol1.heureDeparttoMinute() + vol1.getDuree() + margeSecu >= vol2.heureDeparttoMinute() && vol1.heureDeparttoMinute() <= vol2.heureDeparttoMinute() + vol2.getDuree() + margeSecu) {
                return pointDepart1.equals(pointArrivee2) && pointArrivee1.equals(pointDepart2);
            } else {
                return false;
            }

        }

        result = aCollision(vol1, vol2, pointDepart1, pointArrivee1, pointDepart2, pointArrivee2, intersection, margeSecu);

        return result;
    }

    private boolean aCollision(Vol vol1, Vol vol2, Point pointDepart1, Point pointArrivee1, Point pointDepart2, Point pointArrivee2, Point intersection, double margeSecu) {
        boolean result = false;
        if (intersection != null && (Point.intersectionVerif(intersection, pointDepart1, pointArrivee1)) && (Point.intersectionVerif(intersection, pointDepart2, pointArrivee2))) {
            double distanceVol1 = Point.calculerDistance(pointDepart1, pointArrivee1);
            double distanceVol2 = Point.calculerDistance(pointDepart2, pointArrivee2);

            double distanceVol1ToInt = Point.calculerDistance(pointDepart1, intersection);
            double distanceVol2ToInt = Point.calculerDistance(pointDepart2, intersection);

            double vitesseVol1 = distanceVol1 / vol1.getDuree();
            double vitesseVol2 = distanceVol2 / vol2.getDuree();

            double tmpsVol1ToInt = distanceVol1ToInt / vitesseVol1;
            double tmpsVol2ToInt = distanceVol2ToInt / vitesseVol2;

            double heureVol1ToInt = vol1.heureDeparttoMinute() + tmpsVol1ToInt;
            double heureVol2ToInt = vol2.heureDeparttoMinute() + tmpsVol2ToInt;

            if (Math.abs(heureVol1ToInt - heureVol2ToInt) < margeSecu) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Retourne la valeur absolue d'un nombre donné.
     *
     * @param x le nombre dont on veut la valeur absolue
     * @return la valeur absolue du nombre donné
     */
    public double absolue(double x) {
        if (x < 0) {
            return -x;
        }
        return x;
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
