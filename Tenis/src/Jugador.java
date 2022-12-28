/*
* En esta clase se definen los jugadores que van a participar en el torneo, con sus respectivos atributos
* */
import java.util.Objects;
import java.util.Scanner;
public class Jugador {
    private String name;
    public String puntos;
    public int sets;
    private int prob_win;
    private Scanner scan = new Scanner(System.in);
    public boolean saque;
    public boolean win;
    public int aux;

    public int tieBreakPts;

    Jugador(){
        this.puntos = "0";
        this.sets = 0;
        this.saque = false;
        this.aux = 0;
        this.win = false;
        this.tieBreakPts = 0;
    }
    /*
    * Metodo getter para obtener el nombre del jugador
    */
    public String getName(){
        return name;
    }




    /*
    * Metodo setter para poder definir el nombre del jugador.
    * Se le solicita al usuario ingresar un nombre valido por
    * teclado.
    */
    public void setName(){
        do {
            System.out.println("#Ingrese el nombre del jugador: #");
            this.name = scan.nextLine();
            if(Objects.equals(this.name, "")){
                System.out.println("Ingrese un nombre valido");
            }
        }while(Objects.equals(this.name, ""));
    }




    /*
    * Metodo setter para poder definir la probabilidad de victoria
    * de un jugador.
    *
    * param1: recibe un entero que representa la probabilidad.
    */
    public void setProbWin(int prob){
                this.prob_win = prob;
    }




    /*
    *Metodo getter para para obtener la probabilidad de victoria
    * del jugador.
    */
    public int getProbWing(){
        return this.prob_win;
    }
}