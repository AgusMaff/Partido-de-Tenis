/*
 * Esta clase se encarga de lleva a cabo toda la simulacion del partido, instanciar
 * los jugador que van a participar, actualizar los resultados siempre que haya algun
 * cambio en el marcador e indicar el ganador del partido.
 */
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Torneo implements Observable {
    private static Torneo unicaInstancia;
    private Scanner scan = new Scanner(System.in);
    private String name;
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private int numberSets;
    private ArrayList<Sets> sets = new ArrayList<>();
    public boolean firstTime;
    private int setIndex;
    private int prob;
    private String revancha = null;

    private boolean tieBreak;

    private ArrayList<Observer> suscriptores = new ArrayList<Observer>();

    Torneo(){
    }
    /*
     * Metodo getter para obtener el numero de sets que se van
     * a jugar en el torneo.
     */
    public int getNumberSets(){
        return numberSets;
    }



    /*
     * Metodo setter que se utiliza para definir el numero de
     * sets que se van a jugar en el torneo.
     * El torneo solo admite dos opciones validas, 3 o 5. El numero se
     * ingresa por teclado y en caso de no ser una opcion valida se
     * solicita ingresar otra opcion nuevamente.
     */
    private void setNumberSets(){
        int aux = 0;
        do{
            System.out.println("Ingrese el numero de sets del torneo:");
            aux = scan.nextInt();
            if(aux == 3 || aux == 5){
                this.numberSets = aux;
                break;
            }else{
                System.out.println("El numero de sets ingresado debe ser de 3 o 5");
            }
        }while(true);
    }



    /*
     * Metodo getter que se utiliza para obtener el nombre del torneo.
     */
    public String getName(){
        return name;
    }



    /*
     * Metodo setter que se utiliza para definir el nombre del torneo.
     * El nombre se ingresa por teclado, y en caso de no se un nombre valido
     * se solicita que se ingrese el nombre nuevamente.
     */
    public void setName(){
        do {
            System.out.println("Ingrese nombre el nombre del torneo: ");
            this.name = scan.nextLine();
            if(Objects.equals(this.name, "")){System.out.println("#Ingrese un nombre valido#");}
        }while(Objects.equals(this.name, ""));
    }



    /*
     * Metodo que se utiliza para agregar jugadores al torneo
     */
    private void addJugador(Jugador jugador){
        this.jugadores.add(jugador);
    }



    /*
     * Este metodo es parte del patron singleton. Retorna
     * la instancia de la clase torneo en caso que ya
     * exista una, de los contrario se crea una.
     */
    public static Torneo getInstance(){
        return unicaInstancia == null ? unicaInstancia = new Torneo() : unicaInstancia;
    }



    /*
     * Este metodo da comienzo a la simulacion. Imprime por
     * consola un mensaje de bienvenida al torneo e incializa
     * a los jugadores y los sets.
     * Cuando la simulacion finaliza le pregunta al usuario si
     * desea volver a simular.
     * En caso que no se desee simular nuevamente, se finaliza la
     * simulacion y se imprime un mensaje de despedida.
     * En caso que no se ingrese una opcion valida se solicita que
     * ingrese nuevamente.
     */
    public void initTorneo(){
        do {
            System.out.println("######################");
            System.out.println("BIENVENIDO AL TORNEO " + this.name.toUpperCase());
            System.out.println("######################\n");
            tieBreak = false;
            firstTime = true;
            setIndex = 0;//0
            //Solicito el nombre del jugador 1
            Jugador jugador1 = new Jugador();
            jugador1.setName();

            //Solicito el nombre del jugador 2
            Jugador jugador2 = new Jugador();
            jugador2.setName();

            this.addJugador(jugador1);
            this.addJugador(jugador2);
            this.setNumberSets();

            //Setea una lista con el numero de Sets que se van a jugar
            for (int i = 0; i <= 4; i++) {
                Sets set = new Sets("Set " + (i + 1));
                sets.add(set);
            }

            anadirObservador(Marcador.getInstance(getInstance()));

            System.out.println("Los jugadores " + jugador1.getName() + " y " + jugador2.getName() + " se enfrentaran en el torneo " + this.getName() + "\n");
            System.out.println("El ganador se define al mejor de " + this.getNumberSets() + " sets\n");

            do{
                System.out.println("#Ingrese la probabilidad de victoria de " + jugador1.getName() + "#");
                this.prob = scan.nextInt();
                if(prob >= 0 && prob <= 100){
                    break;
                }else{
                    System.out.println("La probabilidad de victoria debe estar entre 0 o 100.\n" +
                            "Ingrese nuevamente");
                }
            }while( prob < 0 || prob >100);

            jugador1.setProbWin(prob);
            jugador2.setProbWin(100 - jugador1.getProbWing());

            System.out.println("La probabilidad de " + jugador1.getName() + " es del " + jugador1.getProbWing() + "% y la probabilidad de " + jugador2.getName() + " es " + jugador2.getProbWing() + "%\n");

            try{
                TimeUnit.SECONDS.sleep(3);
            }catch(InterruptedException e){
                System.out.println(e);
            }

            do {
                this.simGame();                  //6
                if (sets.get(setIndex).getGames1() >= 6 || sets.get(setIndex).getGames2() >= 6) {
                    if (Math.abs(sets.get(setIndex).getGames1() - sets.get(setIndex).getGames2()) >= 2) {
                        if (sets.get(setIndex).getGames1() > sets.get(setIndex).getGames2()) {
                            printResultadoSet(jugador1, sets.get(setIndex));
                            jugador1.sets++;
                            setIndex++;
                        } else {
                            printResultadoSet(jugador2, sets.get(setIndex));
                            jugador2.sets++;
                            setIndex++;
                        }
                        notificar();
                    } else if (sets.get(setIndex).getGames1() - sets.get(setIndex).getGames2() == 0) {
                        tieBreak();
                    }
                }
                switch (numberSets) {
                    case 3 -> {
                        if (jugador1.sets == 2) {
                            jugador1.win = true;
                        } else if (jugador2.sets == 2) {
                            jugador2.win = true;
                        }
                    }
                    case 5 -> {
                        if (jugador1.sets == 3) {
                            jugador1.win = true;
                        } else if (jugador2.sets == 3) {
                            jugador2.win = true;
                        }
                    }
                }

            } while (!jugador1.win && !jugador2.win);

            for (Jugador j : this.jugadores) {
                if (j.win) {
                    System.out.println("########################");
                    System.out.println("   GANADOR DEL TORNEO   ");
                    System.out.println("Ganador: " + j.getName());
                    System.out.println("Torneo: " + this.getName());
                    System.out.println("########################\n");
                }
            }
            this.jugadores.clear();
            this.sets.clear();
            this.suscriptores.clear();
            System.out.println("Desea jugar la revancha?");
            do {
                System.out.println("Ingrese una de las opciones validas (Si/No)");
                revancha = scan.nextLine();
            }while(Objects.equals(revancha,""));
        }while(Objects.equals(revancha, "Si") || Objects.equals(revancha, "si"));
        System.out.println("Gracias por jugar.\n Saludos.\n Atte: Agustin");
    }



    /*
     * Este metodo lleva a cabo la eleccion aleatoria del
     * primer jugador que va a sacar e imprime por consola
     * cuando este va a realizar un saque.
     */
    private void simGame(){
        if(firstTime){
            firstTime = false;
            int index = (int)Math.floor(Math.random()*2);//numero entero entre 0 y 1
            jugadores.get(index).saque = true;
        }

        for(Jugador j:jugadores){
            if(j.saque){System.out.println("El jugador "+j.getName()+" va a sacar\n");}
        }
        /* Descomentar este bloque de ccodigo para visualizar los saque de cada jugador.
           Para fines de testeo esta comentado para que la simulacion sea mas rapida.
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch(InterruptedException e){
            System.out.println(e);
        }
        */
        simPoint();
    }



    /*
     * Este metodo realiza la eleccion aleatoria del jugador que
     * gana el punto que se esta disputando. Esta eleccion se
     * realiza en base a la probabilidad de victoria definida para
     * cada jugador.
     */
    private void simPoint(){
        do{
            System.out.println("En juego...\n");

            jugadores.get(0).aux = (int)Math.floor(Math.random()*calcRange(jugadores.get(0).getProbWing()) * jugadores.get(0).getProbWing());
            jugadores.get(1).aux = (int)Math.floor(Math.random()*calcRange(jugadores.get(1).getProbWing()) * jugadores.get(1).getProbWing());

            if(jugadores.get(0).aux > jugadores.get(1).aux){

                System.out.println(jugadores.get(0).getName()+" gano el punto\n");
                if(!tieBreak) {
                    calcPuntos(jugadores.get(0), sets.get(setIndex));
                    notificar();
                    continue;
                }
                jugadores.get(0).tieBreakPts++;
            }else if(jugadores.get(0).aux < jugadores.get(1).aux){

                System.out.println(jugadores.get(1).getName()+" gano el punto\n");
                if(!tieBreak) {
                    calcPuntos(jugadores.get(1), sets.get(setIndex));
                    notificar();
                    continue;
                }
                jugadores.get(1).tieBreakPts++;
            }
        }while(jugadores.get(0).aux == jugadores.get(1).aux);
    }



    /*
     * Este metodo calcula los puntos de cada jugador y decide
     * si un jugador gano el game.
     */
    private void calcPuntos(Jugador jugador, Sets set){
        int index;
        if(jugadores.indexOf(jugador) == 0){
            index = 1;
        }else{
            index = 0;
        }

        switch (jugador.puntos) {
            case "0" -> jugador.puntos = "15";
            case "15" -> jugador.puntos = "30";
            case "30" -> jugador.puntos = "40";
            case "40" -> {
                if (jugadores.get(index).puntos != "40" && jugadores.get(index).puntos != "AD") {
                    jugador.puntos = "0";
                    if (index == 1) {
                        set.setGames1();
                    } else {
                        set.setGames2();
                    }
                    swap(jugadores.get(0).saque, jugadores.get(1).saque);
                    jugadores.get(index).puntos = "0";
                } else if (jugadores.get(index).puntos == "40") {
                    jugador.puntos = "AD";
                } else if (jugadores.get(index).puntos == "AD") {
                    jugadores.get(index).puntos = "40";
                }
            }
            case "AD" -> {
                jugador.puntos = "0";
                if (index == 1) {
                    set.setGames1();
                } else {
                    set.setGames2();
                }
                swap(jugadores.get(0).saque, jugadores.get(1).saque);
                jugadores.get(index).puntos = "0";
            }
        }
    }



    /*
     * Este metodo simula un game de tiebreak cuando se alcanza un empate 6 a 6 en un set.
     */
    private void tieBreak(){
        tieBreak = true;
        System.out.print("Hubo un empate de 6 a 6 en el "+sets.get(setIndex).name+". Va a comenzar el tie break.\n");
        try{
            TimeUnit.SECONDS.sleep(5);
        }catch(InterruptedException e){
            System.out.println(e);
        }
        do{
            notificar();
            simGame();
            jugadores.get(0).puntos = Integer.toString(jugadores.get(0).tieBreakPts);
            jugadores.get(1).puntos = Integer.toString(jugadores.get(1).tieBreakPts);
            //notificar();
            if((jugadores.get(0).tieBreakPts + jugadores.get(1).tieBreakPts)%2 == 0 && (jugadores.get(0).tieBreakPts + jugadores.get(1).tieBreakPts) != 0){
                swap(jugadores.get(0).saque, jugadores.get(1).saque);
            }
            if(Objects.equals(jugadores.get(0).puntos, "7") || Objects.equals(jugadores.get(1).puntos, "7")){
                if(Math.abs(Integer.parseInt(jugadores.get(0).puntos) - Integer.parseInt(jugadores.get(1).puntos)) >= 2){
                    if(Integer.parseInt(jugadores.get(0).puntos) > Integer.parseInt(jugadores.get(1).puntos)){
                        sets.get(setIndex).setGames1();
                        incSetTieBreak(jugadores.get(0));
                    }else{
                        sets.get(setIndex).setGames2();
                        incSetTieBreak(jugadores.get(1));
                    }
                    notificar();
                    break;
                }
            }
        }while(true);
    }



    /*
     * Metodo que se ejecuta cuando termina el tiebreak.
     * Limpia los puntos de los jugadores e inncrementa los
     * sets del ganador.
     */
    private void incSetTieBreak(Jugador j){
        jugadores.get(0).puntos = "0";
        jugadores.get(1).puntos = "0";
        jugadores.get(0).tieBreakPts = 0;
        jugadores.get(1).tieBreakPts = 0;
        printResultadoSet(j, sets.get(setIndex));
        j.sets++;
        setIndex++;
        tieBreak = false;
    }



    /*
     * Metodo que intecambia el atributo swap de los jugadores
     * para cambiar el saque al finalizar un game o cada dos
     * puntos en un tie break.
     */
    private void swap(boolean a, boolean b){
        boolean temp;
        temp = jugadores.get(0).saque;
        jugadores.get(0).saque = jugadores.get(1).saque;
        jugadores.get(1).saque = temp;
    }



    /*
     * Metodo que imprime por consola el ganador de cada set
     */
    private void printResultadoSet(Jugador j, Sets s){
        System.out.println("################################");
        System.out.println("El jugador "+j.getName()+" gano el "+sets.get(setIndex).name);
        System.out.println("################################\n");
    }



    /*
     * Metodo que se usa para notificar a los objetos
     * observadores (en este caso un unico marcador)
     * para actualizar el resultado.
     */
    public void notificar(){
        for(Observer o: this.suscriptores){
            o.update(this.jugadores, this.sets);
        }
    }



    /*
     *Metodo para anadir observador a esta instancia observable
     */
    public void anadirObservador(Observer o){
        this.suscriptores.add(o);
    }



    /*
     * Metodo para remover observadores de esta instancia observable.
     */
    public void removerObservador(Observer o){
        this.suscriptores.remove(o);
    }



    /*
     * Metodo para definir el rango de valores que se
     * va a utilizar para elegir el ganador de cada
     * punto. Va en funcion de la probabilidad de
     * victoria de cada jugador.
     */
    private int calcRange(int prob){
        if(prob >= 60 && prob < 70){
            return 81;
        }else if(prob >= 70 && prob < 80){
            return 51;
        }else if(prob >= 80 && prob < 90){
            return 31;
        }else if(prob >= 90 && prob <= 100){
            return 26;
        }else{
            return 101;
        }
    }
}