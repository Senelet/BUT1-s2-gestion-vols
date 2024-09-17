/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * La classe {@code InvalidOrientationException} est une exception personnalisée qui est lancée
 * lorsqu'une orientation invalide est détectée (par exemple, une valeur qui n'est pas acceptée 
 * comme orientation valide dans le contexte donné).
 *
 * @author Mejdi, Amadis et Zakary
 */
public class InvalidOrientationException extends Exception {

    /**
     * Construit une nouvelle exception avec le message spécifié.
     *
     * @param message le message détaillant la raison de l'exception
     */
    public InvalidOrientationException(String message) {
        super(message);
    }
}