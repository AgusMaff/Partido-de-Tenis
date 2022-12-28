/*
 * Esta clase va a crear los sets que se van a encargar de llevar registros de los
 * games que lleva cada jugador.
 */
public class Sets {
    private int games1;
    private int games2;

    public String name;

    Sets(String name){
        this.name = name;
        this.games1 = 0;//0
        this.games2 = 0;//0
    }




    /*
     * Funcion getter para obtener el numero de games que
     * lleva el jugador1 en el set
     */
    public int getGames1(){
        return this.games1;
    }




    /*
     * Funcion getter para obtener el numero de games que
     * lleva el jugador2 en el set
     */
    public int getGames2(){
        return this.games2;
    }




    /*
     * Funcion setter para incrementar los games que lleva
     * el jugador1
     */
    public void setGames1(){
        this.games1++;
    }




    /*
     * Funcion setter para incrementar los games que lleva
     * el jugador2
     */
    public void setGames2(){
        this.games2++;
    }

}
