/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * La classe {@code NegativeKMaxException} est une exception personnalisée qui est lancée
 * lorsqu'une valeur négative est détectée pour le paramètre KMax, qui devrait toujours être positive.
 *
 * @author Mejdi, Amadis et Zakary
 */
public class NegativeKMaxException extends Exception {

    /**
     * Construit une nouvelle exception avec le message spécifié.
     *
     * @param message le message détaillant la raison de l'exception
     */
    public NegativeKMaxException(String message) {
        super(message);
    }
}