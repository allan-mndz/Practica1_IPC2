package vista;

import controlador.ControladorNivel;
import controlador.ControladorPartida;
import controlador.ControladorProducto;
import modelo.Producto;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.util.List;
import java.util.Random;

public class VistaMenuEmpleado extends JFrame {
    private JLabel lblPuntaje;
    private JLabel lblNivel;
    private JLabel lblTiempo;
    private JTextPane txtPedidoActual;
    private JButton btnIniciar;
    private JButton btnEntregar;
    private JPanel mianPanel;
    private JButton btnFinalizar;
    private JButton btnPreparar;
    private JButton btnHornear;
    private JButton btnCancelar;
    private JLabel lblEstado;
    private JProgressBar progresoPedido;
    private JLabel lblImagenPizza;
    private JLabel lblPreparar;
    private JLabel lblHornear;
    private JLabel lblEntregar;

    private int puntaje = 0;
    private int nivel = 1;
    private int tiempoRestante = 0;
    private int tiempoTotalAsignado = 0;
    private boolean juegoActivo = false;
    private Timer timerReloj;
    private String pedidoActual = "";

    private Usuario usuarioActual;
    private ControladorPartida conPartida = new ControladorPartida();
    private ControladorProducto conProd = new ControladorProducto();
    private int idPartidaActual = -1;
    private int idPedidoActual = -1;

    private ControladorNivel conNivel = new ControladorNivel();

    public VistaMenuEmpleado(Usuario usuario) {
        this.usuarioActual = usuario;
        setContentPane(mianPanel);
        setTitle("Pizza Express - Empleado: " + usuarioActual.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        desactivarControlesCocina();
        btnFinalizar.setEnabled(false);

        btnIniciar.addActionListener(e -> iniciarTurno());

        btnPreparar.addActionListener(e -> cambiarEstado("PREPARANDO", btnHornear));

        btnHornear.addActionListener(e -> cambiarEstado("EN_HORNO", btnEntregar));

        btnEntregar.addActionListener(e -> entregarPedido());

        btnCancelar.addActionListener(e -> cancelarVoluntariamente());

        btnFinalizar.addActionListener(e -> finalizarTurnoCompleto());
    }

    private void iniciarTurno(){
        // Enviar idUsuario e idSucursal al iniciar
        idPartidaActual = conPartida.iniciarPartida(usuarioActual.getIdUsuario(), usuarioActual.getIdSucursal());

        if (idPartidaActual != -1) {
            puntaje = 0; nivel = 1;
            btnIniciar.setEnabled(false);
            btnFinalizar.setEnabled(true);
            generarNuevoPedido();
            iniciarCronometro();
        }
    }

    private void generarNuevoPedido() {
        List<Producto> productos = conProd.listarProductos();
        if (!productos.isEmpty()) {
            Producto p = productos.get(new Random().nextInt(productos.size()));
            pedidoActual = p.getNombre();

            tiempoRestante = conNivel.obtenerTiempoPorNivel(nivel);
            tiempoTotalAsignado = tiempoRestante;

            idPedidoActual = conPartida.registrarPedido(idPartidaActual, pedidoActual, tiempoRestante);
            conPartida.registrarDetalle(idPedidoActual, p.getIdProducto());

            progresoPedido.setValue(25);
            progresoPedido.setString("Orden Recibida - 25%");
            progresoPedido.setForeground(java.awt.Color.BLUE);

            txtPedidoActual.setText("ORDEN #" + idPedidoActual + "\nPIZZA: " + pedidoActual.toUpperCase());
            txtPedidoActual.setFont(new java.awt.Font("DejaVu Sans Mono", java.awt.Font.BOLD, 18));
            lblEstado.setText("Estado: RECIBIDA");

            btnPreparar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnHornear.setEnabled(false);
            btnEntregar.setEnabled(false);

            try {
                java.net.URL urlImagen = getClass().getResource("/imagenes/img.png");

                if (urlImagen != null) {
                    ImageIcon iconoOriginal = new ImageIcon(urlImagen);
                    java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
                    lblImagenPizza.setIcon(new ImageIcon(imagenEscalada));

                    lblPreparar.setIcon(null);
                    lblHornear.setIcon(null);
                    lblEntregar.setIcon(null);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró la imagen de la pizza. Verifica el nombre y la ruta.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen de la pizza: " + ex.getMessage());
            }
        }
    }

    private void cambiarEstado(String nuevoEstado, JButton siguienteBoton) {
        conPartida.actualizarEstadoPedido(idPedidoActual, nuevoEstado);
        lblEstado.setText("Estado: " + nuevoEstado);

        if (nuevoEstado.equals("PREPARANDO")) {
            progresoPedido.setValue(50);
            progresoPedido.setString("Preparando Ingredientes... 50%");
            progresoPedido.setForeground(java.awt.Color.ORANGE);
            btnPreparar.setEnabled(false);
            try {
                java.net.URL urlImagen = getClass().getResource("/imagenes/img_1.png");

                if (urlImagen != null) {
                    ImageIcon iconoOriginal = new ImageIcon(urlImagen);
                    java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
                    lblPreparar.setIcon(new ImageIcon(imagenEscalada));
                } else {
                    System.out.println("No se encontró la ruta de la imagen. Verifica el nombre.");
                }
            } catch (Exception ex) {
                System.out.println("Error al cargar imagen: " + ex.getMessage());
            }
        }
        else if (nuevoEstado.equals("EN_HORNO")) {
            progresoPedido.setValue(75);
            progresoPedido.setString("Cocinando en Horno... 75%");
            progresoPedido.setForeground(java.awt.Color.RED);
            btnHornear.setEnabled(false);
            btnCancelar.setEnabled(false);
            try {
                java.net.URL urlImagen = getClass().getResource("/imagenes/img_3.png");

                if (urlImagen != null) {
                    ImageIcon iconoOriginal = new ImageIcon(urlImagen);
                    // Escalar la imagen para que no desajuste tu ventana (150x150 píxeles)
                    java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
                    lblHornear.setIcon(new ImageIcon(imagenEscalada));
                } else {
                    System.out.println("No se encontró la ruta de la imagen. Verifica el nombre.");
                }
            } catch (Exception ex) {
                System.out.println("Error al cargar imagen: " + ex.getMessage());
            }
        }

        siguienteBoton.setEnabled(true);
    }

    private void entregarPedido() {
        conPartida.actualizarEstadoPedido(idPedidoActual, "LISTA/ENTREGADO");

        int puntosGanados = 100;

        if (tiempoRestante >= (tiempoTotalAsignado / 2.0)) {
            puntosGanados += 50;
            JOptionPane.showMessageDialog(this, "¡Bono de Eficiencia! (+150 pts)");
        } else {
            JOptionPane.showMessageDialog(this, "Pedido Entregado (+100 pts)");
        }

        puntaje += puntosGanados;

        if (puntaje >= 1000) {
            nivel = 3;
        } else if (puntaje >= 500) {
            nivel = 2;
        }

        try {
            java.net.URL urlImagen = getClass().getResource("/imagenes/img_2.png");

            if (urlImagen != null) {
                ImageIcon iconoOriginal = new ImageIcon(urlImagen);
                java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
                lblEntregar.setIcon(new ImageIcon(imagenEscalada));
            } else {
                System.out.println("No se encontró la ruta de la imagen. Verifica el nombre.");
            }
        } catch (Exception ex) {
            System.out.println("Error al cargar imagen: " + ex.getMessage());
        }

        actualizarLabels();

        Timer pausa = new Timer(1000, e -> generarNuevoPedido());
        pausa.setRepeats(false);
        pausa.start();
    }

    private void cancelarVoluntariamente() {
        int resp = JOptionPane.showConfirmDialog(this, "¿Cancelar pedido? (-30 pts)");
        if (resp == JOptionPane.YES_OPTION) {
            conPartida.actualizarEstadoPedido(idPedidoActual, "CANCELADA");
            puntaje = Math.max(0, puntaje - 30);
            actualizarLabels();
            generarNuevoPedido();
        }
    }

    private void iniciarCronometro() {
        if (timerReloj != null) timerReloj.stop();
        timerReloj = new Timer(1000, e -> {
            tiempoRestante--;
            lblTiempo.setText("Tiempo: " + tiempoRestante + "s");
            if (tiempoRestante <= 0) {
                procesarTiempoAgotado();
            }
        });
        timerReloj.start();
    }

    private void procesarTiempoAgotado() {
        timerReloj.stop();
        conPartida.actualizarEstadoPedido(idPedidoActual, "NO_ENTREGADO");

        JOptionPane.showMessageDialog(this, "¡EL CLIENTE SE FUE! (-50 pts)");
        puntaje = Math.max(0, puntaje - 50);

        actualizarLabels();
        generarNuevoPedido();
        timerReloj.start();
    }

    private void finalizarTurnoCompleto() {
        timerReloj.stop();
        // Guardamos puntaje total y nivel alcanzado
        conPartida.finalizarPartida(idPartidaActual, puntaje, nivel);
        JOptionPane.showMessageDialog(this, "Turno finalizado. Puntaje: " + puntaje);
        this.dispose();
    }

    private void desactivarControlesCocina() {
        btnPreparar.setEnabled(false);
        btnHornear.setEnabled(false);
        btnEntregar.setEnabled(false);
        btnCancelar.setEnabled(false);
    }

    private void actualizarLabels() {
        lblPuntaje.setText("Puntaje: " + puntaje);
        lblNivel.setText("Nivel: " + nivel);
        lblTiempo.setText("Tiempo Restante: " + tiempoRestante + "s");
    }

}
