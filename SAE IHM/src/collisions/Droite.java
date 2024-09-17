/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collisions;

/**
 * La classe Droite représente une droite dans un espace à deux dimensions.
 * Elle est définie par sa pente (si elle n'est pas verticale), son ordonnée à l'origine,
 * et des indicateurs pour déterminer si elle est verticale ou horizontale.
 * <p>
 * Instances de classe :
 * </p>
 * <ul>
 *   <li>{@code pente} - La pente de la droite (si elle n'est pas verticale)</li>
 *   <li>{@code ordOrigine} - L'ordonnée à l'origine de la droite</li>
 *   <li>{@code droiteVerticale} - Indique si la droite est verticale</li>
 *   <li>{@code droiteHorizontale} - Indique si la droite est horizontale</li>
 * </ul>
 * 
 * @author Mejdi, Amadis et Zakary
 */
public class Droite {

    private double pente; // Pente de la droite
    private double ordOrigine; // Ordonnée à l'origine de la droite
    private boolean droiteVerticale; // Indique si la droite est verticale
    private boolean droiteHorizontale; // Indique si la droite est horizontale

    /**
     * Constructeur par défaut de la classe Droite. Initialise la droite avec
     * une pente de 0, une ordonnée à l'origine de 0, et n'est pas verticale.
     */
    public Droite() {
        this.pente = 0;
        this.ordOrigine = 0;
        this.droiteVerticale = false;
    }

    /**
     * Constructeur de la classe Droite pour initialiser une droite avec une
     * pente et une ordonnée à l'origine spécifiées.
     *
     * @param newPente La pente de la droite
     * @param newOrdOrigine L'ordonnée à l'origine de la droite
     */
    public Droite(double newPente, double newOrdOrigine) {
        this.pente = newPente;
        this.ordOrigine = newOrdOrigine;
        this.droiteVerticale = false;
        this.droiteHorizontale = false;
    }
    //-------------------------------------------------------Getter-----------------------------------------------------------------

    /**
     * Méthode pour obtenir la pente de la droite.
     *
     * @return La pente de la droite
     */
    public double getPente() {
        return this.pente;
    }

    /**
     * Méthode pour obtenir l'ordonnée à l'origine de la droite.
     *
     * @return L'ordonnée à l'origine de la droite
     */
    public double getOrdOrigine() {
        return this.ordOrigine;
    }

    /**
     * Vérifie si la droite est horizontale.
     *
     * @return true si la droite est horizontale, sinon false.
     */
    public boolean isDroiteHorizontale() {
        return droiteHorizontale;
    }

    /**
     * Vérifie si la droite est verticale.
     *
     * @return true si la droite est verticale, sinon false.
     */
    public boolean isDroiteVerticale() {
        return droiteVerticale;
    }

    //-------------------------------------------------------Setter-----------------------------------------------------------------
    /**
     * Définit si la droite est horizontale.
     *
     * @param droiteHorizontale true pour définir la droite comme horizontale,
     * sinon false.
     */
    public void setDroiteHorizontale(boolean droiteHorizontale) {
        this.droiteHorizontale = droiteHorizontale;
    }

    /**
     * Définit l'ordonnée à l'origine de la droite.
     *
     * @param ordOrigine La nouvelle ordonnée à l'origine de la droite.
     */
    public void setOrdOrigine(double ordOrigine) {
        this.ordOrigine = ordOrigine;
    }

    /**
     * Définit si la droite est verticale.
     *
     * @param droiteVerticale true pour définir la droite comme verticale, sinon
     * false.
     */
    public void setDroiteVerticale(boolean droiteVerticale) {
        this.droiteVerticale = droiteVerticale;
    }

    //-------------------------------------------------------Méthodes-----------------------------------------------------------------
    /**
     * Méthode pour calculer la droite passant par deux points spécifiés.
     *
     * @param A Le premier point
     * @param B Le deuxième point
     */
    public void calcDroite(Point A, Point B) {
        if (!A.equals(B)) {
            if (A.getX() != B.getX() && A.getY() != B.getY()) {
                this.pente = (B.getY() - A.getY()) / (B.getX() - A.getX());
                this.ordOrigine = A.getY() - this.pente * A.getX();
            } else if (A.getX() == B.getX()) {
                this.droiteVerticale = true;
                this.pente = 1;
                this.ordOrigine = A.getX();
            } else if (A.getY() == B.getY()) {
                this.droiteHorizontale = true;
                this.pente = 0;
                this.ordOrigine = B.getY();
            }
        }
    }

    /**
     * Méthode pour calculer le point d'intersection de cette droite avec une
     * autre droite spécifiée.
     *
     * @param droite1 L'autre droite
     * @return Le point d'intersection des deux droites, ou null s'il n'y a pas
     * d'intersection
     */
    public Point intersection(Droite droite1) {
        Point inter = new Point();
        double xInter;
        double yInter;
        if (this.droiteVerticale && droite1.droiteVerticale && droite1.ordOrigine != this.ordOrigine) {
            // Deux droites verticales
            inter = null;
        } else if (this.droiteVerticale) {
            // Cas d'une droite verticale
            xInter = this.ordOrigine;
            yInter = droite1.pente * xInter + droite1.ordOrigine;
            inter.setX(xInter);
            inter.setY(yInter);
        } else if (droite1.droiteVerticale) {
            // Cas d'une droite verticale pour la deuxième droite
            xInter = droite1.ordOrigine;
            yInter = this.pente * xInter + this.ordOrigine;
            inter.setX(xInter);
            inter.setY(yInter);
        } else if (this.pente == droite1.pente && this.ordOrigine == droite1.ordOrigine) {
            // Droites confondues
            inter.setX(0);
            inter.setY(0);
        } else if (this.pente == droite1.pente) {
            // Droites parallèles, pas d'intersection
            inter = null;
        } else {
            // Cas général
            xInter = (droite1.ordOrigine - this.ordOrigine) / (this.pente - droite1.pente);
            yInter = this.pente * xInter + this.ordOrigine;
            inter.setX(xInter);
            inter.setY(yInter);
        }
        return inter;
    }
}
