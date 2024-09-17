/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * La classe {@code InvalidDataFormatException} est une exception personnalisée qui est lancée
 * lorsque le format des données est invalide.
 * 
 * @author Mejdi, Amadis et Zakary
 */
public class InvalidDataFormatException extends Exception {

    /**
     * Construit une nouvelle exception avec le message spécifié.
     *
     * @param message le message détaillant la raison de l'exception
     */
    public InvalidDataFormatException(String message) {
        super(message);
    }
}