package model;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */





/**
 * La classe Aeroport représente un aéroport avec ses informations telles que le code, le nom, la latitude, la longitude et les coordonnées X et Y.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 *   <li>{@code code} - Code de l'aéroport</li>
 *   <li>{@code nom} - Nom de l'aéroport</li>
 *   <li>{@code latitude} - Latitude de l'aéroport</li>
 *   <li>{@code longitude} - Longitude de l'aéroport</li>
 *   <li>{@code cordX} - Coordonnée X de l'aéroport</li>
 *   <li>{@code cordY} - Coordonnée Y de l'aéroport</li>
 * </ul>
 * 
 * @author Mèjdi, Amadis et Zakary
 */
public class Aeroport {
    
    private String code; // Code de l'aéroport
    private String nom; // Nom de l'aéroport
    private double latitude; // Latitude de l'aéroport
    private double longitude; // Longitude de l'aéroport
    private double cordX; // Coordonnée X de l'aéroport
    private double cordY; // Coordonnée Y de l'aéroport

    /**
     * Constructeur de la classe Aeroport pour initialiser un aéroport avec les informations spécifiées.
     *
     * @param code     Le code de l'aéroport
     * @param nom      Le nom de l'aéroport
     * @param latitude La latitude de l'aéroport
     * @param longitude La longitude de l'aéroport
     * @param cordX    La coordonnée X de l'aéroport
     * @param cordY    La coordonnée Y de l'aéroport
     */
    public Aeroport(String code, String nom, double latitude, double longitude, double cordX, double cordY) {
        this.code = code;
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cordX = cordX;
        this.cordY = cordY;
    }
    
    //-----------------------------------------------------------------------Getter--------------------------------------------------------------------
    
    /**
     * Méthode pour obtenir le code de l'aéroport.
     *
     * @return Le code de l'aéroport
     */
    public String getCode(){
        return this.code;
    }
    
    /**
     * Méthode pour obtenir le nom de l'aéroport.
     *
     * @return Le nom de l'aéroport
     */
    public String getNom(){
        return this.nom;
    }
    
    /**
     * Méthode pour obtenir la coordonnée X de l'aéroport.
     *
     * @return La coordonnée X de l'aéroport
     */
    public double getCordX(){
        return this.cordX;
    }
    
    /**
     * Méthode pour obtenir la coordonnée Y de l'aéroport.
     *
     * @return La coordonnée Y de l'aéroport
     */
    public double getCordY(){
        return this.cordY;
    }

    /**
     * Méthode pour obtenir la latitude de l'aéroport.
     *
     * @return La latitude de l'aéroport
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Méthode pour obtenir la longitude de l'aéroport.
     *
     * @return La longitude de l'aéroport
     */
    public double getLongitude() {
        return longitude;
    }
    
    //-----------------------------------------------------------------------Setter--------------------------------------------------------------------
    
    /**
     * Méthode pour définir la coordonnée X de l'aéroport.
     *
     * @param newCordX La nouvelle coordonnée X de l'aéroport
     */
    public void setCordX(double newCordX){
        this.cordX = newCordX;
    }
    
    /**
     * Méthode pour définir la coordonnée Y de l'aéroport.
     *
     * @param newCordY La nouvelle coordonnée Y de l'aéroport
     */
    public void setCordY(double newCordY){
        this.cordY = newCordY;
    }
    
    /**
     * Méthode pour obtenir une représentation textuelle de l'aéroport.
     *
     * @return Une chaîne de caractères représentant l'aéroport
     */
    public String toString(){
        return this.code;
    }
}

