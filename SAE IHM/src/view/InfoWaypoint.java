
package view;

import model.Vol;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Étend DefaultWaypoint pour inclure des informations supplémentaires et des vols associés.
 * Cette classe représente un point de passage sur une carte avec des informations supplémentaires et des vols associés.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 *   <li>{@code info} - Information associée au point de passage</li>
 *   <li>{@code vols} - Liste des vols associés au point de passage</li>
 * </ul>
 */

public class InfoWaypoint extends DefaultWaypoint {
    
    private String info;            // Information associée au point de passage
    private final List<Vol> vols;   // Liste des vols associés au point de passage
    
    /**
     * Construit un InfoWaypoint avec les coordonnées et les informations données.
     *
     * @param coord Les coordonnées géographiques du point de passage
     * @param info Les informations associées au point de passage
     */
    public InfoWaypoint(GeoPosition coord, String info) {
        super(coord);
        this.info = info;
        this.vols = new ArrayList<>();
    }
    
    /**
     * Retourne les informations associées à ce point de passage.
     *
     * @return Les informations associées
     */
    public String getInfo() {
        return info;
    }
    
    /**
     * Retourne la liste des vols associés à ce point de passage.
     *
     * @return La liste des vols associés
     */
    public List<Vol> getVols() {
        return vols;
    }

    /**
     * Ajoute un vol à la liste des vols associés à ce point de passage.
     *
     * @param vol Le vol à ajouter
     */
    void addVol(Vol vol) {
        vols.add(vol);
    }
}
