package vista;

import controlador.ControladorProducto;
import controlador.ControladorHistorial;
import modelo.Producto;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class VistaMenuAdminTienda extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTextField txtIdProducto;
    private JTextField txtNombreProducto;
    private JComboBox cmbEstadoProducto;
    private JButton btnGuardarProd;
    private JButton btnActualizarProd;
    private JButton btnLimpiarProd;
    private JTable tablaMisProductos;
    private JTable tablaRanking;
    private JButton btnRefrescarRanking;
    private JButton btnExportarCSV;
    private JButton regresarButton;

    private Usuario adminActual;
    private ControladorProducto conProd = new ControladorProducto();
    private ControladorHistorial conHis = new ControladorHistorial();

    public VistaMenuAdminTienda(Usuario usuario) {
        this.adminActual = usuario;
        setContentPane(mainPanel);
        setTitle("Administrador de Tienda - Sucursal #" + adminActual.getIdSucursal());
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Llenar tablas al iniciar
        mostrarMisProductos();
        mostrarRanking();

        btnGuardarProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto p = new Producto();
                p.setNombre(txtNombreProducto.getText());
                p.setEstadoActivo(cmbEstadoProducto.getSelectedItem().toString().equals("Activo") ? 1 : 0);

                //Le asignamos la sucursal del administrador automáticamente
                p.setIdSucursal(adminActual.getIdSucursal());

                if (conProd.registrarProducto(p)) {
                    JOptionPane.showMessageDialog(null, "Producto Creado en tu Sucursal");
                    mostrarMisProductos();
                    limpiarCampos();
                }
            }
        });

        btnActualizarProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtIdProducto.getText().isEmpty()) return;

                Producto p = new Producto();
                p.setIdProducto(Integer.parseInt(txtIdProducto.getText()));
                p.setNombre(txtNombreProducto.getText());
                p.setEstadoActivo(cmbEstadoProducto.getSelectedItem().toString().equals("Activo") ? 1 : 0);
                p.setIdSucursal(adminActual.getIdSucursal());

                if (conProd.actualizarProducto(p)) {
                    JOptionPane.showMessageDialog(null, "Producto Actualizado");
                    mostrarMisProductos();
                    limpiarCampos();
                }
            }
        });

        btnLimpiarProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        tablaMisProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaMisProductos.getSelectedRow();
                txtIdProducto.setText(tablaMisProductos.getValueAt(fila, 0).toString());
                txtNombreProducto.setText(tablaMisProductos.getValueAt(fila, 1).toString());

                // Seleccionar el estado correcto en el ComboBox
                String estado = tablaMisProductos.getValueAt(fila, 2).toString();
                cmbEstadoProducto.setSelectedItem(estado.equals("1") ? "Activo" : "Inactivo");
            }
        });


        btnRefrescarRanking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRanking();
            }
        });

        btnExportarCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarTablaCSV(tablaRanking);
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                vista.VistaUsuario vistaUsuario = new vista.VistaUsuario();
                vistaUsuario.setVisible(true);
                dispose();
            }
        });
    }

    private void mostrarMisProductos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Estado (1=Activo)");

        List<Producto> lista = conProd.listarProductosPorSucursal(adminActual.getIdSucursal());
        for (int i = 0; i < lista.size(); i++) {
            modelo.addRow(new Object[]{
                    lista.get(i).getIdProducto(),
                    lista.get(i).getNombre(),
                    lista.get(i).getEstadoActivo()
            });
        }
        tablaMisProductos.setModel(modelo);
    }

    private void mostrarRanking() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Empleado");
        modelo.addColumn("Puntaje Total");
        modelo.addColumn("Nivel Alcanzado");
        modelo.addColumn("Fecha");

        List<Object[]> ranking = conHis.obtenerRankingPorSucursal(adminActual.getIdSucursal());
        for (int i = 0; i < ranking.size(); i++) {
            modelo.addRow(ranking.get(i));
        }
        tablaRanking.setModel(modelo);
    }

    private void limpiarCampos() {
        txtIdProducto.setText("");
        txtNombreProducto.setText("");
        cmbEstadoProducto.setSelectedIndex(0);
    }

    private void exportarTablaCSV(JTable tabla) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exportar Ranking a CSV");

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            String ruta = archivo.getAbsolutePath();
            if (!ruta.endsWith(".csv")) { ruta += ".csv"; }

            try (FileWriter csv = new FileWriter(ruta)) {
                TableModel modelo = tabla.getModel();

                for (int i = 0; i < modelo.getColumnCount(); i++) {
                    csv.write(modelo.getColumnName(i) + ",");
                }
                csv.write("\n");

                for (int i = 0; i < modelo.getRowCount(); i++) {
                    for (int j = 0; j < modelo.getColumnCount(); j++) {
                        csv.write("\"" + modelo.getValueAt(i, j).toString() + "\",");
                    }
                    csv.write("\n");
                }
                JOptionPane.showMessageDialog(this, "Archivo CSV exportado con exito.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al exportar: " + ex.getMessage());
            }
        }
    }
}
