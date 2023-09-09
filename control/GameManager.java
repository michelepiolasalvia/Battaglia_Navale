package battaglia_navale.control;

import battaglia_navale.utils.InputOutputUtils;

/**
 * Classe GameManager dell'applicazione.
 * Utilizzata per gestire le funzionalità
 * e regolare una partita
 * <i>&#60;Control&#62;</i>
 */
public final class GameManager {

    //COSTANTI DI DEFAULT

    /**
     * Tentativi di default per la difficoltà facile.
     */
    private static final int MAX_FACILE = 50;
    /**
     * Tentativi di default per la difficoltà media.
     */
    private static final int MAX_MEDIO = 30;
    /**
     * Tentativi di default per la difficoltà difficile.
     */
    private static final  int MAX_DIFFICILE = 10;

    //VARIABILI PER I TENTATIVI, MODIFICABILI
    /**
     * Tentativi difficoltà media.
     */
    private int tentativiMedio = MAX_MEDIO;
    /**
     * Tentativi difficoltà facile.
     */
    private int tentativiFacile = MAX_FACILE;
    /**
     * Tentativi difficoltà difficile.
     */
    private int tentativiDifficile = MAX_DIFFICILE;
    /**
     * Tentativi effettuati durante la partita.
     */
    private int tentativiEffettuati = 0;
    /**
     * Tentativi sbagliati durante la partita.
     */
    private int tentativiSbagliati = 0;
    /**
     * Stringa che indica la difficoltà della partita.
     */
    private String difficolta = "facile";
    /**
     * Variabile booleana che indica se tutte le navi sono state affondate.
     */
    private boolean tutteAffondate = false;


    /**
     * Variabile che indica il numero di tentativi massimi falliti nel gioco.
     */
    private int maxAttempts = tentativiFacile;


    /**
     * Costruttore della classe GameManager.
     */
    private GameManager() {

    }

    /**
     * Istanzia una istanza di GameManager.
     */
    private static class Holder {
        private static final GameManager INSTANCE = new GameManager();
    }

    /**
     * Funzione getInstance.
     * Permette di implementare il design pattern Singleton.
     * @return Restituisce un istanza di GameManager
     */
    public static GameManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Funzione che imposta il livello di difficoltà.
     * @param comando Comando che indica la difficoltà scelta dall'utente
     */
    public void impostaLivello(final String comando) {
        switch (comando) {
            case "/facile" -> {
                maxAttempts = tentativiFacile;
                InputOutputUtils.printCasiSetDiff("Facile");
                difficolta = "facile";
            }
            case "/medio" -> {
                maxAttempts = tentativiMedio;
                InputOutputUtils.printCasiSetDiff("Media");
                difficolta = "media";
            }
            case "/difficile" -> {
                maxAttempts = tentativiDifficile;
                InputOutputUtils.printCasiSetDiff("Diff");
                difficolta = "difficile";
            }
            default -> InputOutputUtils.printCasiSetDiff("NA");
        }
    }

    /**
     * Procedura che modifica il numero max di tentativi falliti.
     * Imposta un un nuovo numero di tentativi, in base al comando di difficoltà scelto
     * @param command comando corrispondente alla difficoltà inserito in input nel main
     * @param numero numero di tentativi che si vogliono impostare per la difficoltà scelta
     */
    public void impostaTentativi(final String command, final int numero) {
        switch (command) {
            case "/facile" -> {
                if (maxAttempts == tentativiFacile) {
                    maxAttempts = numero;
                }
                tentativiFacile = numero;
                System.out.println("Ok");
            }
            case "/medio" -> {
                if (maxAttempts == tentativiMedio) {
                    maxAttempts = numero;
                }
                tentativiMedio = numero;
                System.out.println("Ok");
            }
            case "/difficile" -> {
                if (maxAttempts == tentativiDifficile) {
                    maxAttempts = numero;
                }
                tentativiDifficile = numero;
                System.out.println("Ok");
            }

            default  -> { }
        }
    }

    /**
     * Permette di impostare il numero massimo di tentativi falliti.
     * @param numero nuovo numero massimo di tentativi falliti
     */
    public void setMaxAttempts(final int numero) {
        maxAttempts = numero;
        System.out.println("OK");
    }


    /**
     * Funzione Colpo.
     * Riceve in input un comando che indica una cella,
     * Verifica se la cella rientra nella dimensione scelta della griglia.
     * Se rientra allora controlla la griglia e prova a colpire la cella.
     *
     * @param input Stringa che indica la cella che si vuole colpire
     */
    public void colpo(final String input) {
        BattleShipManager bm = BattleShipManager.getInstance();
        int x = 0;
        int y = 0;
        final int grigliaS = 10;
        final int grigliaL = 18;
        final int grigliaXL = 26;
        tentativiEffettuati++;
        BattleShipManager.RisultatoSparo risultato = null;

        if (bm.getRigheGriglia() == grigliaS) {
            if (input.matches("[A-J]-(10|[1-9])")) {
                String[] parts = input.split("-");
                x = Integer.parseInt(parts[1]) - 1;
                y = parts[0].charAt(0) - 'A';
                risultato = bm.esitoSparo(x, y);
            }
        } else if (bm.getRigheGriglia() == grigliaL) {
            if (input.matches("[A-R]-([1-9]|1[0-8])")) {
                String[] parts = input.split("-");
                x = Integer.parseInt(parts[1]) - 1;
                y = parts[0].charAt(0) - 'A';
                risultato = bm.esitoSparo(x, y);
            }
        } else if (bm.getRigheGriglia() == grigliaXL) {
            if (input.matches("[A-Z]-([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-6])")) {
                String[] parts = input.split("-");
                x = Integer.parseInt(parts[1]) - 1;
                y = parts[0].charAt(0) - 'A';
                risultato = bm.esitoSparo(x, y);
            }
        }
        if (risultato != null) {
            switch (risultato) {
                case ACQUA:
                    InputOutputUtils.printCasiSparo("A");
                    bm.setValueGriglia(x, y, '~'); //simbolo ondina
                    tentativiSbagliati++;
                    break;

                case COLPITO:
                    InputOutputUtils.printCasiSparo("C");
                    bm.setValueGriglia(x, y, 'X');
                    break;

                case COLPITO_E_AFFONDATO:
                    InputOutputUtils.printCasiSparo("CA");
                    bm.setValueGriglia(x, y, 'X');
                    break;

                case GIACOLPITO:
                    InputOutputUtils.printCasiSparo("GC");
                    tentativiEffettuati--;
                    break;

                default:
                    InputOutputUtils.printCasiSparo("NA");
            }
        }

        InputOutputUtils.stampaTentativiEffettuati();
        InputOutputUtils.stampaGriglia("Mostra");
        InputOutputUtils.leggiTempoTrascorso();

        if (risultato == BattleShipManager.RisultatoSparo.COLPITO_E_AFFONDATO) {
            tutteAffondate = true;
            for (int i = 0; i < bm.getRigheGriglia(); i++) {
                for (int j = 0; j < bm.getColonneGriglia(); j++) {
                    if (bm.getValueGriglia(i, j) != ' ' && bm.getValueGriglia(i, j) != 'X'
                            && bm.getValueGriglia(i, j) != '~') {
                        tutteAffondate = false;
                        break;
                    }
                }
            }
        }
    }



    /**
     * Funzione tentativiFiniti.
     * @return true se i tentativi disponibili sono finiti, false altrimenti
     */
    public boolean tentativiFiniti() {
        return tentativiSbagliati > maxAttempts;
    }

    /**
     * Funzione checkNaviAffondate.
     * @return true se tutte le navi sono state affondate
     */
    public boolean checkNaviAffondate() {
        return tutteAffondate;
    }

    /**
     * Funzione resetTutteAffondate.
     * All'inizio di ogni partita setta tutteAffondate a false,
     * in quanto potrebbe rimanere impostato il valore a true dopo aver
     * vinto una partita precedente.
     */
    public void resetTutteAffondate() {
        tutteAffondate = false;
    }

    /**
     * Funzione getTentativiEffettuati.
     * @return numero di tentativi effettuati
     */
    public int getTentativiEffettuati() {
        return tentativiEffettuati;
    }

    /**
     * Funzione getTentativiSbagliati.
     * @return numero di tentativi effettuati
     */
    public int getTentativiSbagliati() {
        return tentativiSbagliati;
    }

    /**
     * Funzione getMaxAttempts.
     * @return numero massimo di tentativi falliti disponibili
     */
    public int getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Funzione getDifficolta.
     * @return la difficoltà del gioco
     */
    public String getDifficolta() {
        return difficolta;
    }


}

