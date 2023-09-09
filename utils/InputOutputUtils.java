package battaglia_navale.utils;

import battaglia_navale.control.BattleShipManager;
import battaglia_navale.control.GameManager;
import battaglia_navale.control.TimeManager;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Classe InputOutputUtils dell'applicazione.
 * Utilizzata per gestire le stampe a video
 * e l'inserimento degli input
 * <i>&#60;Boundary&#62;</i>
 */
public final class InputOutputUtils {
    /**
     * Scanner dell'input.
     */
    private static Scanner inp = new Scanner(System.in, StandardCharsets.UTF_8);

    /**
     * Costruttore di InputOutputUtils.
     */
    private InputOutputUtils() { }

    /**
     * Funzione richiamata dal comando /help.
     * Mostra tutti i comandi riconosciuti dall'applicazione
     */
    public static void printHelp() {
        System.out.println("""
Lista dei comandi nel menu iniziale:
- "/help" per la lista dei comandi

- "/esci" per uscire dal gioco

- "/facile" per impostare la difficoltà facile
   (50 tentativi falliti possibili)

- "/medio" per impostare la difficoltà media
  (30 tentativi falliti possibili)

- "/difficile" per impostare la difficoltà difficile
  (10 tentativi falliti possibili)

- "/facile numero" imposta a numero il numero massimo di tentativi falliti per la difficolta facile

- "/medio numero" imposta a numero il numero massimo di tentativi falliti per la difficolta medio

- "/difficile numero" imposta a numero il numero massimo di tentativi falliti per la difficolta difficile

- "/mostralivello" per visualizzare la difficoltà impostata e il numero massimo di tentativi falliti

- "/mostranavi" per visualizzare ogni tipo di nave con la dimensione e il numero di esemplari da affrontare

- "/tentativi" numero per impostare a numero il numero massimo di tentativi falliti

- "/standard" imposta a 10x10 la dimensione della griglia (è il default)

- "/large" imposta a 18x18 la dimensione della griglia

- "/extralarge" imposta a 26x26 la dimensione della griglia

- "/tempo numero" imposta a numero il numero minuti a disposizione per giocare

- "/gioca" per iniziare una nuova partita

Lista dei comandi disponibili in gioco:

- "/mostratempo" l’applicazione risponde visualizzando il numero di minuti trascorsi nel gioco
  e il numero di minuti ancora disponibili

- "/mostragriglia" l’applicazione risponde visualizzando la griglia, con le righe numerate da 1 a 10/18/26
e le colonne numerate da A a J/R/Z, con le navi affondate e le sole parti già colpite delle navi non
affondate.

- "/svelagriglia" per visualizzare la griglia di gioco con tutte le navi posizionate

- "/mostratentativi" l’applicazione risponde visualizzando il numero di tentativi già effettuati,
  il numero di tentativi falliti e il numero massimo di tentativi falliti.

- "/abbandona" l'applicazione chiede conferma
  • se la conferma è positiva, l’applicazione risponde visualizzando sulla griglia la posizione di tutte le navi
    e si predispone a ricevere nuovi comandi
  • se la conferma è negativa, l'applicazione si predispone a ricevere nuovi tentativi o comandi.
""");
    }


    /**
     * Procedura stampaTentativi.
     * Stampa il numero di tentativi effettuati,
     * il numero di tentativi falliti,
     * il numero massimo di tentativi falliti
     */
    public static void stampaTentativi() {
        System.out.println("Tentativi effettuati: " + GameManager.getInstance().getTentativiEffettuati()
                + "\nTentativi falliti: " + GameManager.getInstance().getTentativiSbagliati()
                + "\nNumero massimo di tentativi falliti: " + GameManager.getInstance().getMaxAttempts());
    }


    /**
     * Stampa il numero di tentativi effettuati.
     */
    public static void stampaTentativiEffettuati() {
        System.out.println("Tentativi Effettuati: " + GameManager.getInstance().getTentativiEffettuati());
    }

    /**
     * Stampa un messaggio alla fine della partita.
     * @param motivo motivo per il quale e' finita la partita,
     *               in base al quale cambia la stampa
     */
    public static void endGame(final String motivo) {
        switch (motivo) {
            case "Tempo" -> System.out.println("Il tempo è finito, hai perso! \nTorno al menù principale");
            case "Tentativi" -> System.out.println("Hai finito i tentativi, hai perso! \nTorno al menù principale");
            case "Navi" -> System.out.println("Hai affondato tutte le navi, hai vinto! \nTorno al menù principale");
            default -> { }
        }
    }

    /**
     * Funzione che legge il tempo trascorso dall'inizio della partita.
     */
    public static void leggiTempoTrascorso() {
        final int delay = 1;
        System.out.println("Tempo trascorso: " + (TimeManager.getInstance().getTempoTrascorso() - delay) + " minuti");
    }

    /**
     * Procedura che stampa la griglia di gioco.
     * Stampa il contenuto di ogni cella della griglia.
     * Se riceve in input "Svela" allora è stata richiamata da /svelagriglia
     * e mostra la posizione di tutte le navi
     * Se riceve in input "Mostra" allora è stata richiamata da /mostragriglia
     * e mostra solo le celle colpite
     * @param menu Stringa ricevuta in input che indica quale stampa effettuare
     */
    public static void stampaGriglia(final String menu) {
          /*Questo blocco di codice imposta il carattere di encoding del flusso di output standard a UTF-8,
            in modo che i caratteri unicode possano essere correttamente visualizzati sulla console.

            In particolare, System.out è il flusso di output standard,
            che viene sovrascritto con un nuovo PrintStream che ha come carattere di encoding UTF-8.

            Il secondo parametro true indica che il flusso di output viene pulito
            automaticamente ogni volta che viene utilizzato il metodo println. */
        PrintStream stdout = System.out;
        BattleShipManager bm = BattleShipManager.getInstance();
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (menu.equals("Svela")) {
            System.out.printf("%2s", " ");
            for (int i = 0; i < bm.getColonneGriglia(); i++) {
                System.out.printf("%3s", (char) ('A' + i));
            }
            System.out.println();

            for (int i = 0; i < bm.getRigheGriglia(); i++) {
                System.out.printf("%2d", (i + 1));
                for (int j = 0; j < bm.getColonneGriglia(); j++) {
                    char cell = bm.getValueGriglia(i, j);
                    if (cell == '\u0000' || cell == ' ' || cell == '~') {
                        System.out.printf("%3s", " ");
                    } else {
                        System.out.printf("%3c", '\u25A0');
                    }
                }
                System.out.println();
            }
        } else if (menu.equals("Mostra")) {
            System.out.printf("%2s", " ");
            for (int i = 0; i < bm.getColonneGriglia(); i++) {
                System.out.printf("%3s", (char) ('A' + i));
            }
            System.out.println();
            for (int i = 0; i < bm.getRigheGriglia(); i++) {
                System.out.printf("%2d", (i + 1));
                for (int j = 0; j < bm.getColonneGriglia(); j++) {
                    char cell = bm.getValueGriglia(i, j);
                    if (cell == '\u0000' || cell == ' ' || cell == 'X' || cell == '~') {
                        System.out.printf("%3s", cell);
                    } else /*if (cell == 'X' || cell == 'a')*/ {
                        System.out.printf("%3c", ' ');
                    }
                }
                System.out.println();
            }
        }
        System.setOut(stdout);  // Reimposta il flusso di output al suo valore originale
    }

    /**
     * Richiede un comando da tastiera.
     * @return comando inserito da tastiera
     */
    public static String prendiComando() {
        System.out.print("Inserire un comando: ");
        String comando = inp.nextLine().toLowerCase();
        return comando;
    }

    /**
     * Controlla se il numero ricevuto sia valido.
     * @param parts stringa contenente il numero da convertire
     * @return -1 se il numero non è valido, il valore convertito altrimenti
     */
    public static int checkNumber(final String parts) {
        int number = -1;
        try {
            number = Integer.parseInt(parts);
            if (number < 1) {
                number = -1;
                System.out.println("Numero inserito non valido! Deve essere maggiore di 1");
            }
        } catch (NumberFormatException e) {
            System.out.println("Comando non valido.");
            number = -1;
        }
        return number;
    }

    /**
     * Stampa un messaggio all'inizio della partita.
     */
    public static void startGame() {
        System.out.println("Le navi sono state piazzate, inizia la partita");
    }

    /**
     * Stampa il messaggio dell'esito di uno sparo.
     * @param caso stringa che indica l'esito dello sparo
     */
    public static void printCasiSparo(final String caso) {
        switch (caso) {
            case "A" -> System.out.println("Acqua");
            case "C" -> System.out.println("Colpito!");
            case "CA" -> System.out.println("Colpito e affondato!");
            case "GC" -> System.out.println("Non puoi colpire di nuovo la stessa cella!");
            case "NA" -> System.out.println("Cella non valida");
            default -> { }
        }
    }

    /**
     * Stampa un messaggio in base alla difficolta' settata.
     * @param caso indica quale difficolta' e' stata settata
     */
    public static void printCasiSetDiff(final String caso) {
        switch (caso) {
            case "Facile" -> System.out.println("Ok, difficoltà facile impostata");
            case "Media" -> System.out.println("Ok, difficoltà media impostata.");
            case "Diff" -> System.out.println("Ok, difficoltà difficile impostata.");
            case "NA" -> System.out.println("Comando non riconosciuto.");
            default -> { }
        }
    }

    /**
     * Funzione richiamata dal comando /esci.
     * @return true se l'utente vuole uscire, false altrimenti
     */
    public static boolean exit() {
        Scanner s = new Scanner(System.in, StandardCharsets.UTF_8);
        while (true) {
            System.out.println("Scrivi /si per confermare,"
                    + "/no altrimenti");
            String risp = s.nextLine().toLowerCase();
            if (risp.equals("/si")) {
                return true;
            } else if (risp.equals("/no")) {
                return false;
            }
        }
    }

    /**
     *Mostra il livello di difficoltà ed il numero massimo di tentativi falliti.
     */
    public static void showLevel() {
        System.out.println("Livello di difficoltà: " + GameManager.getInstance().getDifficolta() + "\n"
                + "Numero max di tentativi falliti: " + GameManager.getInstance().getMaxAttempts());

    }

    /**
     * Richiamata in seguito all'utilizzo del comando /mostranavi.
     * Stampa tutti i tipi di nave con rispettiva lunghezza e
     * numero di esemplari
     */
    public static void mostraNavi() {
        System.out.println("""
            Cacciatorpediniere: ⊠⊠ esemplari: 4
            Incrociatore ⊠⊠⊠ esemplari: 3
            Corazzata ⊠⊠⊠⊠ esemplari: 2
            Portaerei ⊠⊠⊠⊠⊠ esemplari: 1
        """);
    }








}
