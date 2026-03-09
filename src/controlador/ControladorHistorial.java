package controlador;

import modelo.Conexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ControladorHistorial {
    public List<Object[]> obtenerReporteGeneral() {
        List<Object[]> lista = new ArrayList<>();
        // Usamos los nombres exactos de tu diagrama: puntaje_total, nivel_alcanzado, etc.
        String sql = "SELECT p.id_partida, u.username, s.nombre, p.puntaje_total, p.nivel_alcanzado, p.fecha_registro " +
                "FROM Partidas p " +
                "JOIN Usuarios u ON p.id_usuario = u.id_usuario " +
                "JOIN Sucursales s ON p.id_sucursal = s.id_sucursal " +
                "ORDER BY p.fecha_registro DESC";
        Conexion cn = new Conexion();
        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getInt(5), rs.getTimestamp(6)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener reporte general: " + e);
        }
        return lista;
    }


    public List<Object[]> obtenerRankingPorSucursal(int idSucursal) {
        List<Object[]> ranking = new ArrayList<>();
        String sql = "SELECT u.username, p.puntaje_total, p.nivel_alcanzado, p.fecha_registro " +
                "FROM Partidas p INNER JOIN Usuarios u ON p.id_usuario = u.id_usuario " +
                "WHERE p.id_sucursal = ? ORDER BY p.puntaje_total DESC";
        Conexion cn = new Conexion();
        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSucursal);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getString("username");
                fila[1] = rs.getInt("puntaje_total");
                fila[2] = rs.getInt("nivel_alcanzado");
                fila[3] = rs.getString("fecha_registro");
                ranking.add(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ranking por sucursal: " + e);
        }
        return ranking;
    }
}
