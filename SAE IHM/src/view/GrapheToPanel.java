/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.camera.Camera;

/**
 * La classe {@code GrapheToPanel} représente un panneau graphique contenant un
 * graphe interactif, permettant de visualiser et d'interagir avec un graphe.
 * <p>
 * Cette classe configure un panneau principal avec un layout BorderLayout, crée
 * un Viewer pour le graphe, active la mise en page automatique, ajoute un
 * panneau de vue (ViewPanel) pour afficher le graphe, et ajoute un gestionnaire
 * de souris pour permettre le zoom.
 * </p>
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 * <li>{@code viewer} - Viewer pour afficher le graphe</li>
 * <li>{@code viewPanel} - Panneau de vue pour afficher le graphe</li>
 * </ul>
 *
 * @author Mejdi, Zakary et Amadis
 */
public class GrapheToPanel extends JPanel {

    /**
     * Construit une nouvelle instance de {@code GrapheToPanel}. Ce constructeur
     * configure un panneau Swing pour afficher un graphe GraphStream, en
     * ajoutant un panneau de visualisation du graphe et en configurant le zoom.
     *
     * @param graphe Le graphe à afficher dans le panneau.
     */
    public GrapheToPanel(MultiGraph graphe) {
        // Configure le layout du JPanel principal
        this.setLayout(new BorderLayout());

        // Crée un Viewer pour le graphe
        SwingViewer viewer = new SwingViewer(graphe, SwingViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);

        // Active la mise en page automatique
        viewer.enableAutoLayout();

        // Crée un ViewPanel à partir du Viewer
        ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);
        viewPanel.setPreferredSize(new Dimension(950, 500));

        // Ajouter un gestionnaire de souris pour le zoom
        viewPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                Camera camera = viewPanel.getCamera();
                double zoomFactor = Math.pow(1.1, -e.getWheelRotation());
                camera.setViewPercent(camera.getViewPercent() * zoomFactor);
            }
        });

        // Ajoute le ViewPanel au JPanel principal
        this.add(viewPanel, BorderLayout.CENTER);
    }
}
