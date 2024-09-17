package view;

import javax.swing.table.AbstractTableModel;
import model.ListeVol;
import model.Vol;

/**
 * Modèle de tableau abstrait pour afficher une liste de vols dans une JTable.
 * Ce modèle étend AbstractTableModel et gère les opérations CRUD (Create, Read, Update, Delete) sur la liste de vols.
 * <p>
 * Les variables d'instance de cette classe sont les suivantes :
 * </p>
 * <ul>
 *   <li>{@code columnNames} - Contient les noms des colonnes</li>
 *   <li>{@code vols} - Liste de vols associée au modèle</li>
 * </ul>
 */
public class MyAbstractTable extends AbstractTableModel {
    
    private final String[] columnNames = {"NUMERO", "DEPART", "ARRIVÉE", "HEURE DU DÉPART", "MINUTE DU DÉPART", "DURÉE"};
    private final ListeVol listVol;  // Liste de vols associée au modèle
    
    /**
     * Constructeur du modèle de tableau abstrait.
     * @param listeVol Liste de vols à afficher dans le tableau
     */
    public MyAbstractTable(ListeVol listeVol) {
        this.listVol = listeVol;
    }
    
    /**
     * Ajoute un vol à la liste et met à jour le tableau.
     * @param vol Vol à ajouter
     */
    public void addVol(Vol vol) {
        boolean result = listVol.ajVol(vol);
        if (result) {
            fireTableRowsInserted(listVol.getListeVol().size() - 1, listVol.getListeVol().size() - 1);
        }
    }
    
    /**
     * Supprime un vol de la liste et met à jour le tableau.
     * @param vol Vol à supprimer
     */
    public void deleteVol(Vol vol) {
        boolean result = listVol.supVol(vol);
        if (result) {
            fireTableRowsDeleted(listVol.getListeVol().size() - 1, listVol.getListeVol().size() - 1);
        }
    }
    
    /**
     * Retourne le vol à l'indice spécifié dans la liste.
     * @param numero Numéro du vol à rechercher
     * @return Le vol correspondant au numéro, ou null s'il n'est pas trouvé
     */
    public Vol getVol(String numero) {
        for (int i = 0; i < listVol.getListeVol().size(); i++) {
            Vol vol = listVol.accesVol(i);
            if (vol.getNumero().equals(numero)) {
                return vol;
            }
        }
        return null;
    }
    
    @Override
    public int getRowCount() {
        return listVol.getListeVol().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Vol vol = listVol.getListeVol().get(rowIndex);
        switch (columnIndex) {
            case 0:
                vol.setNumero((String) aValue);
                break;
            case 1:
                vol.setDepart((String) aValue);
                break;
            case 2:
                vol.setArrivee((String) aValue);
                break;
            case 3:
                vol.setHeureDepartHeure((int) aValue);
                break;
            case 4:
                vol.setHeureDepartMinute((int) aValue);
                break;
            case 5:
                vol.setDuree((int) aValue);
                break;
            default:
                throw new IllegalArgumentException("Index de colonne invalide");
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vol vol = listVol.accesVol(rowIndex);
        switch (columnIndex) {
            case 0:
                return vol.getNumero();
            case 1:
                return vol.getDepart();
            case 2:
                return vol.getArrivee();
            case 3:
                return vol.getHeureDepartHeure();
            case 4:
                return vol.getHeureDepartMinute();
            case 5:
                return vol.getDuree();
            default:
                return null;
        }
    }
    
    /**
     * Retourne le vol à l'indice spécifié dans la liste.
     * @param rowIndex Indice de la ligne du tableau
     * @return Le vol à l'indice spécifié
     */
    public Vol getVol(int rowIndex) {
        return listVol.accesVol(rowIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
