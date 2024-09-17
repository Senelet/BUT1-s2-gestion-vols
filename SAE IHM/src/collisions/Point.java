/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collisions;

import model.Aeroport;

/**
 * La classe Point représente un point dans un espace à deux dimensions avec des
 * coordonnées x et y.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 * <li>{@code x} - Coordonnée x du point</li>
 * <li>{@code y} - Coordonnée y du point</li>
 * </ul>
 *
 * @author Mèjdi, Amadis et Zakary
 */
public class Point {

    private double x; // Coordonnée x du point
    private double y; // Coordonnée y du point

    /**
     * Constructeur par défaut de la classe Point. Initialise les coordonnées du
     * point à (0, 0).
     */
    public Point() {
        // Initialiser les coordonnées du point à (0, 0)
        this.x = 0;
        this.y = 0;
    }

    /**
     * Constructeur de la classe Point pour initialiser un point avec des
     * coordonnées spécifiées.
     *
     * @param newX La coordonnée x du point
     * @param newY La coordonnée y du point
     */
    public Point(double newX, double newY) {
        // Initialiser les coordonnées du point avec les valeurs spécifiées
        this.x = newX;
        this.y = newY;
    }

    /**
     * Méthode pour obtenir la coordonnée x du point.
     *
     * @return La coordonnée x du point
     */
    public double getX() {
        return this.x;
    }

    /**
     * Méthode pour obtenir la coordonnée y du point.
     *
     * @return La coordonnée y du point
     */
    public double getY() {
        return this.y;
    }

    /**
     * Méthode pour définir la coordonnée y du point.
     *
     * @param modY La nouvelle coordonnée y du point
     */
    public void setY(double modY) {
        this.y = modY;
    }

    /**
     * Méthode pour définir la coordonnée x du point.
     *
     * @param modX La nouvelle coordonnée x du point
     */
    public void setX(double modX) {
        this.x = modX;
    }

    /**
     * Méthode pour comparer deux points et vérifier s'ils sont égaux.
     *
     * @param B Le point à comparer avec le point actuel
     * @return true si les deux points ont les mêmes coordonnées, sinon false
     */
    public boolean equals(Point B) {
        return (this.y == B.y && this.x == B.x);
    }

    /**
     * Méthode statique pour calculer la distance entre deux points.
     *
     * @param A Le premier point
     * @param B Le deuxième point
     * @return La distance entre les deux points
     */
    public static double calculerDistance(Point A, Point B) {
        double dx = B.x - A.x;
        double dy = B.y - A.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Vérifie si un point d'intersection se trouve sur le segment défini par
     * deux points.
     *
     * @param intersection Le point d'intersection à vérifier.
     * @param p1 Le premier point du segment.
     * @param p2 Le deuxième point du segment.
     * @return {@code true} si le point d'intersection se trouve sur le segment
     * défini par les points {@code p1} et {@code p2}, {@code false} sinon.
     */
    public static boolean intersectionVerif(Point intersection, Point p1, Point p2) {
        // Vérifier si le point d'intersection est sur les droites définies par les deux points du segment
        double minX = Math.min(p1.getX(), p2.getX());
        double maxX = Math.max(p1.getX(), p2.getX());
        double minY = Math.min(p1.getY(), p2.getY());
        double maxY = Math.max(p1.getY(), p2.getY());

        // Vérifier si le point d'intersection est à l'intérieur du segment
        return (intersection.getX() >= (minX - 0.00001) && intersection.getX() <= (maxX + 0.00001) && intersection.getY() >= (minY - 0.00001) && intersection.getY() <= (maxY + 0.00001));
    }

    /**
     * Crée un point à partir des coordonnées d'un aéroport.
     *
     * @param aeroport L'aéroport pour lequel créer le point.
     * @return Un objet {@code Point} représentant les coordonnées de
     * l'aéroport.
     */
    public static Point creerPointAeroport(Aeroport aeroport) {
        return new Point(aeroport.getCordX(), aeroport.getCordY());
    }
}
