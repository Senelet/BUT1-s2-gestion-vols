/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ihm;

import Exceptions.InvalidDataFormatException;
import Exceptions.InvalidHourException;
import Exceptions.InvalidLineFormatException;
import Exceptions.InvalidMinuteException;
import Exceptions.NegativeKMaxException;
import Exceptions.NodeOutOfBoundsException;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.ListeAeroport;
import model.ListeVol;
import view.ChargerGraphe;
import view.GraphePlus;
import view.GrapheToPanel;

/**
 * Fenêtre de visualisation d'un graphe ou d'un réseau de vols, selon le
 * contexte de l'application. Cette classe permet d'afficher soit un graphe de
 * test (graphTest), soit un réseau de vols (graphVol) en fonction des données
 * chargées depuis la Fenêtre Principale de l'application. Elle intègre des
 * fonctionnalités pour charger et afficher dynamiquement les données de manière
 * graphique.
 *
 * Les données peuvent être chargées à partir de fichiers spécifiés dans la
 * Fenêtre Principale. Les graphes sont représentés soit par un objet
 * ChargerGraphe pour les graphes de test, soit par un objet GraphePlus pour les
 * réseaux de vols.
 *
 *
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 * <li>{@code mainWindow} : Fenêtre principale de l'application.</li>
 * <li>{@code graphPanel} : Panneau graphique utilisé pour afficher le graphe ou
 * le réseau de vols.</li>
 * <li>{@code graphVol} : Objet représentant un réseau de vols chargé à partir
 * des données.</li>
 * <li>{@code graphTest} : Objet représentant un graphe de test chargé à partir
 * des données.</li>
 * <li>{@code listeAeroport} : Liste des aéroports disponibles dans
 * l'application principale.</li>
 * <li>{@code listeVol} : Liste des vols disponibles dans l'application
 * principale.</li>
 * <li>{@code margeSecu} : Marge de sécurité appliquée aux vols.</li>
 * <li>{@code grapheOuVol} : Indicateur de type de graphe chargé (0 pour graphe
 * de test, 1 pour réseau de vols).</li>
 * <li>{@code nomFichier} : Nom du fichier utilisé pour charger les
 * données.</li>
 * </ul>
 *
 * @author Mejdi,Amadis et Zakary
 */
public class GrapheIntersection extends javax.swing.JFrame {

    private FenetrePrincipale mainWindow; // Fenêtre principale de l'application
    private GrapheToPanel graphPanel; // Panneau graphique utilisé pour afficher le graphe ou le réseau de vols
    private GraphePlus graphVol; // Objet représentant un réseau de vols chargé à partir des données
    private ChargerGraphe graphTest; // Objet représentant un graphe de test chargé à partir des données
    private ListeAeroport listeAeroport; // Liste des aéroports disponibles dans l'application principale
    private ListeVol listeVol; // Liste des vols disponibles dans l'application principale
    private int margeSecu; // Marge de sécurité appliquée aux vols
    private int grapheOuVol; // Indicateur de type de graphe chargé (0 pour graphe de test, 1 pour réseau de vols)
    private String nomFichier; // Nom du fichier utilisé pour charger les données

    /**
     * Construit une nouvelle instance de {@code GrapheIntersection}.
     *
     * @param mainWindow la fenêtre principale de l'application
     * @throws IOException si une erreur d'entrée/sortie se produit
     * @throws NegativeKMaxException si la valeur de kMax est négative
     * @throws InvalidDataFormatException si le format des données est invalide
     * @throws NodeOutOfBoundsException si un nœud est en dehors des limites
     * valides
     * @throws InvalidHourException si l'heure est invalide
     * @throws InvalidMinuteException si les minutes sont invalides
     * @throws InvalidLineFormatException si le format de la ligne est invalide
     */
    public GrapheIntersection(FenetrePrincipale mainWindow) throws IOException, NegativeKMaxException, InvalidDataFormatException, NodeOutOfBoundsException, InvalidHourException, InvalidMinuteException, InvalidLineFormatException {
        this.mainWindow = mainWindow;
        initComponents();
        setResizable(false);
        nomFichier = mainWindow.getNomFichier();
        margeSecu = 15;
        listeAeroport = mainWindow.getListeAeroport();
        listeVol = new ListeVol();
        graphTest = new ChargerGraphe();

        initGraphe();

        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(graphPanel);
        jPanel2.revalidate();
        jPanel2.repaint();

    }

    //-----------------------------------------------------------------------Getter--------------------------------------------------------------------
    /**
     * Récupère la marge de sécurité actuellement définie pour les vols dans le
     * réseau de vols (graphVol).
     *
     * @return La marge de sécurité actuellement définie.
     */
    public int getMargeSecu() {
        return margeSecu;
    }

    /**
     * Récupère le type de graphe ou de réseau de vols actuellement chargé dans
     * la fenêtre.
     *
     * @return 0 si un graphe de test est chargé, 1 si un réseau de vols est
     * chargé.
     */
    public int getGrapheouVol() {
        return grapheOuVol;
    }

    /**
     * Récupère l'objet ChargerGraphe représentant le graphe de test
     * actuellement chargé.
     *
     * @return L'objet ChargerGraphe représentant le graphe de test.
     */
    public ChargerGraphe getGraphTest() {
        return graphTest;
    }

    /**
     * Récupère l'objet GraphePlus représentant le réseau de vols actuellement
     * chargé.
     *
     * @return L'objet GraphePlus représentant le réseau de vols.
     */
    public GraphePlus getGraphVol() {
        return graphVol;
    }

    /**
     * Récupère le nom du fichier associé aux données chargées dans la Fenêtre
     * Principale.
     *
     * @return Le nom du fichier associé aux données.
     */
    public String getNomFichier() {
        return nomFichier;
    }

    //-----------------------------------------------------------------------Setter--------------------------------------------------------------------
    /**
     * Définit la marge de sécurité pour les vols dans le réseau de vols
     * (graphVol).
     *
     * @param margeSecu La nouvelle marge de sécurité à définir.
     */
    public void setMargeSecu(int margeSecu) {
        this.margeSecu = margeSecu;
    }

    //-----------------------------------------------------------------------Méthodes--------------------------------------------------------------------
    /**
     * Initialise le graphe ou le réseau de vols en fonction du type de fenêtre
     * Principale sélectionnée. Charge les données de test (graphTest) ou les
     * données de réseau de vols (graphVol). Détermine également si le graphe
     * chargé est un graphe de test (0) ou un réseau de vols (1).
     *
     * @throws IOException Si une erreur d'entrée/sortie survient lors du
     * chargement des données.
     * @throws NegativeKMaxException Si une valeur de K max négative est
     * utilisée.
     * @throws InvalidDataFormatException Si le format des données chargées est
     * invalide.
     * @throws NodeOutOfBoundsException Si un nœud est en dehors des limites
     * spécifiées.
     * @throws InvalidHourException Si une heure spécifiée est invalide.
     * @throws InvalidMinuteException Si une minute spécifiée est invalide.
     * @throws InvalidLineFormatException Si le format d'une ligne de données
     * est invalide.
     */
    public void initGraphe() throws IOException, NegativeKMaxException, InvalidDataFormatException, NodeOutOfBoundsException, InvalidHourException, InvalidMinuteException, InvalidLineFormatException {
        if (this.mainWindow.getFenId() == 0) {
            mainWindow.chargerGraphe();
            graphTest = mainWindow.getGrapheCharger(); // Initialise un nouveau graphe de test
            graphPanel = new GrapheToPanel(graphTest); // Initialise le panneau graphique avec le graphe de test
            grapheOuVol = 0; // Indique que le graphe chargé est un graphe de test
        } else {
            listeVol = mainWindow.getListeVol(); // Charge la liste de vols depuis le fichier spécifié dans mainWindow
            graphVol = new GraphePlus("Graphe-Aeroport"); // Initialise un nouveau réseau de vols
            graphVol.mettreSommetVol(listeVol); // Ajoute les sommets correspondant aux vols dans le réseau
            graphVol.mettreIntersection(listeVol, listeAeroport, margeSecu); // Génère les intersections entre vols et aéroports
            graphPanel = new GrapheToPanel(graphVol); // Initialise le panneau graphique avec le réseau de vols
            grapheOuVol = 1; // Indique que le graphe chargé est un réseau de vols
        }
    }

    

    /**
     * Charge les données du graphe de test (graphTest) depuis le fichier
     * spécifié dans mainWindow. Affiche un message d'erreur si une erreur
     * survient lors du chargement du graphe.
     *
     * @throws IOException Si une erreur d'entrée/sortie survient lors du
     * chargement du graphe.
     * @throws NegativeKMaxException Si une valeur de K max négative est
     * utilisée.
     * @throws InvalidDataFormatException Si le format des données du graphe est
     * invalide.
     * @throws NodeOutOfBoundsException Si un nœud du graphe est en dehors des
     * limites spécifiées.
     */
    private void chargerListeGraphe() throws IOException, NegativeKMaxException, InvalidDataFormatException, NodeOutOfBoundsException {
        int ligneIgnorer = graphTest.chargerGraph(mainWindow.getFilePathGraphe()); // Charge les données du graphe depuis le fichier spécifié
        if (ligneIgnorer > 0) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement du graphe", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(18, 18, 57));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/Image/retour.png"))); // NOI18N
        jButton7.setPreferredSize(new java.awt.Dimension(39, 39));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/Image/Parametre2.png"))); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(39, 39));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 928, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
        );

        jButton2.setBackground(new java.awt.Color(72, 72, 128));
        jButton2.setFont(new java.awt.Font("Impact", 0, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Coloration");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(72, 72, 128));
        jButton3.setFont(new java.awt.Font("Impact", 0, 15)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Afficher Stats");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(72, 72, 128));
        jButton4.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Appliquer les modifications");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(208, 208, 208)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(63, 63, 63))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //-----------------------------------------------------------------------Action--------------------------------------------------------------------

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        FenetrePrincipale main = new FenetrePrincipale();
        main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ParametreMarge setting = new ParametreMarge(this, rootPaneCheckingEnabled);
        setting.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        GrapheColorie colorie = null;
        try {
            colorie = new GrapheColorie(mainWindow);
        } catch (IllegalArgumentException | IOException | NegativeKMaxException | InvalidDataFormatException | NodeOutOfBoundsException | InvalidHourException | InvalidMinuteException | InvalidLineFormatException ex) {
            Logger.getLogger(GrapheIntersection.class.getName()).log(Level.SEVERE, null, ex);
        }
        colorie.setVisible(true);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        StatNonColorie NoColor = new StatNonColorie(this, rootPaneCheckingEnabled);
        NoColor.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (grapheOuVol == 0) {
            graphTest = new ChargerGraphe();
            try {
                chargerListeGraphe();
            } catch (IOException | NegativeKMaxException | InvalidDataFormatException | NodeOutOfBoundsException ex) {
                Logger.getLogger(GrapheIntersection.class.getName()).log(Level.SEVERE, null, ex);
            }
            graphPanel = new GrapheToPanel(graphTest);
        } else {
            graphVol = new GraphePlus(mainWindow.getPathVol());
            graphVol.mettreSommetVol(listeVol);
            graphVol.mettreIntersection(listeVol, listeAeroport, margeSecu);
            graphPanel = new GrapheToPanel(graphVol);
        }

        jPanel2.removeAll();
        jPanel2.add(graphPanel);
        jPanel2.revalidate();
        jPanel2.repaint();

    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

}
