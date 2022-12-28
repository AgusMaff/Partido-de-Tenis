/*
* Esta clase va a simular el marcador que va a llevar las puntuaciones del partido
* durante cada instante de tiempo.
*/
import java.util.ArrayList;
public class Marcador implements Observer{
    private static Marcador unicaInstancia;

    private Observable ovservable = null;

    Marcador(Observable observable){
        this.ovservable = observable;
    }




    /*
    *Este metodo es parte del patron singleton. Retorna
    *la instancia de la clase marcador en caso que ya
    * exista una, de los contrario se crea una.
    *
    * param1: recibe un objeto del tipo observable ya
    *         es necesario para instanciar la clase.
    */
    public static Marcador getInstance(Observable o){
        return unicaInstancia == null ? unicaInstancia = new Marcador(o) : unicaInstancia;
    }




    /*
    *Este metodo se utiliza junto con el patron Observer
    *llevar el registro del partido cada vez que haya un
    * cambio en el marcador
    *
    * param1: recibe un ArrayList de los jugadores para
    *         poder extraer los datos de cada uno.
    * param2: recibe un ArrayList de los Sets para poder
    *         extraer el numero de games que lleva cada
    *         jugador.
    */
    public void update(ArrayList<Jugador> jugadores, ArrayList<Sets> sets){
        System.out.println("########################################################");
        System.out.println("#                       Marcador                       #");
        System.out.println("########################################################");
        System.out.println(jugadores.get(0).getName()+"   Pts: "+jugadores.get(0).puntos+"|     "+sets.get(0).getGames1()+"     |"+"|     "+sets.get(1).getGames1()+"     |"+"|     "+sets.get(2).getGames1()+"     |"+"|     "+sets.get(3).getGames1()+"     |"+"|     "+sets.get(4).getGames1()+"     |\n");
        System.out.println("               |    Set 1    ||    Set 2    ||    Set 3    ||    Set 4    ||     Set 5    |\n");
        System.out.println(jugadores.get(1).getName()+"   Pts: "+jugadores.get(1).puntos+"|     "+sets.get(0).getGames2()+"     |"+"|     "+sets.get(1).getGames2()+"     |"+"|     "+sets.get(2).getGames2()+"     |"+"|     "+sets.get(3).getGames2()+"     |"+"|     "+sets.get(4).getGames2()+"     |\n\n\n");
    }
}
