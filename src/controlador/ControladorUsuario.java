package controlador;

import modelo.Conexion;
import modelo.Usuario;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ControladorUsuario {

    public Usuario login(String username, String password){
        String sql = "SELECT * FROM Usuarios WHERE username = ? AND password = ?";
        Conexion cn = new Conexion();

        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario foundUser = new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("id_rol"),
                            rs.getInt("id_sucursal")
                    );
                    return foundUser;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al hacer login: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }


    // 1. Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";
        Conexion cn = new Conexion();

        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setIdRol(rs.getInt("id_rol"));
                usuario.setIdSucursal(rs.getInt("id_sucursal"));
                lista.add(usuario);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar usuarios: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    // 2. Registrar un nuevo usuario
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (username, password, id_rol, id_sucursal) VALUES (?, ?, ?, ?)";
        Conexion cn = new Conexion();

        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setInt(3, usuario.getIdRol());

            // Validamos si es Super Admin (Rol 1) para dejar la sucursal en NULL si es necesario
            if (usuario.getIdRol() == 1 && usuario.getIdSucursal() == 0) {
                stmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(4, usuario.getIdSucursal());
            }

            stmt.execute();
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // 4. DELETE: Eliminar un usuario
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM Usuarios WHERE id_usuario = ?";
        Conexion cn = new Conexion();
        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e);
            return false;
        }
    }

    public boolean existeUsuario(String username) {
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE username = ?";
        Conexion cn = new Conexion();

        try (Connection conn = cn.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si el conteo es mayor a 0
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al validar usuario duplicado: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
