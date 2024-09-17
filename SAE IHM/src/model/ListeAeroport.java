package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Mèjdi
 */
import Exceptions.InvalidLineFormatException;
import Exceptions.InvalidOrientationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * La classe ListeAeroport représente une liste d'aéroports.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 * <li>{@code listeAeroports} - Liste des aéroports</li>
 * <li>{@code nbAeroport} - Nombre d'aéroports dans la liste</li>
 * </ul>
 *
 * @author Mèjdi, Amadis et Zakary
 */
public class ListeAeroport {

    private final ArrayList<Aeroport> listeAeroports; // Liste des aéroports
    private int nbAeroport; // Nombre d'aéroports dans la liste

    /**
     * Constructeur de la classe ListeAeroport pour initialiser une liste vide
     * d'aéroports. Initialise également le compteur d'aéroports.
     */
    public ListeAeroport() {
        listeAeroports = new ArrayList<Aeroport>();
        nbAeroport = 0; // Initialiser le nombre d'aéroports à zéro
    }

    //-----------------------------------------------------------------------Getter--------------------------------------------------------------------
    /**
     * Obtient la liste des aéroports.
     *
     * @return une {@code ArrayList<Aeroport>} la liste des aéroports.
     */
    public ArrayList<Aeroport> getListeAeroports() {
        return listeAeroports;
    }

    //-----------------------------------------------------------------------Méthode--------------------------------------------------------------------
    /**
     * Tente d'ajouter un aéroport à la liste d'aéroports si celui-ci n'est pas
     * déjà présent.
     *
     * @param aeroport L'aéroport à ajouter.
     * @return true si l'aéroport a été ajouté avec succès, false sinon.
     */
    public boolean ajAeroport(Aeroport aeroport) {
        boolean result = false;
        if (aeroport != null && !(this.listeAeroports.contains(aeroport))) {
            result = this.listeAeroports.add(aeroport);
            if (result == true) {
                nbAeroport++;
            }
        }
        return result;
    }

    /**
     * Supprime un aéroport de la liste d'aéroports s'il y est présent.
     *
     * @param aeroport L'aéroport à supprimer.
     * @return true si l'aéroport a été supprimé avec succès, false sinon.
     */
    public boolean supAeroport(Aeroport aeroport) {
        boolean result = false;
        if (aeroport != null && (this.listeAeroports.contains(aeroport))) {
            result = this.listeAeroports.remove(aeroport);
            if (result == true) {
                nbAeroport--;
            }
        }
        return result;
    }

    /**
     * Accède à un aéroport par son indice dans la liste.
     *
     * @param i L'indice de l'aéroport dans la liste.
     * @return L'aéroport à l'indice spécifié ou null si l'indice n'est pas
     * pertinent.
     */
    public Aeroport accesAeroportInd(int i) {
        if (this.indicePertinent(i)) {
            return this.listeAeroports.get(i);
        } else {
            return null;
        }
    }

    /**
     * Recherche un aéroport par son code dans la liste.
     *
     * @param code Le code de l'aéroport à rechercher.
     * @return L'aéroport correspondant au code donné, ou null si aucun aéroport
     * correspondant n'est trouvé.
     */
    public Aeroport accesAeroportCode(String code) {
        int taille = this.listeAeroports.size();
        Aeroport a;
        Aeroport result = null;
        for (int i = 0; i < taille; i++) {
            a = this.accesAeroportInd(i);
            if (a.getCode().equals(code)) {
                result = a;
            }
        }
        return result;
    }

    /**
     * Vérifie si un indice est valide pour accéder à la liste d'aéroports.
     *
     * @param i L'indice à vérifier.
     * @return true si l'indice est valide, false sinon.
     */
    public boolean indicePertinent(int i) {
        boolean result = false;
        if (i >= 0 && i < this.listeAeroports.size()) {
            result = true;
        }
        return result;
    }

    /**
     * Retourne la taille de la liste des aéroports.
     *
     * @return Le nombre d'aéroports dans la liste.
     */
    public int tailleList() {
        return this.listeAeroports.size();
    }

    /**
     * Lit les données des aéroports à partir d'un fichier spécifié et les
     * ajoute à la liste des aéroports. Chaque ligne du fichier doit contenir
     * les données d'un aéroport formatées comme suit : code de
     * l'aéroport;nom;degré latitude;minute
     * latitude;seconde;latitude;orientation latitude; degré longitude;minute
     * longitude;seconde; longitude;orientation longitude. Si le format d'un
     * nombre est incorrect, une ligne est ignorée et une erreur de format de
     * nombre est signalée.
     *
     * @param cheminFichier Le chemin du fichier à lire.
     * @return Le nombre de lignes ignorées en raison d'erreurs de format ou de
     * valeurs invalides.
     * @throws InvalidLineFormatException Si la ligne ne contient pas exactement
     * 10 éléments.
     * @throws InvalidOrientationException Si l'orientation de la latitude ou de
     * la longitude est invalide.(Différent de {'N', 'S', 'E', 'O'})
     */
    public int extractionAeroport(String cheminFichier) throws InvalidLineFormatException, InvalidOrientationException {
        int lignesIgnorees = 0; // Compteur pour les lignes ignorées

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                try {
                    String[] elements = ligne.split(";");

                    // Vérification du nombre d'éléments
                    if (elements.length != 10) {
                        lignesIgnorees++;
                        throw new InvalidLineFormatException("La ligne ne contient pas 10 éléments : " + ligne);
                    }

                    String code = elements[0];
                    String nom = elements[1];

                    // Vérification des orientations
                    String orientationLatitude = elements[5];
                    String orientationLongitude = elements[9];
                    if (!isOrientationValide(orientationLatitude) || !isOrientationValide(orientationLongitude)) {
                        lignesIgnorees++;
                        throw new InvalidOrientationException("Orientation invalide dans la ligne : " + ligne);
                    }

                    double latitude = convertToDecimalDegrees(elements[2], elements[3], elements[4], orientationLatitude);
                    double longitude = convertToDecimalDegrees(elements[6], elements[7], elements[8], orientationLongitude);
                    double cordX = convertX(latitude, longitude);
                    double cordY = convertY(latitude, longitude);

                    Aeroport aeroport = new Aeroport(code, nom, latitude, longitude, cordX, cordY);
                    boolean result = this.ajAeroport(aeroport);
                    if (!result) {
                        System.out.println("Échec de l'ajout de l'aéroport avec le code : " + code);
                    }
                } catch (InvalidLineFormatException | InvalidOrientationException | NumberFormatException e) {
                    System.out.println(e.getMessage());
                    // Incrémenter le compteur seulement si c'est une des exceptions spécifiées
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lignesIgnorees;
    }

    /**
     * Vérifie si l'orientation est valide.
     *
     * @param orientation L'orientation à vérifier.
     * @return true si l'orientation est valide, sinon false.
     */
    private boolean isOrientationValide(String orientation) {
        return "E".equals(orientation) || "N".equals(orientation) || "S".equals(orientation) || "O".equals(orientation);
    }

    /**
     * Convertit des coordonnées géographiques exprimées en degrés, minutes et
     * secondes en degrés décimaux.
     *
     * @param degrees Degrés.
     * @param minutes Minutes.
     * @param seconds Secondes.
     * @param orientation Orientation ('N', 'S', 'E', 'O').
     * @return Les degrés décimaux calculés.
     */
    public static double convertToDecimalDegrees(String degrees, String minutes, String seconds, String orientation) {
        double deg = Double.parseDouble(degrees);
        double min = Double.parseDouble(minutes);
        double sec = Double.parseDouble(seconds);
        double decimalDegrees = deg + (min / 60) + (sec / 3600);
        if (orientation.equals("S") || orientation.equals("O")) {
            decimalDegrees = -decimalDegrees;
        }
        return decimalDegrees;
    }

    /**
     * Calcule la coordonnée X sur une projection cartographique à partir des
     * coordonnées latitudes et longitudes.
     *
     * @param latitude Latitude en degrés décimaux.
     * @param longitude Longitude en degrés décimaux.
     * @return La coordonnée X calculée.
     */
    public double convertX(double latitude, double longitude) {
        int rayon = 6371;
        double latitudeR = Math.toRadians(latitude);
        double longitudeR = Math.toRadians(longitude);
        double result = rayon * Math.cos(latitudeR) * Math.sin(longitudeR);
        return result;
    }

    /**
     * Calcule la coordonnée Y sur une projection cartographique à partir des
     * coordonnées latitudes et longitudes.
     *
     * @param latitude Latitude en degrés décimaux.
     * @param longitude Longitude en degrés décimaux.
     * @return La coordonnée Y calculée.
     */
    public double convertY(double latitude, double longitude) {
        int rayon = 6371;
        double latitudeR = Math.toRadians(latitude);
        double longitudeR = Math.toRadians(longitude);
        double result = rayon * Math.cos(latitudeR) * Math.cos(longitudeR);
        return result;
    }

}
