package battaglia_navale.control;
import battaglia_navale.Entities.BattleShip;

import java.util.Random;


/**
 * Classe BattleshipManager.
 * Utilizzata per gestire la griglia di gioco.
 * <i>&#60;Control&#62;</i>
 */
public final class BattleShipManager {
    /**
     * Oggetto di tipo BattleShip.
     * Rappresenta la griglia di gioco
     * contenente le navi
     */
    private BattleShip grigliaGioco = new BattleShip();



    /** Oggetto della classe Random.
     * Usato per generare casualmente valori interi e booleani
     */
    private static Random random = new Random();

    /**
     * Costruttore della classe BattleShipManager.
     */
    private BattleShipManager() { }

    /**
     * Istanzia una istanza di BattleShipManager.
     */
    private static class Holder {
        private static final BattleShipManager INSTANCE = new BattleShipManager();
    }

    /**
     * Funzione getInstance.
     * Permette di implementare il design pattern Singleton
     * @return l'istanza di BattleShipManager
     */
    public static BattleShipManager getInstance() {
        return Holder.INSTANCE;
    }





    /**
     * Procedura che posiziona randomicamente tutte le navi all'interno della griglia.
     */
    public void piazzaNavi() {

        // Definisci i simboli delle navi
        char[] simboliNavi = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        for (int i = 0; i < grigliaGioco.getLunghezzaNavi().length; i++) {
            int lunghezza = grigliaGioco.getLunghezzaNavi()[i];
            char simbolo = simboliNavi[i];

            // Genera casualmente le coordinate iniziali e la direzione
            int x;
            int y;
            boolean orizzontale;
            do {
                x = random.nextInt(grigliaGioco.getRighe());
                y = random.nextInt(grigliaGioco.getColonne());
                orizzontale = random.nextBoolean();
            } while (!verificaPosizione(x, y, lunghezza, orizzontale));

            for (int j = 0; j < lunghezza; j++) {
                if (orizzontale) {
                    grigliaGioco.setValue(x, y + j, simbolo);
                } else {
                    grigliaGioco.setValue(x + j, y, simbolo);
                }
            }

        }
    }

    /**
     * Funzione che verifica se il posizionamento della nave è corretto.
     * @param x Riga in cui si vuole piazzare
     * @param y Colonna in cui si vuole piazzare
     * @param lunghezza lunghezza della nave
     * @param orizzontale true se orizzontale, false se verticale
     * @return true se è possibile piazzare la nave, false altrimenti
     */
    private boolean verificaPosizione(final int x, final int y, final int lunghezza, final boolean orizzontale) {
        if (orizzontale) {
            if (y + lunghezza > grigliaGioco.getColonne()) {
                return false;
            }

            for (int i = y; i < y + lunghezza; i++) {
                if (grigliaGioco.getValue(x, i) != ' ') {
                    return false;
                }
            }
        } else {
            if (x + lunghezza > grigliaGioco.getRighe()) {
                return false;
            }

            for (int i = x; i < x + lunghezza; i++) {
                if (grigliaGioco.getValue(i, y) != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Procedura che inizializza la Griglia alla dimensione scelta.
     */
    public void inizializzaGriglia() {
        char[][] tabellaPos = new char[grigliaGioco.getRighe()][grigliaGioco.getColonne()];
        grigliaGioco.setTabellaPos(tabellaPos);
        for (int i = 0; i < grigliaGioco.getRighe(); i++) {
            for (int j = 0; j < grigliaGioco.getColonne(); j++) {
                grigliaGioco.setValue(i, j, ' ');
            }
        }
    }

    /**
     * Funzione EsitoSparo.
     * Riceve in input le coordinate di una cella e tramite dei controlli
     * verifica l'esito del colpo.
     * @param x intero che indica la riga che si vuole colpire
     * @param y intero che indica la colonna che si vuole colpire
     * @return Restituisce l'esito del colpo sulla cella ricevuta in input
     */
    RisultatoSparo esitoSparo(final int x, final int y) {
        if (grigliaGioco.getValue(x, y) == ' ') {
            return RisultatoSparo.ACQUA;
        } else if (grigliaGioco.getValue(x, y) != 'X' && grigliaGioco.getValue(x, y) != '\u007E') {
            char simboloNave = grigliaGioco.getValue(x, y);
            boolean affondato = true;

            for (int i = 0; i < grigliaGioco.getRighe(); i++) {
                for (int j = 0; j < grigliaGioco.getColonne(); j++) {
                    if (grigliaGioco.getValue(i, j) == simboloNave && (i != x || j != y)) {
                        affondato = false;
                        break;
                    }
                }
            }
            if (affondato) {
                return RisultatoSparo.COLPITO_E_AFFONDATO;
            } else {
                return RisultatoSparo.COLPITO;
            }
        } else {
            return RisultatoSparo.GIACOLPITO;
        }
    }

    /**
     * Enumerazione RisultatoSparo.
     * Rappresenta tutti i possibili esiti di uno sparo
     * in una cella
     */
    enum RisultatoSparo {
        ACQUA,
        COLPITO,
        COLPITO_E_AFFONDATO,
        GIACOLPITO
    }

    /**
     * Funzione getRigheGriglia.
     * @return il numero di righe della griglia
     */
    public int getRigheGriglia() {
        return grigliaGioco.getRighe();
    }

    /**
     * Funzione getColonneGriglia.
     * @return il numero di colonne della griglia
     */
    public int getColonneGriglia() {
        return grigliaGioco.getColonne();
    }

    /**
     * Funzione getValueGriglia.
     * @return il valore di una cella della griglia
     */
    public char getValueGriglia(final int i, final int y) {
        return grigliaGioco.getValue(i, y);
    }

    /**
     * Funzione setValueGriglia.
     * Modifica una cella della griglia
     */
    public void setValueGriglia(final int i, final int y, final char value) {
        grigliaGioco.setValue(i, y, value);
    }

    /**
     * Cambia la dimensione della griglia di gioco in base al comando usato.
     * @param command stringa che indica la dimensione scelta
     */
    public void changeDimGriglia(final String command) {
        grigliaGioco.changeDim(command);
    }



}
