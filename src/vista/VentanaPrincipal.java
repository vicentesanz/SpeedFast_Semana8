package vista;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import modelo.Entrega;
import modelo.EstadoPedido;
import modelo.GestorPedidos;
import modelo.Pedido;
import modelo.Repartidor;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private final GestorPedidos gestorPedidos;

    private final PedidoDAO pedidoDAO;
    private final RepartidorDAO repartidorDAO;
    private final EntregaDAO entregaDAO;

    private JButton btnRegistrarPedido;
    private JButton btnGestionarRepartidores;
    private JButton btnListarPedidos;
    private JButton btnIniciarEntrega;
    private JButton btnSalir;

    public VentanaPrincipal() {

        gestorPedidos = new GestorPedidos();

        pedidoDAO = new PedidoDAO();
        repartidorDAO = new RepartidorDAO();
        entregaDAO = new EntregaDAO();

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

        btnListarPedidos =
                new JButton(
                        "Listar Pedidos"
                );

        btnIniciarEntrega =
                new JButton(
                        "Asignar Repartidor / Iniciar Entrega"
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

        btnListarPedidos.addActionListener(
                e -> abrirListaPedidos()
        );

        btnIniciarEntrega.addActionListener(
                e -> iniciarEntrega()
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
                btnListarPedidos
        );

        panelBotones.add(
                btnIniciarEntrega
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

    private void abrirListaPedidos() {

        VentanaListaPedidos ventanaLista =
                new VentanaListaPedidos(
                        gestorPedidos
                );

        ventanaLista.setVisible(
                true
        );
    }

    private void iniciarEntrega() {

        List<Pedido> pedidos =
                pedidoDAO.listarTodos();

        List<Pedido> pedidosPendientes =
                new ArrayList<>();

        for (Pedido pedido : pedidos) {

            if (
                    pedido.getEstado()
                            == EstadoPedido.PENDIENTE
            ) {

                pedidosPendientes.add(
                        pedido
                );
            }
        }

        if (pedidosPendientes.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay pedidos pendientes para iniciar una entrega.",
                    "Sin pedidos pendientes",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        List<Repartidor> repartidores =
                repartidorDAO.listarTodos();

        if (repartidores.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay repartidores registrados en la base de datos.",
                    "Sin repartidores",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String[] opcionesPedidos =
                new String[
                        pedidosPendientes.size()
                        ];

        for (
                int i = 0;
                i < pedidosPendientes.size();
                i++
        ) {

            Pedido pedido =
                    pedidosPendientes.get(i);

            opcionesPedidos[i] =
                    "Pedido #"
                            + pedido.getId()
                            + " - "
                            + pedido.getDireccionEntrega();
        }

        String seleccionPedido =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Seleccione el pedido:",
                        "Iniciar Entrega",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opcionesPedidos,
                        opcionesPedidos[0]
                );

        if (seleccionPedido == null) {
            return;
        }

        int indicePedido = -1;

        for (
                int i = 0;
                i < opcionesPedidos.length;
                i++
        ) {

            if (
                    opcionesPedidos[i]
                            .equals(
                                    seleccionPedido
                            )
            ) {

                indicePedido = i;

                break;
            }
        }

        if (indicePedido == -1) {
            return;
        }

        Pedido pedidoSeleccionado =
                pedidosPendientes.get(
                        indicePedido
                );

        String[] opcionesRepartidores =
                new String[
                        repartidores.size()
                        ];

        for (
                int i = 0;
                i < repartidores.size();
                i++
        ) {

            Repartidor repartidor =
                    repartidores.get(i);

            opcionesRepartidores[i] =
                    "Repartidor #"
                            + repartidor.getId()
                            + " - "
                            + repartidor.getNombre();
        }

        String seleccionRepartidor =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Seleccione el repartidor:",
                        "Asignar Repartidor",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opcionesRepartidores,
                        opcionesRepartidores[0]
                );

        if (seleccionRepartidor == null) {
            return;
        }

        int indiceRepartidor = -1;

        for (
                int i = 0;
                i < opcionesRepartidores.length;
                i++
        ) {

            if (
                    opcionesRepartidores[i]
                            .equals(
                                    seleccionRepartidor
                            )
            ) {

                indiceRepartidor = i;

                break;
            }
        }

        if (indiceRepartidor == -1) {
            return;
        }

        Repartidor repartidorSeleccionado =
                repartidores.get(
                        indiceRepartidor
                );

        Entrega entrega =
                new Entrega(
                        pedidoSeleccionado.getId(),
                        repartidorSeleccionado.getId(),
                        LocalDate.now(),
                        LocalTime.now()
                                .withNano(0)
                );

        boolean entregaGuardada =
                entregaDAO.guardar(
                        entrega
                );

        if (!entregaGuardada) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar la entrega en la base de datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        boolean estadoActualizado =
                pedidoDAO.actualizarEstado(
                        pedidoSeleccionado.getId(),
                        EstadoPedido.EN_REPARTO.name()
                );

        if (!estadoActualizado) {

            JOptionPane.showMessageDialog(
                    this,
                    "La entrega fue registrada, pero no se pudo actualizar el estado del pedido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Entrega iniciada correctamente.\n"
                        + "Pedido: #"
                        + pedidoSeleccionado.getId()
                        + "\nRepartidor: "
                        + repartidorSeleccionado.getNombre()
                        + "\nEntrega: #"
                        + entrega.getId(),
                "Entrega iniciada",
                JOptionPane.INFORMATION_MESSAGE
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