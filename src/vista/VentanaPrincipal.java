package vista;

import modelo.EstadoPedido;
import modelo.GestorPedidos;
import modelo.Pedido;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {

    private final GestorPedidos gestorPedidos;

    private JButton btnRegistrarPedido;
    private JButton btnListarPedidos;
    private JButton btnIniciarEntrega;
    private JButton btnSalir;

    public VentanaPrincipal() {

        gestorPedidos = new GestorPedidos();

        setTitle("SpeedFast - Sistema de Reparto");
        setSize(500, 410);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(20, 20));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        );

        JLabel lblTitulo = new JLabel(
                "SpeedFast",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        JLabel lblSubtitulo = new JLabel(
                "Sistema de gestión de pedidos",
                SwingConstants.CENTER
        );

        lblSubtitulo.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        JPanel panelTitulos = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );

        panelTitulos.add(lblTitulo);
        panelTitulos.add(lblSubtitulo);

        JPanel panelBotones = new JPanel(
                new GridLayout(4, 1, 15, 15)
        );

        btnRegistrarPedido = new JButton(
                "Registrar Pedido"
        );

        btnListarPedidos = new JButton(
                "Listar Pedidos"
        );

        btnIniciarEntrega = new JButton(
                "Asignar Repartidor / Iniciar Entrega"
        );

        btnSalir = new JButton(
                "Salir"
        );

        btnRegistrarPedido.addActionListener(e ->
                abrirRegistroPedido()
        );

        btnListarPedidos.addActionListener(e ->
                abrirListaPedidos()
        );

        btnIniciarEntrega.addActionListener(e ->
                iniciarEntrega()
        );

        btnSalir.addActionListener(e ->
                salirAplicacion()
        );

        panelBotones.add(btnRegistrarPedido);
        panelBotones.add(btnListarPedidos);
        panelBotones.add(btnIniciarEntrega);
        panelBotones.add(btnSalir);

        panelPrincipal.add(
                panelTitulos,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                panelBotones,
                BorderLayout.CENTER
        );

        add(panelPrincipal);
    }

    private void abrirRegistroPedido() {

        VentanaRegistroPedido ventanaRegistro =
                new VentanaRegistroPedido(gestorPedidos);

        ventanaRegistro.setVisible(true);
    }

    private void abrirListaPedidos() {

        VentanaListaPedidos ventanaLista =
                new VentanaListaPedidos(gestorPedidos);

        ventanaLista.setVisible(true);
    }

    private void iniciarEntrega() {

        if (gestorPedidos.getPedidos().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay pedidos registrados.",
                    "Sin pedidos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        ArrayList<Pedido> pedidosPendientes =
                new ArrayList<>();

        for (Pedido pedido : gestorPedidos.getPedidos()) {

            if (pedido.getEstado() == EstadoPedido.PENDIENTE) {
                pedidosPendientes.add(pedido);
            }
        }

        if (pedidosPendientes.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay pedidos pendientes para iniciar entrega.",
                    "Sin pedidos pendientes",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        String[] opciones =
                new String[pedidosPendientes.size()];

        for (int i = 0; i < pedidosPendientes.size(); i++) {

            Pedido pedido = pedidosPendientes.get(i);

            opciones[i] =
                    "Pedido #" + pedido.getId()
                            + " - "
                            + pedido.getDireccionEntrega();
        }

        String seleccion = (String)
                JOptionPane.showInputDialog(
                        this,
                        "Seleccione el pedido:",
                        "Iniciar Entrega",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );

        if (seleccion == null) {
            return;
        }

        int indiceSeleccionado = -1;

        for (int i = 0; i < opciones.length; i++) {

            if (opciones[i].equals(seleccion)) {
                indiceSeleccionado = i;
                break;
            }
        }

        if (indiceSeleccionado == -1) {
            return;
        }

        Pedido pedidoSeleccionado =
                pedidosPendientes.get(indiceSeleccionado);

        pedidoSeleccionado.asignarRepartidor();

        pedidoSeleccionado.setEstado(
                EstadoPedido.EN_REPARTO
        );

        JOptionPane.showMessageDialog(
                this,
                "Pedido #" + pedidoSeleccionado.getId()
                        + " iniciado correctamente.\n"
                        + "Repartidor: "
                        + pedidoSeleccionado.getRepartidor(),
                "Entrega iniciada",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void salirAplicacion() {

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea salir de SpeedFast?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}