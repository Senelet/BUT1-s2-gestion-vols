/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * Exception levée lorsque le code d'un aéroport est inexistant.
 * 
 * Cette exception est destinée à être utilisée pour signaler des erreurs
 * spécifiques liées à des codes d'aéroport invalides ou non trouvés.
 *
 * @author Mejdi, Amadis et Zakary
 */
public class CodeAeroportException extends Exception {
    /**
     * Constructeur de l'exception avec un message spécifique.
     *
     * @param message le message détaillant la cause de l'exception
     */
    public CodeAeroportException(String message) {
        super(message);
    }
}
