package view;

import Exceptions.CodeAeroportException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;
import model.*;

/**
 * La classe {@code GrapheMapFrance} représente un panneau contenant une carte
 * interactive de la France, où les aéroports et les routes entre eux peuvent
 * être visualisés.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 * <li>{@code mapViewer} - Composant pour afficher la carte</li>
 * <li>{@code route} - Liste des positions géographiques représentant une
 * route</li>
 * <li>{@code routePainter} - Peintre pour dessiner les routes sur la carte</li>
 * <li>{@code painters} - Liste des peintres pour dessiner différents éléments
 * sur la carte</li>
 * <li>{@code waypointPainter} - Peintre pour dessiner les points de cheminement
 * (waypoints) sur la carte</li>
 * <li>{@code listeVol} - Liste des vols</li>
 * <li>{@code listeAeroport} - Liste des aéroports</li>
 * </ul>
 */
public final class GrapheMapFrance extends JPanel {

    private JXMapViewer mapViewer; // Composant pour afficher la carte
    private List<GeoPosition> route; // Liste des positions géographiques représentant une route
    private RoutePainter routePainter; // Peintre pour dessiner les routes sur la carte
    private List<Painter<JXMapViewer>> painters; // Liste des peintres pour dessiner différents éléments sur la carte
    private WaypointPainter<Waypoint> waypointPainter; // Peintre pour dessiner les points de cheminement (waypoints) sur la carte
    private ListeVol listeVol; // Liste des vols
    private final ListeAeroport listeAeroport; // Liste des aéroports

    /**
     * Constructeur de la classe {@code GrapheMapFrance}.
     *
     * @param newListeVol la liste des vols à afficher contenant les aéroports
     * qui seront affichés
     * @param newListeAeroport la base de données contenant les aéroports
     * @param heure l'heure de départ des vols à afficher (-1 pour ignorer)
     * @param niveau le niveau des vols à afficher (-1 pour ignorer)
     * @param codeAeroport codeAeroport si l'on veut afficher seulement les vols
     * partant de codeAeroport ("All" si l'on veut tous les afficher)
     * @throws Exceptions.CodeAeroportException Si codeAeroport n'existe pas de
     * la liste des aéroports
     */
    public GrapheMapFrance(ListeVol newListeVol, ListeAeroport newListeAeroport, int heure, int niveau, String codeAeroport) throws CodeAeroportException {
        this.listeVol = newListeVol;
        this.listeAeroport = newListeAeroport;
        initializeComponents();
        configureMap();
        configureListeners();
        addWaypointsAndRoutes(heure, niveau, codeAeroport);
        configureLayout();
    }

    //-------------------------------------------------------Getter-----------------------------------------------------------------
    /**
     * Obtient le composant de la carte.
     *
     * @return le composant de la carte
     */
    public JPanel getMap() {
        return mapViewer;
    }

    //-------------------------------------------------------Setter-----------------------------------------------------------------
    public void setListeVol(ListeVol listeVol) {
        this.listeVol = listeVol;
    }

    //-------------------------------------------------------Méthodes-----------------------------------------------------------------
    /**
     * Initialise les composants de base de la carte.
     */
    private void initializeComponents() {
        mapViewer = new JXMapViewer();
        route = new ArrayList<>();
        routePainter = new RoutePainter(route);
        painters = new ArrayList<>();
        waypointPainter = new WaypointPainter<>();
    }

    /**
     * Configure les paramètres de la carte.
     */
    /**
     * Configure la carte en initialisant le TileFactory et en réglant les
     * paramètres de zoom et de position.
     */
    private void configureMap() {
        // Crée une instance de TileFactoryInfo pour utiliser les tuiles OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        // Assigne la fabrique de tuiles à la carte
        mapViewer.setTileFactory(tileFactory);

        // Définit le niveau de zoom initial de la carte
        mapViewer.setZoom(13);

        // Centre la carte sur la position géographique donnée (ici, centre de la France)
        mapViewer.setAddressLocation(new GeoPosition(46.5, 2));
    }

    /**
     * Configure les écouteurs pour les actions de la souris et du clavier.
     */
    private void configureListeners() {
        // Crée un écouteur pour le panoramique de la carte via la souris
        MouseInputListener mouseListener = new PanMouseInputListener(mapViewer);

        // Ajoute les écouteurs de souris à la carte pour la manipulation
        mapViewer.addMouseListener(mouseListener);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        mapViewer.addMouseMotionListener(mouseListener);
    }

    /**
     * Ajoute les waypoints et les routes à la carte en fonction des paramètres
     * fournis.
     *
     * @param heure l'heure de départ des vols à afficher (-1 pour ignorer)
     * @param niveau le niveau des vols à afficher (-1 pour ignorer)
     * @param codeAeroport codeAeroport si l'on veut afficher seulement les vols
     * partant de codeAeroport ("All" si l'on veut tous les afficher)
     * @throws CodeAeroportException Si codeAeroport n'existe pas dans la liste
     * des aéroports
     */
    public void addWaypointsAndRoutes(int heure, int niveau, String codeAeroport) throws CodeAeroportException {
        try {
            // Ajoute les waypoints à la carte selon les critères spécifiés
            addWaypoints(listeVol, listeAeroport, heure, niveau, codeAeroport);
        } catch (CodeAeroportException ex) {
            // Affiche une boîte de dialogue en cas d'erreur avec le code d'aéroport
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        // Ajoute les peintres des waypoints et des routes à la liste des peintres
        painters.add(waypointPainter);
        painters.add(routePainter);

        // Définit le peintre de superposition pour la carte avec les peintres combinés
        mapViewer.setOverlayPainter(new CompoundPainter<>(painters));
    }

    /**
     * Configure la disposition des composants du panneau.
     */
    private void configureLayout() {
        // Utilise un layout BoxLayout pour organiser les composants du panneau
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        // Définit la taille préférée du composant mapViewer
        mapViewer.setPreferredSize(new Dimension(950, 500));

        // Ajoute le composant mapViewer au panneau
        add(mapViewer);
    }

    /**
     * Ajoute les waypoints et les routes à partir des listes de vols et
     * d'aéroports fournies.
     *
     * @param listeVol la liste des vols
     * @param listeAeroport la liste des aéroports
     * @param heure l'heure de départ des vols à afficher (-1 pour ignorer)
     * @param niveau le niveau des vols à afficher (-1 pour ignorer)
     * @param codeAeroport codeAeroport si l'on veut afficher seulement les vols
     * partant de codeAeroport ("All" si l'on veut tous les afficher)
     * @throws CodeAeroportException Si codeAeroport n'existe pas dans la liste
     * des aéroports
     */
    public void addWaypoints(ListeVol listeVol, ListeAeroport listeAeroport, int heure, int niveau, String codeAeroport) throws CodeAeroportException {
        // Crée un ensemble pour stocker les waypoints
        Set<InfoWaypoint> waypointSet = new HashSet<>();
        Aeroport aeroportD, aeroportA;
        Vol vol;
        int taille = listeVol.tailleList();

        for (int i = 0; i < taille; i++) {
            vol = listeVol.accesVol(i);

            // Filtrer les vols en fonction de l'heure de départ si l'heure est spécifiée
            if (heure != -1 && vol.getHeureDepartHeure() != heure) {
                continue;
            }

            // Filtrer les vols en fonction du niveau si le niveau est spécifié
            if (niveau != 0 && vol.getNiveau() != niveau) {
                continue;
            }

            // Filtrer les vols en fonction du code de l'aéroport si spécifié
            if (!codeAeroport.equals("All") && !vol.getDepart().equals(codeAeroport)) {
                continue;
            }

            // Récupère les informations des aéroports de départ et d'arrivée
            aeroportD = listeAeroport.accesAeroportCode(vol.getDepart());
            aeroportA = listeAeroport.accesAeroportCode(vol.getArrivee());

            if (aeroportD != null && aeroportA != null) {
                // Crée des positions géographiques pour les aéroports
                GeoPosition positionAeroportD = new GeoPosition(aeroportD.getLatitude(), aeroportD.getLongitude());
                GeoPosition positionAeroportA = new GeoPosition(aeroportA.getLatitude(), aeroportA.getLongitude());

                // Ajoute les positions des aéroports à la route
                route.add(positionAeroportD); // Ajout du point de départ
                route.add(positionAeroportA); // Ajout du point d'arrivée

                // Crée ou trouve des waypoints pour les aéroports
                InfoWaypoint waypointD = findOrCreateWaypoint(waypointSet, positionAeroportD, aeroportD.getNom());
                InfoWaypoint waypointA = findOrCreateWaypoint(waypointSet, positionAeroportA, aeroportA.getNom());

                // Ajoute le vol aux waypoints correspondants
                addVolToWaypoint(waypointD, vol, waypointSet);
                addVolToWaypoint(waypointA, vol, waypointSet);
            } else {
                // Lève une exception si l'un des aéroports n'existe pas
                if (aeroportD == null) {
                    throw new CodeAeroportException("L'aéroport de départ est inexistant : " + vol.getDepart());
                } else {
                    throw new CodeAeroportException("L'aéroport d'arrivée est inexistant : " + vol.getArrivee());
                }
            }
        }

        // Définit les waypoints à peindre sur la carte
        waypointPainter.setWaypoints(waypointSet);

        // Ajoute les écouteurs de souris pour interagir avec les waypoints
        addMouseListenerToMap(waypointSet);
    }

    /**
     * Ajoute un vol à un waypoint et met à jour le set de waypoints.
     *
     * @param waypoint le waypoint à mettre à jour
     * @param vol le vol à ajouter
     * @param waypointSet le set de waypoints à mettre à jour
     */
    private void addVolToWaypoint(InfoWaypoint waypoint, Vol vol, Set<InfoWaypoint> waypointSet) {
        waypoint.addVol(vol);
        waypointSet.add(waypoint);
    }

    /**
     * Trouve ou crée un waypoint pour une position géographique donnée et un
     * nom d'aéroport.
     *
     * @param waypointSet le set de waypoints à vérifier
     * @param position la position géographique du waypoint
     * @param aeroportName le nom de l'aéroport
     * @return le waypoint trouvé ou créé
     */
    private InfoWaypoint findOrCreateWaypoint(Set<InfoWaypoint> waypointSet, GeoPosition position, String aeroportName) {
        for (InfoWaypoint wp : waypointSet) {
            if (wp.getPosition().equals(position)) {
                return wp;
            }
        }
        return new InfoWaypoint(position, aeroportName);
    }

    /**
     * Ajoute un écouteur de souris à la carte pour afficher les informations
     * des waypoints.
     *
     * @param waypointSet le set de waypoints à vérifier
     */
    private void addMouseListenerToMap(Set<InfoWaypoint> waypointSet) {
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    JXMapViewer map = (JXMapViewer) e.getSource();
                    Point2D worldPos = e.getPoint();
                    GeoPosition clickPosition = mapViewer.convertPointToGeoPosition(e.getPoint());

                    for (InfoWaypoint waypoint : waypointSet) {
                        GeoPosition waypointPosition = waypoint.getPosition();
                        if (isCloseEnough(clickPosition, waypointPosition)) { // Vérifier si le clic est proche du waypoint
                            StringBuilder message = new StringBuilder(waypoint.getInfo());
                            message.append("\nVols liés :\n");
                            for (Vol vol : waypoint.getVols()) {
                                message.append(vol.toString()).append("\n");
                            }
                            JOptionPane.showMessageDialog(mapViewer, message.toString(), "Informations sur l'aéroport", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * Vérifie si deux positions géographiques sont suffisamment proches l'une
     * de l'autre.
     *
     * @param p1 la première position
     * @param p2 la deuxième position
     * @return true si les positions sont proches, sinon false
     */
    private boolean isCloseEnough(GeoPosition p1, GeoPosition p2) {
        double intervalle = 0.05; // Précision que l'on veut
        return Math.abs(p1.getLatitude() - p2.getLatitude()) < intervalle
                && Math.abs(p1.getLongitude() - p2.getLongitude()) < intervalle;
    }

    /**
     * Efface les routes et les waypoints de la carte.
     */
    public void clearMap() {
        route.clear();
        waypointPainter.setWaypoints(new HashSet<>());
        repaint();
    }

    /**
     * Ajoute de nouveaux waypoints et routes à la carte.
     *
     * @param newHeure la nouvelle heure de départ des vols à afficher (-1 pour
     * ignorer)
     * @param newNiveau le nouveau niveau des vols à afficher (-1 pour ignorer)
     * @param newCodeAeroport codeAeroport si l'on veut afficher seulement les
     * vols partant de codeAeroport ("All" si l'on veut tous les afficher).
     * @throws Exceptions.CodeAeroportException Si codeAeroport n'existe pas de
     * la liste des aéroports
     */
    public void addNewWaypointsAndRoutes(int newHeure, int newNiveau, String newCodeAeroport) throws CodeAeroportException {
        clearMap(); // Effacer les routes et les points existants

        // Ajouter de nouveaux waypoints et routes avec les nouvelles données
        addWaypoints(listeVol, listeAeroport, newHeure, newNiveau, newCodeAeroport);

        mapViewer.revalidate(); // Recalculer la disposition des composants parents
        mapViewer.repaint(); // Forcer le rafraîchissement de l'affichage du composant parent
    }

    /**
     * Classe interne {@code RoutePainter} qui dessine les routes entre les
     * waypoints.
     */
    private static class RoutePainter implements Painter<JXMapViewer> {

        private final List<GeoPosition> track;

        /**
         * Constructeur de la classe {@code RoutePainter}.
         *
         * @param track la liste des positions géographiques à dessiner
         */
        public RoutePainter(List<GeoPosition> track) {
            this.track = track;
        }

        @Override
        public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
            g = (Graphics2D) g.create();
            int compteur = 0;

            // Convertit les coordonnées géographiques en pixels du bitmap mondial
            org.jxmapviewer.viewer.TileFactory tf = map.getTileFactory();
            Rectangle rect = map.getViewportBounds();
            g.translate(-rect.getX(), -rect.getY());

            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke((float) 1.3));

            Point2D previous = null;
            for (GeoPosition gp : track) {
                Point2D pt = tf.geoToPixel(gp, map.getZoom());

                // Trace une ligne seulement entre chaque groupe de deux points consécutifs
                if (previous != null && compteur % 2 == 1) {
                    g.drawLine((int) previous.getX(), (int) previous.getY(), (int) pt.getX(), (int) pt.getY());
                    drawArrow(g, (int) previous.getX(), (int) previous.getY(), (int) pt.getX(), (int) pt.getY());
                }
                previous = pt;
                compteur++;
            }

            g.dispose();
        }

        /**
         * Méthode pour dessiner une flèche.
         *
         * @param g le contexte graphique
         * @param x1 la coordonnée x de la première extrémité de la ligne
         * @param y1 la coordonnée y de la première extrémité de la ligne
         * @param x2 la coordonnée x de la deuxième extrémité de la ligne
         * @param y2 la coordonnée y de la deuxième extrémité de la ligne
         */
        private void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
            double angle = Math.atan2(y2 - y1, x2 - x1);
            int arrowLength = 10; // Longueur de la flèche
            int arrowWidth = 10; // Largeur de la flèche

            int x3 = (int) (x2 - arrowLength * Math.cos(angle + Math.PI / 6));
            int y3 = (int) (y2 - arrowLength * Math.sin(angle + Math.PI / 6));
            int x4 = (int) (x2 - arrowLength * Math.cos(angle - Math.PI / 6));
            int y4 = (int) (y2 - arrowLength * Math.sin(angle - Math.PI / 6));

            g.drawLine(x2, y2, x3, y3);
            g.drawLine(x2, y2, x4, y4);
        }
    }

}
