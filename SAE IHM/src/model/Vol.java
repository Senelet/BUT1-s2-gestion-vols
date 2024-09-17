package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * La classe Vol représente un vol avec ses détails tels que le numéro de vol,
 * l'aéroport de départ, l'aéroport d'arrivée, l'heure de départ, la durée du
 * vol, etc.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 * <li>{@code numero} - Numéro de vol</li>
 * <li>{@code depart} - Aéroport de départ</li>
 * <li>{@code arrivee} - Aéroport d'arrivée</li>
 * <li>{@code heureDepartHeure} - Heure de départ (heure)</li>
 * <li>{@code heureDepartMinute} - Heure de départ (minute)</li>
 * <li>{@code duree} - Durée du vol (en minutes)</li>
 * <li>{@code niveau} - Altitude du vol</li>
 * </ul>
 *
 * @author Mejdi, Amadis et Zakary
 */
public class Vol {

    private String numero; // Numéro de vol
    private String depart; // Aéroport de départ
    private String arrivee; // Aéroport d'arrivée
    private int heureDepartHeure; // Heure de départ (heure)
    private int heureDepartMinute; // Heure de départ (minute)
    private int duree; // Durée du vol (en minutes)

    private int niveau;//Altitude auquel le vol va devoir voler

    /**
     * Constructeur de la classe Vol pour initialiser un objet Vol avec les
     * détails spécifiés.
     *
     * @param numero Numéro de vol
     * @param depart Aéroport de départ
     * @param arrivee Aéroport d'arrivée
     * @param heureDepartHeure Heure de départ (heure)
     * @param heureDepartMinute Heure de départ (minute)
     * @param duree Durée du vol (en minutes)
     */
    public Vol(String numero, String depart, String arrivee, int heureDepartHeure, int heureDepartMinute, int duree) {
        this.numero = numero;
        this.depart = depart;
        this.arrivee = arrivee;
        this.heureDepartHeure = heureDepartHeure;
        this.heureDepartMinute = heureDepartMinute;
        this.duree = duree;
        this.niveau = -1;
    }

    //-----------------------------------------------------------------------Getter--------------------------------------------------------------------
    /**
     * Méthode pour obtenir l'aéroport de départ du vol.
     *
     * @return Aéroport de départ
     */
    public String getDepart() {
        return this.depart;
    }

    /**
     * Méthode pour obtenir l'aéroport d'arrivée du vol.
     *
     * @return Aéroport d'arrivée
     */
    public String getArrivee() {
        return this.arrivee;
    }

    /**
     * Méthode pour obtenir le numéro de vol.
     *
     * @return Numéro de vol
     */
    public String getNumero() {
        return this.numero;
    }

    /**
     * Méthode pour obtenir la durée du vol en minutes.
     *
     * @return Durée du vol (en minutes)
     */
    public int getDuree() {
        return this.duree;
    }

    /**
     * Méthode pour obtenir l'heure de départ du vol (heure).
     *
     * @return Heure de départ (heure)
     */
    public int getHeureDepartHeure() {
        return this.heureDepartHeure;
    }

    /**
     * Méthode pour obtenir l'heure de départ du vol (minute).
     *
     * @return Heure de départ (minute)
     */
    public int getHeureDepartMinute() {
        return this.heureDepartMinute;
    }

    /**
     * Récupère le niveau du vol.
     *
     * @return Le niveau du vol.
     */
    public int getNiveau() {
        return niveau;
    }

    //-----------------------------------------------------------------------Setter--------------------------------------------------------------------
    /**
     * Modifie le numéro de vol.
     *
     * @param numero Le numéro de vol à modifier.
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Modifie l'aéroport d'arrivée du vol.
     *
     * @param arrivee Le code de l'aéroport d'arrivée à modifier.
     */
    public void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }

    /**
     * Modifie l'aéroport de départ du vol.
     *
     * @param depart Le code de l'aéroport de départ à modifier.
     */
    public void setDepart(String depart) {
        this.depart = depart;
    }

    /**
     * Modifie la durée du vol en minutes.
     *
     * @param duree La durée du vol en minutes à modifier.
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

    /**
     * Modifie l'heure de départ du vol (heure).
     *
     * @param heureDepartHeure L'heure de départ du vol à modifier.
     */
    public void setHeureDepartHeure(int heureDepartHeure) {
        this.heureDepartHeure = heureDepartHeure;
    }

    /**
     * Modifie l'heure de départ du vol (minute).
     *
     * @param heureDepartMinute Les minutes de départ du vol à modifier.
     */
    public void setHeureDepartMinute(int heureDepartMinute) {
        this.heureDepartMinute = heureDepartMinute;
    }

    /**
     * Modifie le niveau du vol.
     *
     * @param niveau Le niveau du vol à modifier.
     */
    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    /**
     * Méthode pour obtenir une représentation textuelle de l'objet Vol.
     *
     * @return Une chaîne représentant les détails du vol.
     */
    
    //-----------------------------------------------------------------------Méthode--------------------------------------------------------------------

    /**
     * Méthode pour convertir l'heure de départ du vol en minutes.
     *
     * @return Heure de départ convertie en minutes
     */
    public double heureDeparttoMinute() {
        return this.heureDepartHeure * 60 + this.heureDepartMinute;
    }

    @Override
    public String toString() {
        // Formate l'heure de départ en HH:MM
        String heureDepartFormatted = String.format("%02d:%02d", heureDepartHeure, heureDepartMinute);
        // Calcule l'heure d'arrivée en ajoutant la durée du vol à l'heure de départ
        int heureArriveeHeure = (heureDepartHeure + duree / 60) % 24;
        int heureArriveeMinute = (heureDepartMinute + duree % 60) % 60;
        String heureArriveeFormatted = String.format("%02d:%02d", heureArriveeHeure, heureArriveeMinute);

        return String.format("Vol %s:\t  Départ: %s (%s)\t  Arrivée: %s (%s)\t  Durée: %d minutes",
                numero, depart, heureDepartFormatted, arrivee, heureArriveeFormatted, duree);
    }
}
