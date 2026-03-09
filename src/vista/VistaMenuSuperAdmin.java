package vista;

import controlador.ControladorHistorial;
import controlador.ControladorSucursal;
import controlador.ControladorUsuario;
import modelo.Sucursal;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VistaMenuSuperAdmin extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JTextField txtIdSucursal;
    private JTextField txtNombreSucursal;
    private JButton btnGuardarSucursal;
    private JButton btnActualizarSucursal;
    private JButton btnEliminarSucursal;
    private JButton btnLimpiarSucursal;
    private JTable tablaSucursales;

    private JTabbedPane tabbedPane2;
    private JTextField txtIdUsuario;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtIdRol;
    private JTextField txtIdSucursalUsuario;
    private JButton btnGuardarUsuario;
    private JButton btnActualizarUsuario;
    private JButton btnEliminarUsuario;
    private JButton btnLimpiarUsuario;
    private JTable tablaUsuarios;
    private JTabbedPane tabbedPane3;
    private JButton btnRefrescarHistorial;
    private JTable tablaHistorial;
    private JButton REGRESARButton;

    ControladorSucursal conSuc = new ControladorSucursal();
    ControladorUsuario conUsu = new ControladorUsuario();
    ControladorHistorial conHis = new ControladorHistorial();

    public VistaMenuSuperAdmin() {
        setContentPane(mainPanel);
        setTitle("Menú Super Admin");
        setSize(1500, 1500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mostrarSucursales();
        mostrarUsuarios();

        btnGuardarSucursal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sucursal s = new Sucursal();
                s.setNombre(txtNombreSucursal.getText());
                if (conSuc.registrarSucursal(s)) {
                    JOptionPane.showMessageDialog(null, "Sucursal Guardada");
                    mostrarSucursales();
                    limpiarSucursal();
                }
            }
        });

        btnActualizarSucursal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sucursal s = new Sucursal();
                s.setIdSucursal(Integer.parseInt(txtIdSucursal.getText()));
                s.setNombre(txtNombreSucursal.getText());
                if (conSuc.actualizarSucursal(s)) {
                    JOptionPane.showMessageDialog(null, "Sucursal Actualizada");
                    mostrarSucursales();
                    limpiarSucursal();
                }
            }
        });


        tablaSucursales.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaSucursales.getSelectedRow();
                txtIdSucursal.setText(tablaSucursales.getValueAt(fila, 0).toString());
                txtNombreSucursal.setText(tablaSucursales.getValueAt(fila, 1).toString());
            }
        });

        btnGuardarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoUsername = txtUsername.getText();

                if(nuevoUsername.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre de usuario no puede estar vacío.");
                    return;
                }

                if (conUsu.existeUsuario(nuevoUsername)) {
                    JOptionPane.showMessageDialog(null, "¡Error! El usuario '" + nuevoUsername + "' ya existe. Por favor elige otro.");
                    return;
                }

                Usuario u = new Usuario();
                u.setUsername(nuevoUsername);
                u.setPassword(txtPassword.getText());
                u.setIdRol(Integer.parseInt(txtIdRol.getText()));
                u.setIdSucursal(Integer.parseInt(txtIdSucursalUsuario.getText()));

                if (conUsu.registrarUsuario(u)) {
                    JOptionPane.showMessageDialog(null, "Usuario Registrado");
                    mostrarUsuarios();
                    limpiarUsuario();
                }
            }
        });

        btnEliminarSucursal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtIdSucursal.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecciona una sucursal de la tabla primero.");
                    return;
                }

                int id = Integer.parseInt(txtIdSucursal.getText());
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar esta sucursal?", "Confirmar", JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {
                    if (conSuc.eliminarSucursal(id)) {
                        JOptionPane.showMessageDialog(null, "Sucursal eliminada.");
                        mostrarSucursales(); // Refrescamos la tabla
                        txtIdSucursal.setText(""); // Limpiamos
                        txtNombreSucursal.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: No se puede eliminar porque tiene usuarios o partidas asociadas.");
                    }
                }
            }
        });

        btnEliminarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtIdUsuario.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecciona un usuario de la tabla primero.");
                    return;
                }

                int id = Integer.parseInt(txtIdUsuario.getText());
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {
                    if (conUsu.eliminarUsuario(id)) {
                        JOptionPane.showMessageDialog(null, "Usuario eliminado.");
                        mostrarUsuarios(); // Refrescamos la tabla
                    }
                }
            }
        });

        tablaUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaUsuarios.getSelectedRow();
                txtIdUsuario.setText(tablaUsuarios.getValueAt(fila, 0).toString());
                txtUsername.setText(tablaUsuarios.getValueAt(fila, 1).toString());
                txtIdRol.setText(tablaUsuarios.getValueAt(fila, 2).toString());
                txtIdSucursalUsuario.setText(tablaUsuarios.getValueAt(fila, 3).toString());
            }
        });

        btnLimpiarSucursal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtIdSucursal.setText("");
                txtNombreSucursal.setText("");
            }
        });

        btnLimpiarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtIdUsuario.setText("");
                txtUsername.setText("");
                txtPassword.setText("");
                txtIdRol.setText("");
                txtIdSucursalUsuario.setText("");
            }
        });

        btnRefrescarHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarHistorial();
            }
        });
        REGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VistaUsuario vistaUsuario = new VistaUsuario();
                vistaUsuario.setVisible(true);
                VistaMenuSuperAdmin.this.dispose();
            }
        });
    }

    public void mostrarSucursales() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID"); modelo.addColumn("Nombre");
        List<Sucursal> lista = conSuc.listarSucursales();
        for (int i = 0; i < lista.size(); i++) {
            modelo.addRow(new Object[]{lista.get(i).getIdSucursal(), lista.get(i).getNombre()});
        }
        tablaSucursales.setModel(modelo);
    }

    public void mostrarUsuarios() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID"); modelo.addColumn("Usuario"); modelo.addColumn("Rol"); modelo.addColumn("Sucursal");
        List<Usuario> lista = conUsu.listarUsuarios();
        for (int i = 0; i < lista.size(); i++) {
            modelo.addRow(new Object[]{lista.get(i).getIdUsuario(), lista.get(i).getUsername(), lista.get(i).getIdRol(), lista.get(i).getIdSucursal()});
        }
        tablaUsuarios.setModel(modelo);
    }

    public void mostrarHistorial() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID"); modelo.addColumn("Empleado"); modelo.addColumn("Sucursal");
        modelo.addColumn("Puntos"); modelo.addColumn("Nivel"); modelo.addColumn("Fecha");
        List<Object[]> reporte = conHis.obtenerReporteGeneral();
        for (int i = 0; i < reporte.size(); i++) {
            modelo.addRow(reporte.get(i));
        }
        tablaHistorial.setModel(modelo);
    }

    private void limpiarSucursal() {
        txtIdSucursal.setText("");
        txtNombreSucursal.setText("");
    }
    private void limpiarUsuario() {
        txtIdUsuario.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtIdRol.setText("");
        txtIdSucursalUsuario.setText("");
    }
}
