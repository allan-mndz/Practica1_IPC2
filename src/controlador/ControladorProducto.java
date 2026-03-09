package controlador;

import modelo.Conexion;
import modelo.Producto;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorProducto {
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Productos WHERE estado_activo = 1";
        Conexion cn = new Conexion();

        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setIdSucursal(rs.getInt("id_sucursal"));
                lista.add(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos: " + e);
        }
        return lista;
    }

    public boolean registrarProducto(Producto p) {
        String sql = "INSERT INTO Productos (nombre, estado_activo, id_sucursal) VALUES (?, 1, ?)";
        Conexion cn = new Conexion();
        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setInt(2, p.getIdSucursal());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar producto: " + e);
            return false;
        }
    }

    public List<Producto> listarProductosPorSucursal(int idSucursal) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Productos WHERE id_sucursal = ?";
        Conexion cn = new Conexion();
        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSucursal);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setEstadoActivo(rs.getInt("estado_activo"));
                p.setIdSucursal(rs.getInt("id_sucursal"));
                lista.add(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar por sucursal: " + e);
        }
        return lista;
    }

    public boolean actualizarProducto(Producto p) {
        String sql = "UPDATE Productos SET nombre=?, estado_activo=?, id_sucursal=? WHERE id_producto=?";
        Conexion cn = new Conexion();
        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setInt(2, p.getEstadoActivo());
            stmt.setInt(3, p.getIdSucursal());
            stmt.setInt(4, p.getIdProducto());

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar producto: " + e);
            return false;
        }
    }
}
