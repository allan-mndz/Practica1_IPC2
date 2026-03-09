package controlador;

import modelo.Conexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ControladorPartida {

    public int iniciarPartida(int idUsuario, int idSucursal) {
        String sql = "INSERT INTO Partidas (id_usuario, id_sucursal, fecha_registro, puntaje_total, nivel_alcanzado) VALUES (?, ?, NOW(), 0, 1)";
        Conexion cn = new Conexion();
        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idSucursal);
            stmt.executeUpdate();

            // Id que mysql le asigno a esta partida
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                return rs.getInt(1);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al iniciar partida: " + e);
        }
        return -1;
    }

    public int registrarPedido(int idPartida, String nombrePizza, int tiempoLimite) {
        String sql = "INSERT INTO Pedidos (id_partida, estado_actual, tiempo_limite) VALUES (?, 'Pendiente', ?)";
        Conexion cn = new Conexion();
        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, idPartida);
            stmt.setInt(2, tiempoLimite);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar pedido: " + e);
        }
        return -1;
    }

    public void actualizarEstadoPedido(int idPedido, String estado) {
        String sql = "UPDATE Pedidos SET estado_actual = ? WHERE id_pedido = ?";
        Conexion cn = new Conexion();
        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estado);
            stmt.setInt(2, idPedido);
            stmt.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar pedido: " + e);
        }
    }

    public void finalizarPartida(int idPartida, int puntaje, int nivel) {
        String sql = "UPDATE Partidas SET puntaje_total = ?, nivel_alcanzado = ? WHERE id_partida = ?";
        Conexion cn = new Conexion();
        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, puntaje);
            stmt.setInt(2, nivel);
            stmt.setInt(3, idPartida);
            stmt.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al finalizar partida: " + e);
        }
    }

    public void registrarDetalle(int idPedido, int idProducto) {
        String sql = "INSERT INTO Detalle (id_pedido, id_producto) VALUES (?, ?)";
        Conexion cn = new Conexion();
        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar detalle: " + e);
        }
    }
}
