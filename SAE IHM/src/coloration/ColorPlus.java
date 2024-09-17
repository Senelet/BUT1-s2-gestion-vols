package coloration;

import java.util.Objects;
import java.util.Random;

/**
 * La classe ColorPlus représente une couleur avec des composants rouge, vert et bleu (RGB).
 * Elle inclut des méthodes pour vérifier si la couleur est noire, pour comparer des couleurs,
 * pour générer une couleur aléatoire, et pour obtenir le code de hachage de la couleur.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 *   <li>{@code red} - Composant rouge de la couleur (valeur entre 0 et 255).</li>
 *   <li>{@code green} - Composant vert de la couleur (valeur entre 0 et 255).</li>
 *   <li>{@code blue} - Composant blue de la couleur (valeur entre 0 et 255).</li>
 * </ul>
 * @author Mejdi,Zakary et Amadis
 */
public class ColorPlus {
    /**
     * Composant rouge de la couleur (valeur entre 0 et 255).
     */
    int red;
    
    /**
     * Composant vert de la couleur (valeur entre 0 et 255).
     */
    int green;
    
    /**
     * Composant bleu de la couleur (valeur entre 0 et 255).
     */
    int blue;
    
    /**
     * Constructeur par défaut qui initialise les composants de la couleur à 0.
     */
    public ColorPlus() { }

    /**
     * Constructeur qui initialise les composants de la couleur avec des valeurs spécifiques.
     *
     * @param newRed Valeur du composant rouge (entre 0 et 255).
     * @param newGreen Valeur du composant vert (entre 0 et 255).
     * @param newBlue Valeur du composant bleu (entre 0 et 255).
     */
    public ColorPlus(int newRed, int newGreen, int newBlue) {
        red = newRed;
        green = newGreen;
        blue = newBlue;
    }
    
    /**
     * Vérifie si la couleur est noire (tous les composants sont à 0).
     *
     * @return true si la couleur est noire, false sinon.
     */
    public boolean isBlack() {
        return red == 0 && green == 0 && blue == 0;
    }
    
    /**
     * Compare cette couleur avec un autre objet pour vérifier s'ils sont égaux.
     *
     * @param obj L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ColorPlus color = (ColorPlus) obj;
        return red == color.red && green == color.green && blue == color.blue;
    }

    /**
     * Génère un code de hachage pour cette couleur.
     *
     * @return Le code de hachage de la couleur.
     */
    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue);
    }
    
    /**
     * Génère une couleur aléatoire avec des composants rouge, vert et bleu aléatoires.
     *
     * @return Une instance de ColorPlus avec des valeurs aléatoires pour les composants.
     */
    public static ColorPlus generateRandomColor() {
        Random randomGenerator = new Random();
        int red = randomGenerator.nextInt(256);
        int green = randomGenerator.nextInt(256);
        int blue = randomGenerator.nextInt(256);

        return new ColorPlus(red, green, blue);
    }

    /**
     * Obtient la valeur du composant bleu de la couleur.
     *
     * @return La valeur du composant bleu (entre 0 et 255).
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Obtient la valeur du composant rouge de la couleur.
     *
     * @return La valeur du composant rouge (entre 0 et 255).
     */
    public int getRed() {
        return red;
    }

    /**
     * Obtient la valeur du composant vert de la couleur.
     *
     * @return La valeur du composant vert (entre 0 et 255).
     */
    public int getGreen() {
        return green;
    }
}
