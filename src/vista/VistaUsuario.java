package vista;

import controlador.ControladorUsuario;
import modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaUsuario extends JFrame {
    private JPasswordField txtPassword;
    private JTextField txtUsuario;
    private JButton btnEntrar;
    private JPanel mainPanel;

    public VistaUsuario() {
        setContentPane(mainPanel);
        setTitle("Login - Pizza Express");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = txtUsuario.getText();
                String pass = new String(txtPassword.getPassword());

                ControladorUsuario controladorUsuario = new ControladorUsuario();
                Usuario logueado = controladorUsuario.login(user, pass);

                if (logueado != null) {
                    this.dispose();
                    int rol = logueado.getIdRol();

                    switch (rol) {
                        case 1:
                            new VistaMenuSuperAdmin().setVisible(true);
                            VistaUsuario.this.dispose();
                            break;
                        case 2:
                            new VistaMenuAdminTienda(logueado).setVisible(true);
                            VistaUsuario.this.dispose();
                            break;
                        case 3:
                            new VistaMenuEmpleado(logueado).setVisible(true);
                            VistaUsuario.this.dispose();
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Error: Rol no reconocido.");
                            break;
                    }
                    JOptionPane.showMessageDialog(null, "Bienvenido, " + logueado.getUsername() + "!");
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void dispose() {
            }
        });
    }




}
