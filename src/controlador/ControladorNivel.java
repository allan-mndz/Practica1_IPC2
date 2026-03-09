package controlador;

import modelo.Conexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ControladorNivel {
    public int obtenerTiempoPorNivel(int nivel) {
        int tiempoBase = 60;
        String sql = "SELECT tiempo_base_segundos FROM Configuracion_Niveles WHERE id_nivel = ?";
        Conexion cn = new Conexion();

        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nivel);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Jalamos el tiempo directamente de la tabla
                tiempoBase = rs.getInt("tiempo_base_segundos");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener tiempo del nivel: " + e);
        }
        return tiempoBase;
    }
}
