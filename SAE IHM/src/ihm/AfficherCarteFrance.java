/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ihm;


import Exceptions.CodeAeroportException;
import Exceptions.InvalidHourException;
import Exceptions.InvalidLineFormatException;
import Exceptions.InvalidMinuteException;
import coloration.Dsatur;
import coloration.NodeList;
import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.ListeAeroport;
import model.ListeVol;
import view.GrapheMapFrance;
import view.GraphePlus;

/**
 * La classe AfficherCarteFrance représente une fenêtre de l'application qui affiche une carte de la France avec les vols et les aéroports.
 * Elle permet de visualiser et d'appliquer des modifications aux données des vols et des aéroports.
 * Les fonctionnalités incluent la gestion des paramètres de vol, l'application d'un algorithme de coloration pour optimiser les vols et la mise à jour de la carte affichée.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 *   <li>{@code mainWindow} - La fenêtre principale de l'application</li>
 *   <li>{@code niveauVol} - Niveau des vols (par défaut 0)</li>
 *   <li>{@code heureVol} - Heure des vols (par défaut -1, signifiant aucune heure spécifique)</li>
 *   <li>{@code mapFrance} - Carte de la France représentant le graphe des vols</li>
 *   <li>{@code listeAeroport} - Liste des aéroports</li>
 *   <li>{@code listeVol} - Liste des vols</li>
 *   <li>{@code kMax} - Le paramètre kMax pour les vols</li>
 *   <li>{@code margeSecu} - La marge de sécurité en vols</li>
 *   <li>{@code codeAeroport} - Code de l'aéroport à afficher (par défaut "All" : pour tous les sélectionner)</li>
 * </ul>
 * 
 * @author Mejdi,Zakary et Amadis
 */
public class AfficherCarteFrance extends javax.swing.JFrame {

    private final FenetrePrincipale mainWindow;//La fenêtre principale de l'application
    private int niveauVol = 0; //Niveau des vols (par défaut 0)
    private int heureVol = -1; //Heure des vols (par défaut -1, signifiant aucune heure spécifique)
    private final GrapheMapFrance mapFrance; //Carte de la France représentant le graphe des vols
    private final ListeAeroport listeAeroport; //Liste des aéroports
    private final ListeVol listeVol;//Liste des vols
    private int kMax;//Le paramètre kMax pour la coloration soit le nombres d'altitudes
    private int margeSecu; //La marge de sécurité en vols
    private String codeAeroport = "All"; //Code de l'aéroport à afficher (par défaut "All" : pour tous les sélectionner)
    

    /**
     * Constructeur de la classe AfficherCarteFrance.
     * Initialise la fenêtre avec les paramètres principaux et charge les données des vols et des aéroports.
     * 
     * @param mainWindow La fenêtre principale de l'application.
     * @throws InvalidHourException Si l'heure d'un vol est invalide.
     * @throws InvalidMinuteException Si la minute d'un vol est invalide.
     * @throws InvalidLineFormatException Si le format d'une ligne de données de vol est invalide.
     * @throws Exceptions.CodeAeroportException Si le code de l'aéroport est inexistant de la liste d'aéroport
     */
    public AfficherCarteFrance(FenetrePrincipale mainWindow) throws InvalidHourException, InvalidMinuteException, InvalidLineFormatException, CodeAeroportException{
        listeAeroport = mainWindow.getListeAeroport();
        listeVol = mainWindow.getListeVol();
        this.mainWindow = mainWindow;
        
        
        
        mapFrance = new GrapheMapFrance(listeVol, listeAeroport, heureVol, niveauVol, codeAeroport);
        kMax = 4; //Initialement kmax est mis à 4
        margeSecu = 15; //Initalement la marge de sécurité de deux avions potentiellement en collisions est de 15 minutes comme le figure l'énoncé
        initComponents();
        
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(mapFrance.getMap(), BorderLayout.CENTER);
        jPanel2.revalidate();
        jPanel2.repaint();
        setResizable(false);

    }
    //-----------------------------------------------------------------------Getter--------------------------------------------------------------------
    
    
    
    /**
     * Retourne l'heure actuelle des vols.
     *
     * @return L'heure des vols.
     */
    public int getHeure_vol() {
        return heureVol;
    }

    /**
     * Retourne le niveau actuel des vols.
     *
     * @return Le niveau des vols.
     */
    public int getNiveau_vol() {
        return niveauVol;
    }

    /**
     * Retourne la valeur maximale de k pour l'algorithme Dsatur.
     *
     * @return La valeur de kMax.
     */
    public int getkMax() {
        return kMax;
    }

    /**
     * Retourne la liste des aéroports.
     *
     * @return La liste des aéroports.
     */
    public ListeAeroport getListeAeroport() {
        return listeAeroport;
    }

    /**
     * Retourne le code de l'aéroport sélectionné.
     *
     * @return Le code de l'aéroport.
     */
    public String getCodeAeroport() {
        return codeAeroport;
    }

    /**
     * Retourne la marge de sécurité actuelle.
     *
     * @return La marge de sécurité.
     */
    public int getMargeSecu() {
        return margeSecu;
    }

    /**
     * Retourne le panneau utilisé pour afficher la carte.
     *
     * @return Le panneau de la carte.
     */
    public JPanel getjPanel2() {
        return jPanel2;
    }
    
    //-----------------------------------------------------------------------Setter--------------------------------------------------------------------

    /**
     * Définit le panneau utilisé pour afficher la carte.
     *
     * @param jPanel2 Le panneau de la carte.
     */
    public void setjPanel2(JPanel jPanel2) {
        this.jPanel2 = jPanel2;
    }

    /**
     * Définit le niveau du vols.
     *
     * @param niveau_vol Le nouveau niveau des vols.
     */
    public void setNiveauVol(int niveau_vol) {
        this.niveauVol = niveau_vol;
    }

    /**
     * Définit l'heure des vols.
     *
     * @param heure_vol La nouvelle heure des vols.
     */
    public void setHeureVol(int heure_vol) {
        this.heureVol = heure_vol;
    }

    /**
     * Définit la valeur maximale de k pour l'algorithme de coloration au choix.
     *
     * @param kMax La nouvelle valeur de kMax.
     */
    public void setkMax(int kMax) {
        this.kMax = kMax;
    }

    /**
     * Définit la marge de sécurité.
     *
     * @param margeSecu La nouvelle marge de sécurité.
     */
    public void setMargeSecu(int margeSecu) {
        this.margeSecu = margeSecu;
    }

    /**
     * Définit le code de l'aéroport sélectionné.
     *
     * @param codeAeroport Le nouveau code de l'aéroport.
     */
    public void setCodeAeroport(String codeAeroport) {
        this.codeAeroport = codeAeroport;
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
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(18, 18, 57));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/Image/Parametre2.png"))); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(39, 39));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/Image/retour.png"))); // NOI18N
        jButton7.setPreferredSize(new java.awt.Dimension(39, 39));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 947, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
        );

        jButton2.setBackground(new java.awt.Color(72, 72, 128));
        jButton2.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Appliquer les modifications");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                .addGap(102, 102, 102)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 496, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(488, 488, 488))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(73, 73, 73))
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
        ChargerListeVol chargerListe = new ChargerListeVol(mainWindow);
        chargerListe.updateLabel();
        chargerListe.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ParametreMapFrance setting = new ParametreMapFrance(this, rootPaneCheckingEnabled);
        setting.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (niveauVol != 0) {
            GraphePlus graphe = new GraphePlus("Graphe des vols");
            graphe.mettreSommetVol(listeVol);
            graphe.mettreIntersection(listeVol, listeAeroport, margeSecu);
            NodeList nodeList = new NodeList(graphe);
            Dsatur.colorierDsaturVolTest(nodeList, kMax);
            Dsatur.appliqueAltitudetoVol(nodeList, listeVol);
        }
        
        try {
            mapFrance.addNewWaypointsAndRoutes(heureVol, niveauVol, codeAeroport);
        } catch (CodeAeroportException ex) {
            Logger.getLogger(AfficherCarteFrance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

}
