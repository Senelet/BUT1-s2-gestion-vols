/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * La classe {@code InvalidLineFormatException} est une exception personnalisée qui est lancée
 * lorsqu'une ligne dans un fichier de données ne respecte pas le format attendu.
 * 
 * @author Mejdi, Amadis et Zakary
 */
public class InvalidLineFormatException extends Exception {

    /**
     * Construit une nouvelle exception avec le message spécifié.
     *
     * @param message le message détaillant la raison de l'exception
     */
    public InvalidLineFormatException(String message) {
        super(message);
    }
}