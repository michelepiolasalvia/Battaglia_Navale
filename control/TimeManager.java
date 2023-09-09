package battaglia_navale.control;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe TimeManager.
 * Classe utilizzata per gestire
 * l'utilizzo di un timer di gioco
 * <i>&#60;Control&#62;</i>
 */
public final class TimeManager {

    /**
     * Istanza unica della classe TimeManager.
     */
    private static TimeManager instance;

    /**
     * Oggetto di tipo Timer.
     */
    private Timer timer;
    /**
     * Tempo trascorso dall'inizio della partita.
     */
    private int tempoTrascorso;

    /**
     * Indica se il timer è attivo o no.
     */
    private boolean timerAttivo = false;
    /**
     * Durata di default della partita.
     */
    private final int defaultTime = 20;
    /**
     * Durata del timer.
     */
    private int tempoTimer = defaultTime;

    /**
     *Costruttore della classe TimeManager.
     */
    private TimeManager() { }

    /**
     * Metodo statico per ottenere l'istanza unica di TimeManager (Singleton).
     * @return istanza unica di TimeManager
     */
    public static synchronized TimeManager getInstance() {
        if (instance == null) {
            instance = new TimeManager();
        }
        return instance;
    }


    /**
     * Funzione impostaTimer.
     * Funzione richiamata in seguito al comando
     * /tempo numero
     * che serve a impostare il timer ad un tempo a scelta
     * @param durata durata del timer scelta dall'utente
     */
    public void impostaTimer(final int durata) {
        tempoTimer = durata;
        System.out.println("OK");
    }

    /**
     * Funzione iniziaTimer.
     * Quando viene avviata la partita
     * inizializza le variabili necessarie e richiama
     * avviaTimer()
     */
    public void iniziaTimer() {
        final int fine = 0;
        tempoTrascorso = fine;
        timerAttivo = true;
        avviaTimer();
    }

    /**
     * Funzione avviaTimer.
     * Fa partire il timer quando viene avviata la partita
     * e ne gestisce il funzionamento
     */
    private void avviaTimer() {
        final int period = 60000;
        final int delay = 0;
        synchronized (TimeManager.class) {
            if (timer != null) {
                timer.cancel();
            }
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (tempoTimer > delay) {
                    tempoTimer--;
                    tempoTrascorso++;
                } else {
                    timerAttivo = false;
                    timer.cancel();
                }
            }
        }, delay, period); // Esegui la task ogni minuto (60000 millisecondi)
    }

    /**
     * Funzione giocoAttivo.
     * Funzione utilizzata per indicare
     * se il timer è finito oppure no
     * @return true se il timer è attivo, false altrimenti
     */
    public boolean giocoAttivo() {
        return timerAttivo;
    }

    /**
     * Funzione mostraTempo.
     * Funzione utilizzata per mostrare al giocatore
     * il tempo trascorso e il tempo rimanente
     */
    public void mostraTempo() {
        final int delay = 1;
        System.out.println("Tempo trascorso: " + (tempoTrascorso - delay) + " minuti");
        System.out.println("Tempo rimanente: " + (tempoTimer + delay) + " minuti");
    }

    /**
     * Procedura che chiude il thread del timer.
     */
    public void bloccaTimer() {
        timer.cancel(); // Annulla la TimerTask per fermare il timer
        timerAttivo = false; // Imposta il timer come non attivo
        tempoTimer=defaultTime;
    }

    /**
     * Funzione getTempoTrascorso.
     * @return tempo trascorso dall'inizio della partita
     */
    public int getTempoTrascorso() {
        return tempoTrascorso;
    }

}


