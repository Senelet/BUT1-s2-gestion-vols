package model;

import Exceptions.InvalidHourException;
import Exceptions.InvalidLineFormatException;
import Exceptions.InvalidMinuteException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe ListeVol représente une liste de Vol.
 * <p>
 * Elle permet d'ajouter, de supprimer et d'accéder aux vols dans la liste. Elle
 * peut également extraire les données des vols à partir d'un fichier spécifié.
 * </p>
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 * <li>{@code listeVols} - Liste des vols</li>
 * <li>{@code nbVol} - Nombre de vols dans la liste</li>
 * </ul>
 *
 * @author Mejdi, Amadis et Zakary
 */
public class ListeVol {

    private final ArrayList<Vol> listeVols; // Liste des vols
    private int nbVol; // Nombre de vols dans la liste

    /**
     * Constructeur de la classe ListeVol pour initialiser une liste vide de
     * Vol. Initialise également le compteur de Vol.
     */
    public ListeVol() {
        listeVols = new ArrayList<Vol>();
        nbVol = 0;
    }

    //-----------------------------------------------------------------------Getter--------------------------------------------------------------------
    /**
     * Obtient la liste des vols.
     *
     * @return une {@code List<Vol>} contenant les vols
     */
    public List<Vol> getListeVol() {
        return listeVols;
    }

    //-------------------------------------------------------Méthodes-----------------------------------------------------------------
    /**
     * Tente d'ajouter un vol à la liste des vols s'il n'est pas déjà inclus.
     *
     * @param vol L'objet vol à ajouter.
     * @return true si le vol a été ajouté avec succès à la liste, false sinon.
     */
    public boolean ajVol(Vol vol) {
        boolean result = false;
        if (vol != null && !(this.listeVols.contains(vol))) {
            result = this.listeVols.add(vol);
            if (result) {
                nbVol++;
            }
        }
        return result;
    }

    /**
     * Supprime un vol de la liste des vols s'il existe.
     *
     * @param vol L'objet vol à supprimer.
     * @return true si le vol a été supprimé avec succès de la liste, false
     * sinon.
     */
    public boolean supVol(Vol vol) {
        boolean result = false;
        if (vol != null && (this.listeVols.contains(vol))) {
            result = this.listeVols.remove(vol);
            if (result) {
                nbVol--;
            }
        }
        return result;
    }

    /**
     * Accède à un vol par son indice dans la liste.
     *
     * @param i L'index du vol dans la liste.
     * @return Le vol à l'index spécifié ou null si l'index n'est pas valide.
     */
    public Vol accesVol(int i) {
        if (indicePertinent(i)) {
            return this.listeVols.get(i);
        } else {
            return null;
        }
    }

    /**
     * Vérifie si l'index donné est valide pour accéder à la liste des vols.
     *
     * @param i L'index à vérifier.
     * @return true si l'index est dans les limites de la liste des vols, false
     * sinon.
     */
    public boolean indicePertinent(int i) {
        return i >= 0 && i < this.listeVols.size();
    }

    /**
     * Renvoie la taille de la liste des vols.
     *
     * @return Le nombre de vols dans la liste.
     */
    public int tailleList() {
        return this.listeVols.size();
    }

    /**
     * Lit les données des vols à partir d'un fichier spécifié et les ajoute à
     * la liste des vols. Chaque ligne du fichier doit contenir les données d'un
     * vol formatées comme suit : numéro du vol;lieu de
     * départ;lieu;d'arrivée;heure de départ;minute de départ;durée du vol. Si
     * le format d'un nombre est incorrect, une ligne est ignorée et une erreur
     * de format de nombre est signalée.
     *
     * @param cheminFichier Le chemin du fichier à lire.
     * @return Le nombre de lignes ignorées en raison d'erreurs de format ou de
     * valeurs invalides.
     * @throws InvalidHourException Si l'heure de départ est invalide (pas entre
     * 0 et 23).
     * @throws InvalidMinuteException Si la minute de départ est invalide (pas
     * entre 0 et 59).
     * @throws InvalidLineFormatException Si la ligne ne contient pas exactement
     * 6 éléments.
     * @throws IllegalArgumentException Si la durée de vol est négative.
     */
    public int extractionVol(String cheminFichier) throws InvalidHourException, InvalidMinuteException, InvalidLineFormatException {
        int lignesIgnorees = 0; // Compteur pour les lignes ignorées

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                try {
                    String[] elements = ligne.split(";");
                    if (elements.length != 6) {
                        throw new InvalidLineFormatException("La ligne ne contient pas exactement 6 elements : " + ligne);
                    }
                    String numero = elements[0];
                    String depart = elements[1];
                    String arrivee = elements[2];
                    int heureDepart = Integer.parseInt(elements[3]);
                    int minuteDepart = Integer.parseInt(elements[4]);
                    int dureeVol = Integer.parseInt(elements[5]);

                    if (heureDepart < 0 || heureDepart > 23) {
                        throw new InvalidHourException("Heure de départ invalide : " + heureDepart);
                    }

                    if (minuteDepart < 0 || minuteDepart > 59) {
                        throw new InvalidMinuteException("Minute de départ invalide : " + minuteDepart);
                    }
                    if (dureeVol < 0) {
                        throw new IllegalArgumentException("Durée de vol invalide (négatif) : " + ligne);
                    }

                    Vol vol = new Vol(numero, depart, arrivee, heureDepart, minuteDepart, dureeVol);
                    if (!ajVol(vol)) {
                        System.out.println("Échec de l'ajout du vol numéro : " + numero);
                    }
                } catch (InvalidLineFormatException | InvalidHourException | InvalidMinuteException e) {
                    lignesIgnorees++;
                    System.out.println(e.getMessage());
                } catch (NumberFormatException e) {
                    lignesIgnorees++;
                    System.out.println("Erreur de format de nombre : " + ligne);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lignesIgnorees;
    }
}
