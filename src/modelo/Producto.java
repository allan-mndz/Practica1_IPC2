package modelo;

public class Producto {
    private int idProducto;
    private String nombre;
    private int estadoActivo;
    private int idSucursal;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, int estadoActivo, int idSucursal) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.estadoActivo = estadoActivo;
        this.idSucursal = idSucursal;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int isEstadoActivo() {
        return estadoActivo;
    }

    public int getEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(int estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }
}
