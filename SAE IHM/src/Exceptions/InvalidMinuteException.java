/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * La classe {@code InvalidMinuteException} est une exception personnalisée qui est lancée
 * lorsqu'une valeur de minute est invalide (par exemple, hors de la plage 0-59).
 * 
 * @author Mejdi, Amadis et Zakary
 */
public class InvalidMinuteException extends Exception {

    /**
     * Construit une nouvelle exception avec le message spécifié.
     *
     * @param message le message détaillant la raison de l'exception
     */
    public InvalidMinuteException(String message) {
        super(message);
    }
}