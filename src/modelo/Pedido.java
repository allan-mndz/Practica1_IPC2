package modelo;

public class Pedido {
    private int idPedido;
    private int idPartida;
    private int tiempoLimite;
    private String estadoActual;

    public Pedido() {
    }

    public Pedido(int idPedido, int idPartida, int tiempoLimite, String estadoActual) {
        this.idPedido = idPedido;
        this.idPartida = idPartida;
        this.tiempoLimite = tiempoLimite;
        this.estadoActual = estadoActual;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public int getTiempoLimite() {
        return tiempoLimite;
    }

    public void setTiempoLimite(int tiempoLimite) {
        this.tiempoLimite = tiempoLimite;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }
}
