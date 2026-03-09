package modelo;

public class Partida {
    private int idPartida;
    private int idUsuario;
    private int idSucursal;
    private int puntajeTotal;
    private int nivelAlcanzado;
    private String fechaRegistro;

    public Partida() {
    }

    public Partida(int idPartida, int idUsuario, int idSucursal, int puntajeTotal, int nivelAlcanzado, String fechaRegistro) {
        this.idPartida = idPartida;
        this.idUsuario = idUsuario;
        this.idSucursal = idSucursal;
        this.puntajeTotal = puntajeTotal;
        this.nivelAlcanzado = nivelAlcanzado;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(int puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public int getNivelAlcanzado() {
        return nivelAlcanzado;
    }

    public void setNivelAlcanzado(int nivelAlcanzado) {
        this.nivelAlcanzado = nivelAlcanzado;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
