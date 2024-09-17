/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ihm;

import Exceptions.InvalidDataFormatException;
import Exceptions.InvalidHourException;
import Exceptions.InvalidLineFormatException;
import Exceptions.InvalidMinuteException;
import Exceptions.InvalidOrientationException;
import Exceptions.NegativeKMaxException;
import Exceptions.NodeOutOfBoundsException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.ListeAeroport;
import model.ListeVol;
import view.ChargerGraphe;

/**
 * FenetrePrincipale est la classe principale de l'interface graphique du
 * gestionnaire de vols. Elle permet de charger des fichiers de graphes, de vols
 * et d'aéroports. Cette fenêtre propose plusieurs options d'interaction avec
 * l'utilisateur.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 * <li>{@code fileWindow} - Fenêtre de chargement de la liste de vols</li>
 * <li>{@code nomFichier} - Nom du fichier sélectionné (Celui provenant d'un
 * fichier de vol ou de graphe)</li>
 * <li>{@code nomFichierAeroport} - Nom du fichier d'aéroports sélectionné</li>
 * <li>{@code filePathVol} - Chemin du fichier de vols</li>
 * <li>{@code filePathGraphe} - Chemin du fichier de graphe</li>
 * <li>{@code filePathAeroport} - Chemin du fichier d'aéroports</li>
 * <li>{@code listeAeroport} - Liste des aéroports</li>
 * <li>{@code listeVol} - Liste des vols</li>
 * <li>{@code chargerGraphe} - Graphe de type ChargerGraphe</li>
 * <li>{@code ligneIgnorer} - Nombre de ligne non pris en compte dans le fichier
 * des aéroports dû à une erreur </li>
 * <li>{@code fenId} - Identifiant de la fenêtre</li>
 * </ul>
 *
 * @author Mejdi,Zakary et Amadis
 */
public class FenetrePrincipale extends javax.swing.JFrame {

    private final ChargerListeVol fileWindow; //Fenêtre de chargement de la liste de vols

    private String nomFichier; //Nom du fichier sélectionné (d'un fichier de vol ou de graphe)
    private String nomFichierAeroport; //Nom du fichier d'aéroports sélectionné

    private String filePathVol; //Chemin du fichier de vols
    private String filePathGraphe;//Chemin du fichier du graphe
    private String filePathAeroport; //Chemin du fichier des aéroports

    private static ListeAeroport listeAeroport;//Liste des aéroports
    private ListeVol listeVol; //Liste des vols
    private ChargerGraphe chargerGraphe; //Graphe dans le cas où l'user charge un graphe déjà fait

    private int ligneIgnorer;//Nombre de ligne non pris en compte dans le fichier des aéroports,vols ou graphe dû à une erreur 
    private int fenId;//Identifiant de la fenêtre

    /**
     * Crée une nouvelle instance de FenetrePrincipale.
     */
    public FenetrePrincipale() {

        initComponents();
        setResizable(false);
        //Initialisation des objets
        listeAeroport = new ListeAeroport();
        listeVol = new ListeVol();
        chargerGraphe = new ChargerGraphe();
        fileWindow = new ChargerListeVol(this);
    }

    //-----------------------------------------------------------------------Getter--------------------------------------------------------------------
    /**
     * Obtient le nom du fichier sélectionné.
     *
     * @return Le nom du fichier sélectionné.
     */
    public String getNomFichier() {
        return nomFichier;
    }

    /**
     * Obtient le chemin du fichier de graphe sélectionné.
     *
     * @return Le chemin du fichier de graphe.
     */
    public String getFilePathGraphe() {
        return filePathGraphe;
    }

    /**
     * Obtient le chemin du fichier de vols sélectionné.
     *
     * @return Le chemin du fichier de vols.
     */
    public String getPathVol() {
        return filePathVol;
    }

    /**
     * Obtient l'identifiant de la fenêtre.
     *
     * @return L'identifiant de la fenêtre.
     */
    public int getFenId() {
        return fenId;
    }

    /**
     * Obtient la liste des aéroports.
     *
     * @return La liste des aéroports.
     */
    public ListeAeroport getListeAeroport() {
        return listeAeroport;
    }

    /**
     * Obtient le graphe de type ChargerGraphe (type graph test)
     *
     * @return Le graphe
     */
    public ChargerGraphe getGrapheCharger() {
        return chargerGraphe;
    }

    /**
     * Obtient la liste des vols.
     *
     * @return La liste des vols.
     */
    public ListeVol getListeVol() {
        return listeVol;
    }

    /**
     * Obtient le nom du fichier d'aéroports sélectionné.
     *
     * @return Le nom du fichier d'aéroports.
     */
    public String getNomFichierAeroport() {
        return nomFichierAeroport;
    }

    /**
     * Obtient le chemin du fichier d'aéroports sélectionné.
     *
     * @return Le chemin du fichier d'aéroports.
     */
    public String getFilePathAeroport() {
        return filePathAeroport;
    }

    //-----------------------------------------------------------------------Setter--------------------------------------------------------------------
    /**
     * Modifie la liste des vols
     *
     * @param listeVol La liste des vols à modifier.
     */
    public void setListeVol(ListeVol listeVol) {
        this.listeVol = listeVol;
    }

    /**
     * Charge la liste des aéroports à partir du fichier filePathAeroport.
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la
     * lecture du fichier.
     * @throws InvalidOrientationException Si l'orientation des données dans le
     * fichier est invalide.
     * @throws InvalidLineFormatException Si le format d'une ligne dans le
     * fichier est invalide.
     * @throws InvalidDataFormatException Si le format des données dans le
     * fichier est invalide.
     */
    public void chargerListeAeroport() throws IOException, InvalidOrientationException, InvalidLineFormatException, InvalidDataFormatException {
        ligneIgnorer = listeAeroport.extractionAeroport(filePathAeroport);
        if (ligneIgnorer > 0) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement de " + ligneIgnorer + " aéroport", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Charge la liste des vols à partir du fichier filePathVol..
     *
     * @throws InvalidLineFormatException Si le format d'une ligne dans le
     * fichier est invalide.
     * @throws InvalidHourException Si une heure dans le fichier est invalide.
     * @throws InvalidMinuteException Si une minute dans le fichier est
     * invalide.
     */
    public void chargerListeVol() throws InvalidLineFormatException, InvalidHourException, InvalidMinuteException {
        ligneIgnorer = listeVol.extractionVol(filePathVol);
        if (ligneIgnorer > 0) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement de " + ligneIgnorer + " vols", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Charge le graphe à partir du fichier filePathGraphe..
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la
     * lecture du fichier.
     * @throws NegativeKMaxException Si la valeur de kMax est négative.
     * @throws InvalidDataFormatException Si le format des données dans le
     * fichier est invalide.
     * @throws NodeOutOfBoundsException Si un nœud du graphe est hors des
     * limites autorisées.
     */
    public void chargerGraphe() throws IOException, NegativeKMaxException, InvalidDataFormatException, NodeOutOfBoundsException {
        chargerGraphe = new ChargerGraphe();
        ligneIgnorer = chargerGraphe.chargerGraph(filePathGraphe);
        if (ligneIgnorer > 0) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement de " + ligneIgnorer + " lignes", "Erreur", JOptionPane.ERROR_MESSAGE);
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

        jFileChooser1 = new javax.swing.JFileChooser();
        MainPane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        ButtonPanel = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        MainPane.setBackground(new java.awt.Color(18, 18, 57));

        jLabel1.setFont(new java.awt.Font("Impact", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bienvenue sur le gestionnaire de vols");

        ButtonPanel.setBackground(new java.awt.Color(18, 18, 57));
        ButtonPanel.setLayout(new java.awt.GridLayout(1, 0, 30, 0));

        jButton2.setBackground(new java.awt.Color(72, 72, 128));
        jButton2.setFont(new java.awt.Font("Impact", 0, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Charger un graphe");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        ButtonPanel.add(jButton2);

        jButton3.setBackground(new java.awt.Color(72, 72, 128));
        jButton3.setFont(new java.awt.Font("Impact", 0, 15)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Charger une liste de vols");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        ButtonPanel.add(jButton3);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/Image/avion3.png"))); // NOI18N

        jButton4.setBackground(new java.awt.Color(72, 72, 128));
        jButton4.setFont(new java.awt.Font("Impact", 0, 15)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Charger une liste d'aéroport");
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MainPaneLayout = new javax.swing.GroupLayout(MainPane);
        MainPane.setLayout(MainPaneLayout);
        MainPaneLayout.setHorizontalGroup(
            MainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPaneLayout.createSequentialGroup()
                .addGap(0, 268, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(250, 250, 250)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(MainPaneLayout.createSequentialGroup()
                .addGap(299, 299, 299)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(MainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPaneLayout.createSequentialGroup()
                        .addComponent(ButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(333, 333, 333))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPaneLayout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(539, 539, 539))))
        );
        MainPaneLayout.setVerticalGroup(
            MainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPaneLayout.createSequentialGroup()
                .addGroup(MainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainPaneLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(MainPaneLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(ButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        String defaultPath = "D:\\APPLICATION SAE IHM\\SAE IHM\\Data Test"; //Pour la démo pour aller plus vite
        File defaultDirectory = new File(defaultPath);
        fileChooser.setCurrentDirectory(defaultDirectory);
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            nomFichier = selectedFile.getName();
            filePathGraphe = selectedFile.getAbsolutePath();
            try {
                chargerGraphe();
            } catch (IOException | NegativeKMaxException | InvalidDataFormatException | NodeOutOfBoundsException ex) {
                Logger.getLogger(FenetrePrincipale.class.getName()).log(Level.SEVERE, null, ex);
            }
            fenId = 0;
            GrapheIntersection inter = null;
            try {
                inter = new GrapheIntersection(this);
            } catch (IllegalArgumentException | IOException | NegativeKMaxException | InvalidDataFormatException | NodeOutOfBoundsException | InvalidHourException | InvalidMinuteException | InvalidLineFormatException ex) {
                Logger.getLogger(FenetrePrincipale.class.getName()).log(Level.SEVERE, null, ex);
            }
            inter.setVisible(true);
            this.dispose();
        } else {
            System.out.println("File selection cancelled.");
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (listeAeroport.tailleList() == 0) {
            JOptionPane.showMessageDialog(this, "Erreur : La liste d'aéroport n'a pas été charger", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        String defaultPath = "D:\\APPLICATION SAE IHM\\SAE IHM\\Data Test"; //Pour la démo pour aller plus vite
        File defaultDirectory = new File(defaultPath);
        fileChooser.setCurrentDirectory(defaultDirectory);
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            nomFichier = selectedFile.getName();
            filePathVol = selectedFile.getAbsolutePath();
            try {
                chargerListeVol();
            } catch (InvalidHourException | InvalidMinuteException | InvalidLineFormatException ex) {
                Logger.getLogger(FenetrePrincipale.class.getName()).log(Level.SEVERE, null, ex);
            }
            fenId = 1;
            fileWindow.setVisible(true);
            fileWindow.updateLabel();
            this.dispose();
        } else {
            System.out.println("File selection cancelled.");
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        String defaultPath = "D:\\APPLICATION SAE IHM\\SAE IHM\\Data Test"; //Pour la démo pour aller plus vite
        File defaultDirectory = new File(defaultPath);
        fileChooser.setCurrentDirectory(defaultDirectory);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            nomFichierAeroport = selectedFile.getName();
            filePathAeroport = selectedFile.getAbsolutePath();
            try {
                chargerListeAeroport();
                JOptionPane.showMessageDialog(null, "L'aéroport a bien été chargé");
            } catch (IOException | InvalidOrientationException | InvalidLineFormatException | InvalidDataFormatException ex) {
                Logger.getLogger(FenetrePrincipale.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ButtonPanel;
    private javax.swing.JPanel MainPane;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

}
