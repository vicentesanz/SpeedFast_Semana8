package vista;

import modelo.GestorPedidos;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private final GestorPedidos gestorPedidos;

    private JButton btnRegistrarPedido;
    private JButton btnGestionarRepartidores;
    private JButton btnGestionarPedidos;
    private JButton btnGestionarEntregas;
    private JButton btnSalir;

    public VentanaPrincipal() {

        gestorPedidos = new GestorPedidos();

        setTitle("SpeedFast - Sistema de Reparto");
        setSize(500, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        40,
                        30,
                        40
                )
        );

        JLabel lblTitulo =
                new JLabel(
                        "SpeedFast",
                        SwingConstants.CENTER
                );

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        JLabel lblSubtitulo =
                new JLabel(
                        "Sistema de gestión de pedidos",
                        SwingConstants.CENTER
                );

        lblSubtitulo.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        JPanel panelTitulos =
                new JPanel(
                        new GridLayout(
                                2,
                                1,
                                5,
                                5
                        )
                );

        panelTitulos.add(
                lblTitulo
        );

        panelTitulos.add(
                lblSubtitulo
        );

        JPanel panelBotones =
                new JPanel(
                        new GridLayout(
                                5,
                                1,
                                15,
                                15
                        )
                );

        btnRegistrarPedido =
                new JButton(
                        "Registrar Pedido"
                );

        btnGestionarRepartidores =
                new JButton(
                        "Gestionar Repartidores"
                );

        btnGestionarPedidos =
                new JButton(
                        "Gestionar Pedidos"
                );

        btnGestionarEntregas =
                new JButton(
                        "Gestionar Entregas"
                );

        btnSalir =
                new JButton(
                        "Salir"
                );

        btnRegistrarPedido.addActionListener(
                e -> abrirRegistroPedido()
        );

        btnGestionarRepartidores.addActionListener(
                e -> abrirGestionRepartidores()
        );

        btnGestionarPedidos.addActionListener(
                e -> abrirGestionPedidos()
        );

        btnGestionarEntregas.addActionListener(
                e -> abrirGestionEntregas()
        );

        btnSalir.addActionListener(
                e -> salirAplicacion()
        );

        panelBotones.add(
                btnRegistrarPedido
        );

        panelBotones.add(
                btnGestionarRepartidores
        );

        panelBotones.add(
                btnGestionarPedidos
        );

        panelBotones.add(
                btnGestionarEntregas
        );

        panelBotones.add(
                btnSalir
        );

        panelPrincipal.add(
                panelTitulos,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                panelBotones,
                BorderLayout.CENTER
        );

        add(
                panelPrincipal
        );
    }

    private void abrirRegistroPedido() {

        VentanaRegistroPedido ventanaRegistro =
                new VentanaRegistroPedido(
                        gestorPedidos
                );

        ventanaRegistro.setVisible(
                true
        );
    }

    private void abrirGestionRepartidores() {

        VentanaGestionRepartidores ventanaGestion =
                new VentanaGestionRepartidores();

        ventanaGestion.setVisible(
                true
        );
    }

    private void abrirGestionPedidos() {

        VentanaListaPedidos ventanaGestion =
                new VentanaListaPedidos(
                        gestorPedidos
                );

        ventanaGestion.setVisible(
                true
        );
    }

    private void abrirGestionEntregas() {

        VentanaGestionEntregas ventanaGestion =
                new VentanaGestionEntregas();

        ventanaGestion.setVisible(
                true
        );
    }

    private void salirAplicacion() {

        int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea salir de SpeedFast?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (
                opcion
                        == JOptionPane.YES_OPTION
        ) {

            System.exit(0);
        }
    }
}