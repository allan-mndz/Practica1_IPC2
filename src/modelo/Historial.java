package modelo;

public class Historial {
    private int idHistorial;
    private int idPedido;
    private String estado;
    private String fechaRegistro;

    public Historial() {
    }

    public Historial(int idHistorial, int idPedido, String estado, String fechaRegistro) {
        this.idHistorial = idHistorial;
        this.idPedido = idPedido;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
