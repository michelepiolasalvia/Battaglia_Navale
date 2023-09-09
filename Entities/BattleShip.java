package battaglia_navale.Entities;

import java.util.Arrays;

/**
 * Classe Battleship.
 * Utilizzata per rappresentare la griglia di gioco.
 * <i>&#60;Entity&#62;</i>
 */
public final class BattleShip {

    /** Costante che indica la dimensione di default della griglia. */
    private static final int DEFAULT_DIM = 10;

    /** Costante che indica la dimensione large della griglia. */
    private static final int LARGE_DIM = 18;
    /** Costante che indica la dimensione extralarge della griglia. */
    private static final int EXTRA_DIM = 26;

    /** intero che indica il numero di righe della griglia. */
    private int righe = DEFAULT_DIM;
    /** intero che indica il numero di colonne della griglia. */
    private int colonne = DEFAULT_DIM;

    /** Array di interi costante che indica la lunghezza dei tipi di nave. */
    private final int[] lunghezzaNavi = {2, 2, 2, 2, 3, 3, 3, 4, 4, 5};

    /** Matrice di interi utilizzata per rappresentare la griglia di gioco.*/
    private char[][] tabellaPos;

    /**
     * Funzione getRighe.
     * @return Restituisce il numero di righe della griglia
     */
    public int getRighe() {
        return righe;
    }

    /**
     * Funzione setRighe.
     * @param nuoveRighe nuovo numero di righe
     */
    public void setRighe(final int nuoveRighe) {
        this.righe = nuoveRighe;
    }

    /**
     * Funzione getColonne.
     * @return Restituisce il numero di colonne della griglia
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Funzione setColonne.
     * @param nuoveColonne nuovo numero di colonne
     */
    public void setColonne(final int nuoveColonne) {
        this.colonne = nuoveColonne;
    }

    /**
     * Funzione getValue.
     * @param r riga della cella desiderata
     * @param c colonna della cella desiderata
     * @return il valore contenuto nella cella di riga r e colonna c
     */
    public char getValue(final int r, final int c) {
        return tabellaPos[r][c];
    }

    /**
     * Funzione setValue.
     * @param i riga della cella desiderata
     * @param j colonna della cella desiderata
     * @param c valore che si vuole inserire nella cella di riga r e colonna c
     */
    public void setValue(final int i, final int j, final char c) {
        tabellaPos[i][j] = c;
    }


    /**
     * Funzione getLunghezzaNavi.
     * @return l'array contenente la lunghezza delle navi.
     */
    public int[] getLunghezzaNavi() {
        return Arrays.copyOf(lunghezzaNavi, lunghezzaNavi.length);
    }

    /**
     * Permette di modificare le celle della matrice.
     * @param newTabellaPos nuova matrice
     */
    public void setTabellaPos(final char[][] newTabellaPos) {
        this.tabellaPos = Arrays.copyOf(newTabellaPos, newTabellaPos.length);
    }


    /**
     * Cambia la dimensione della griglia di gioco in base al comando usato.
     * @param command stringa che indica la dimensione scelta
     */
    public void changeDim(final String command) {
        if (command.equals("/standard")) {
            righe = DEFAULT_DIM;
            colonne = DEFAULT_DIM;
        } else if (command.equals("/large")) {
            righe = LARGE_DIM;
            colonne = LARGE_DIM;
        } else if (command.equals("/extralarge")) {
            righe = EXTRA_DIM;
            colonne = EXTRA_DIM;
        }
    }
}
