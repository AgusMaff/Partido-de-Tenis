public interface Observable {
    public void notificar();
    public void anadirObservador(Observer o);

    public void removerObservador(Observer o);

}
