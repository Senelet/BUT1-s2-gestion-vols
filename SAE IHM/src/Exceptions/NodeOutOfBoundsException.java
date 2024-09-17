/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * La classe {@code NodeOutOfBoundsException} est une exception personnalisée qui est lancée
 * lorsqu'un nœud est en dehors des limites valides dans une structure de données.
 *
 * @author Mejdi, Amadis et Zakary
 */
public class NodeOutOfBoundsException extends Exception {

    /**
     * Construit une nouvelle exception avec le message spécifié.
     *
     * @param message le message détaillant la raison de l'exception
     */
    public NodeOutOfBoundsException(String message) {
        super(message);
    }
}