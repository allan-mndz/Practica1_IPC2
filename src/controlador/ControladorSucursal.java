package controlador;

import modelo.Conexion;
import modelo.Sucursal;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ControladorSucursal {

    public List<Sucursal> listarSucursales() {
       List<Sucursal> lista = new ArrayList<>();
       String sql = "SELECT * FROM Sucursales";
       Conexion conexion = new Conexion();

       try(Connection con = conexion.conectar();
           PreparedStatement stmt = con.prepareStatement(sql);
           ResultSet rs = stmt.executeQuery()) {

           while (rs.next()) {
               Sucursal sucursal = new Sucursal();
               sucursal.setIdSucursal(rs.getInt("id_sucursal"));
               sucursal.setNombre(rs.getString("nombre"));
               lista.add(sucursal);
           }
       } catch (Exception e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(null, "Error al listar sucursales: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
       }

        return lista; // Retorna la lista de sucursales obtenida
    }

    public boolean registrarSucursal(Sucursal sucursal) {
        String sql = "INSERT INTO Sucursales (nombre) VALUES (?)";
        Conexion cn = new Conexion();

        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sucursal.getNombre());
            stmt.execute();
            return true; // Retorna verdadero

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar sucursal: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean actualizarSucursal(Sucursal sucursal) {
        String sql = "UPDATE Sucursales SET nombre = ? WHERE id_sucursal = ?";
        Conexion cn = new Conexion();

        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sucursal.getNombre());
            stmt.setInt(2, sucursal.getIdSucursal());
            stmt.execute();
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar sucursal: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean eliminarSucursal(int id) {
        String sql = "DELETE FROM Sucursales WHERE id_sucursal = ?";
        Conexion cn = new Conexion();
        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar sucursal: " + e);
            return false;
        }
    }
}
