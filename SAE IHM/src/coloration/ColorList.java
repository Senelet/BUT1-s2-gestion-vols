package coloration;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 * La classe ColorList gère une liste de couleurs.
 * Elle permet de générer une liste de couleurs aléatoires et de récupérer cette liste.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 *   <li>{@code colorList} - Tableau de ColorPlus contenant la liste des couleurs.</li>
 *   <li>{@code nbColors} - Nombre de couleurs dans la liste.</li>
 * </ul>
 * @author Mejdi, Amadis et Zakary
 */
public class ColorList {
    
    /**
     * Tableau de ColorPlus contenant la liste des couleurs.
     */
    final ColorPlus[] colorList;
    
    /**
     * Nombre de couleurs dans la liste.
     */
    final int nbColors;

    /**
     * Constructeur pour créer une instance de ColorList avec un nombre spécifique de couleurs.
     *
     * @param newNbColors Le nombre de couleurs à générer.
     */
    public ColorList(int newNbColors) {
        nbColors = newNbColors;
        colorList = new ColorPlus[nbColors];
    }

    /**
     * Génère une liste de couleurs aléatoires.
     * Chaque couleur est générée à l'aide de la méthode generateRandomColor() de la classe ColorPlus.
     *
     * @return Un tableau de ColorPlus contenant les couleurs générées.
     */
    public ColorPlus[] generateRandomColorList() {
        ColorPlus tmpColor;
        for (int ii = 0; ii < nbColors; ii++) {
            tmpColor = new ColorPlus().generateRandomColor();
            colorList[ii] = tmpColor;
        }
        return colorList;
    }
    
    /**
     * Retourne la liste des couleurs.
     *
     * @return Un tableau de ColorPlus contenant les couleurs.
     */
    public ColorPlus[] getColorList(){
        return colorList;
    }
    
    /**
     * Retourne le nombre de couleurs différentes.
     *
     * @return Un entier contenant le nombre de couleurs.
     */
    public int getNbColors(){
        return nbColors;
    }
    
}

