package modelo;

public class ConfiguracionNivel {
    private int idNivel;
    private int tiempoBaseSegundos;

    public ConfiguracionNivel() {
    }

    public ConfiguracionNivel(int idNivel, int tiempoBaseSegundos) {
        this.idNivel = idNivel;
        this.tiempoBaseSegundos = tiempoBaseSegundos;
    }

    public int getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }

    public int getTiempoBaseSegundos() {
        return tiempoBaseSegundos;
    }

    public void setTiempoBaseSegundos(int tiempoBaseSegundos) {
        this.tiempoBaseSegundos = tiempoBaseSegundos;
    }
}
