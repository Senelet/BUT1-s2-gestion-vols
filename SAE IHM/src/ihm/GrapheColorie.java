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
import coloration.Dsatur;
import coloration.NodeList;
import coloration.WelshPowell;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.ListeAeroport;
import model.ListeVol;
import view.ChargerGraphe;
import view.GraphePlus;
import view.GrapheToPanel;

/**
 * Fenêtre de coloration de graphe ou de réseau de vols, selon le contexte de
 * l'application. Cette classe permet de visualiser et de colorier dynamiquement
 * soit un graphe de test (graphTest), soit un réseau de vols (graphVol) chargé
 * à partir des données fournies par la Fenêtre Principale de l'application.
 * Elle intègre des fonctionnalités pour appliquer différents algorithmes de
 * coloration (DSATUR ou Welsh-Powell) et afficher le résultat graphiquement.
 *
 * Les données peuvent être chargées à partir de fichiers spécifiés dans la
 * Fenêtre Principale. Les graphes sont représentés soit par un objet
 * ChargerGraphe pour les graphes de test, soit par un objet GraphePlus pour les
 * réseaux de vols.
 *
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 * <li>{@code mainWindow} : Fenêtre principale de l'application.</li>
 * <li>{@code graphPanel} : Panneau graphique utilisé pour afficher le graphe ou
 * le réseau de vols colorié.</li>
 * <li>{@code graphVol} : Objet représentant un réseau de vols chargé à partir
 * des données.</li>
 * <li>{@code graphTest} : Objet représentant un graphe de test chargé à partir
 * des données.</li>
 * <li>{@code listAeroport} : Liste des aéroports disponibles dans l'application
 * principale.</li>
 * <li>{@code listeVol} : Liste des vols disponibles dans l'application
 * principale.</li>
 * <li>{@code nodeList} : Liste de nœuds utilisée pour la coloration du
 * graphe.</li>
 * <li>{@code kMax} : Paramètre kMax utilisé pour la coloration des graphes ou
 * des réseaux de vols.</li>
 * <li>{@code grapheOuVol} : Indicateur de type de structure (0 pour graphe de
 * test, 1 pour réseau de vols).</li>
 * <li>{@code nbCouleurs} : Nombre de couleurs utilisées pour la coloration du
 * graphe ou du réseau de vols.</li>
 * <li>{@code taille} : Taille de la liste des aéroports ou des noeuds du graphe chargé.</li>
 * <li>{@code margeSecu} : Marge de sécurité appliquée aux vols.</li>
 * <li>{@code nomFichier} : Nom du fichier utilisé pour charger les
 * données.</li>
 * <li>{@code wp} : Objet WelshPowell utilisé pour la coloration par
 * l'algorithme de Welsh-Powell.</li>
 * </ul>
 *
 * @author Mejdi,Amadis et Zakary
 */
public class GrapheColorie extends javax.swing.JFrame {

    private final FenetrePrincipale mainWindow;//Fenêtre principale de l'application
    private GrapheToPanel graphPanel;//Panneau graphique utilisé pour afficher le graphe ou le réseau de vols colorié
    private GraphePlus graphVol; //Objet représentant un réseau de vols chargé à partir des données.
    private ChargerGraphe graphTest; //Objet représentant un graphe de test chargé à partir des données.
    private ListeAeroport listAeroport; //Liste des aéroports disponibles dans l'application principale.
    private ListeVol listeVol;//Liste des vols disponibles dans l'application principale.
    private NodeList nodeList;//Liste de nœuds utilisée pour la coloration du graphe
    private int kMax; //Paramètre kMax utilisé pour la coloration des graphes ou vols
    private int grapheOuVol; //Indicateur de type de structure (0 pour graphe de test, 1 pour réseau de vols
    private int nbCouleurs;//Nombre de couleurs utilisées pour la coloration du graphe
    private  int taille; //Taille de la liste des aéroports ou des noeuds du graphe chargé.
    private final double margeSecu;// Marge de sécurité appliquée aux vols.
    private final String nomFichier;//Nom du fichier utilisé pour charger les données.
    private WelshPowell wp;//Objet WelshPowell utilisé pour la coloration par l'algorithme de Welsh-Powell

    /**
     * Constructeur de la classe {@code GrapheColorie}.
     *
     * Ce constructeur initialise la fenêtre principale avec un graphe coloré,
     * en configurant les composants nécessaires et en préparant les données
     * requises.
     *
     * @param mainWindow la fenêtre principale de l'application
     * @throws IOException si une erreur d'entrée/sortie se produit lors du
     * chargement des données
     * @throws NegativeKMaxException si la valeur de kMax est négative
     * @throws InvalidDataFormatException si les données ont un format invalide
     * @throws NodeOutOfBoundsException si un nœud est en dehors des limites
     * autorisées
     * @throws InvalidHourException si une heure est invalide
     * @throws InvalidMinuteException si une minute est invalide
     * @throws InvalidLineFormatException si le format d'une ligne de données
     * est invalide
     */
    public GrapheColorie(FenetrePrincipale mainWindow) throws IOException, NegativeKMaxException, InvalidDataFormatException, NodeOutOfBoundsException, InvalidHourException, InvalidMinuteException, InvalidLineFormatException {
        this.mainWindow = mainWindow;
        initComponents();
        setResizable(false);
        margeSecu = 15;
        kMax = 4;
        nomFichier = mainWindow.getNomFichier();
        initGrapheColo();
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(graphPanel);
        jPanel2.revalidate();
        jPanel2.repaint();
    }

    //-----------------------------------------------------------------------Getter--------------------------------------------------------------------
    /**
     * Obtenir la valeur actuelle de kMax.
     *
     * @return La valeur de kMax
     */
    public int getkMax() {
        return kMax;
    }

    /**
     * Obtenir le type de graphe ou de vol (0 pour graphe, 1 pour vol).
     *
     * @return Le type de graphe ou de vol
     */
    public int getGrapheouVol() {
        return grapheOuVol;
    }

    /**
     * Obtenir l'objet ChargerGraphe associé.
     *
     * @return L'objet ChargerGraphe
     */
    public ChargerGraphe getGraphTest() {
        return graphTest;
    }

    /**
     * Obtenir l'objet GraphePlus associé.
     *
     * @return L'objet GraphePlus
     */
    public GraphePlus getGraphVol() {
        return graphVol;
    }

    /**
     * Obtenir le nom du fichier.
     *
     * @return Le nom du fichier
     */
    public String getNomFichier() {
        return nomFichier;
    }

    /**
     * Obtenir le nombre de couleurs utilisées.
     *
     * @return Le nombre de couleurs
     */
    public int getNbCouleurs() {
        return nbCouleurs;
    }

    /**
     * Obtenir l'algorithme actuellement sélectionné.
     *
     * @return Le nom de l'algorithme
     */
    public String getAlgo() {
        return (String) jComboBox1.getSelectedItem();
    }

    /**
     * Obtenir le nombre de conflits (selon l'algorithme sélectionné).
     *
     * @return Le nombre de conflits
     */
    public int getNbConflit() {
        if (jComboBox1.getSelectedItem().equals("Dsatur")) {
            return Dsatur.getNbConflit();
        } else {
            return wp.getNbConflits();
        }
    }

    //-----------------------------------------------------------------------Setter--------------------------------------------------------------------
    /**
     * Définir la valeur de kMax.
     *
     * @param kMax La nouvelle valeur de kMax
     */
    public void setkMax(int kMax) {
        this.kMax = kMax;
    }

    //-----------------------------------------------------------------------Méthode--------------------------------------------------------------------
    /**
     * Colorer le graphe en utilisant l'algorithme DSATUR.
     */
    public void coloDsatur() {
        if (grapheOuVol == 0) {
            graphTest = mainWindow.getGrapheCharger();
            // Recolorier le graphe en utilisant DSATUR
            nodeList = new NodeList(graphTest);
            nbCouleurs = Dsatur.colorierDsaturGraphTest(nodeList, kMax);
            Dsatur.appliqueColor(nbCouleurs, nodeList);
            graphPanel = new GrapheToPanel(graphTest); // Recréez le panneau de graphique avec les nouvelles données
        } else {
            
            listAeroport = mainWindow.getListeAeroport();
            nodeList = new NodeList(graphVol);
            nbCouleurs = Dsatur.colorierDsaturVolTest(nodeList, kMax);
            Dsatur.appliqueColor(nbCouleurs, nodeList);
            graphPanel = new GrapheToPanel(graphVol);
        }
    }

    /**
     * Colorer le graphe en utilisant l'algorithme Welsh-Powell.
     */
    public void coloWP() {
        if (grapheOuVol == 0) {
            // Recolorier le graphe en utilisant WP
            wp = new WelshPowell(graphTest);
            nbCouleurs = wp.colorGraphWelshPowell(kMax);
            graphPanel = new GrapheToPanel(graphTest); // Recréez le panneau de graphique avec les nouvelles données
        } else {
            wp = new WelshPowell(graphVol);
            nbCouleurs = wp.colorGraphWelshPowellVol(kMax);
            graphPanel = new GrapheToPanel(graphVol);
        }
    }

    /**
     * Initialiser le graphe de coloration en fonction de la fenêtre principale.
     *
     * @throws IOException En cas d'erreur d'entrée/sortie lors de la lecture
     * des données
     * @throws NegativeKMaxException Si la valeur de kMax est négative
     * @throws InvalidDataFormatException En cas de format de données invalide
     * @throws NodeOutOfBoundsException Si un nœud est hors limites
     * @throws InvalidHourException Si l'heure est invalide
     * @throws InvalidMinuteException Si la minute est invalide
     * @throws InvalidLineFormatException Si le format de ligne est invalide
     */
    public void initGrapheColo() throws IOException, NegativeKMaxException, InvalidDataFormatException, NodeOutOfBoundsException, InvalidHourException, InvalidMinuteException, InvalidLineFormatException {
        if (this.mainWindow.getFenId() == 0) {
            graphTest = mainWindow.getGrapheCharger(); // Initialiser un nouveau graphe de test
            nodeList = new NodeList(graphTest); // Initialiser la liste de nœuds
            taille = nodeList.taille();
            nbCouleurs = Dsatur.colorierDsaturGraphTest(nodeList, taille); // Colorier le graphe de test par DSATUR
            Dsatur.appliqueColor(nbCouleurs, nodeList); // Appliquer les couleurs
            graphPanel = new GrapheToPanel(graphTest); // Initialiser le panel de graphique avec le graphe de test
            grapheOuVol = 0; // Indiquer qu'il s'agit d'un graphe
        } else {
            listAeroport = mainWindow.getListeAeroport();
            listeVol = mainWindow.getListeVol();
            taille = listAeroport.tailleList();
            graphVol = new GraphePlus("Graphe-Aeroport"); // Initialiser un nouveau graphe de vol
            graphVol.mettreSommetVol(listeVol); // Mettre à jour les sommets du graphe de vol
            graphVol.mettreIntersection(listeVol, listAeroport, margeSecu); // Mettre à jour les intersections du graphe de vol
            nodeList = new NodeList(graphVol); // Initialiser la liste de nœuds
            nbCouleurs = Dsatur.colorierDsaturVolTest(nodeList, taille); // Colorier le graphe de vol par DSATUR
            Dsatur.appliqueColor(nbCouleurs, nodeList); // Appliquer les couleurs
            graphPanel = new GrapheToPanel(graphVol); // Initialiser le panel de graphique avec le graphe de vol
            grapheOuVol = 1; // Indiquer qu'il s'agit d'un graphe de vol
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
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

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

        jPanel2.setPreferredSize(new java.awt.Dimension(928, 499));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 943, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
        );

        jButton4.setBackground(new java.awt.Color(72, 72, 128));
        jButton4.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Appliquer les modifications");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dsatur", "W&P" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Algorithme");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(209, 209, 209)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 943, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4)
                            .addComponent(jLabel1))
                        .addGap(63, 63, 63))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75))))
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
        GrapheIntersection inter = null;
        try {
            inter = new GrapheIntersection(mainWindow);
        } catch (IOException | NegativeKMaxException | InvalidDataFormatException | NodeOutOfBoundsException | InvalidHourException | InvalidMinuteException | InvalidLineFormatException ex) {
            Logger.getLogger(GrapheColorie.class.getName()).log(Level.SEVERE, null, ex);
        }
        inter.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ParametreKmax setting = new ParametreKmax(this, rootPaneCheckingEnabled);
        setting.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        StatColorie Color = new StatColorie(this, rootPaneCheckingEnabled);
        Color.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try {
            if (jComboBox1.getSelectedItem().equals("Dsatur")) {
                coloDsatur();
            } else {
                coloWP();
            }

            jPanel2.removeAll(); // Retirer l'ancien panel
            jPanel2.add(graphPanel); // Ajouter le nouveau panel
            jPanel2.revalidate(); // Revalider le panel pour réorganiser les composants
            jPanel2.repaint(); // Repeindre le panel pour afficher les nouvelles modifications
        } catch (Exception ex) {
            Logger.getLogger(GrapheColorie.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton4ActionPerformed


    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
