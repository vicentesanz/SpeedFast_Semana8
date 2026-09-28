package vista;

import dao.PedidoDAO;
import modelo.GestorPedidos;
import modelo.Pedido;
import modelo.PedidoComida;
import modelo.PedidoEncomienda;
import modelo.PedidoExpress;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {

    private final GestorPedidos gestorPedidos;
    private final PedidoDAO pedidoDAO;

    private JTextField txtDireccion;
    private JComboBox<String> cmbTipo;
    private JButton btnGuardar;
    private JButton btnVolver;

    public VentanaRegistroPedido(GestorPedidos gestorPedidos) {

        this.gestorPedidos = gestorPedidos;
        this.pedidoDAO = new PedidoDAO();

        setTitle("SpeedFast - Registrar Pedido");
        setSize(450, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal =
                new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        JLabel lblTitulo = new JLabel(
                "Registro de Pedido",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        JPanel panelFormulario =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                10,
                                10
                        )
                );

        JLabel lblDireccion =
                new JLabel("Dirección:");

        txtDireccion =
                new JTextField();

        JLabel lblTipo =
                new JLabel("Tipo de pedido:");

        cmbTipo = new JComboBox<>(
                new String[]{
                        "Comida",
                        "Encomienda",
                        "Express"
                }
        );

        panelFormulario.add(lblDireccion);
        panelFormulario.add(txtDireccion);

        panelFormulario.add(lblTipo);
        panelFormulario.add(cmbTipo);

        btnGuardar =
                new JButton("Guardar Pedido");

        btnVolver =
                new JButton("Volver al Menú");

        btnGuardar.addActionListener(
                e -> guardarPedido()
        );

        btnVolver.addActionListener(
                e -> dispose()
        );

        JPanel panelBotones =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                10,
                                0
                        )
                );

        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);

        panelPrincipal.add(
                lblTitulo,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                panelFormulario,
                BorderLayout.CENTER
        );

        panelPrincipal.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        add(panelPrincipal);
    }

    private void guardarPedido() {

        String direccion =
                txtDireccion.getText().trim();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar una dirección.",
                    "Campo incompleto",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String tipo =
                (String) cmbTipo.getSelectedItem();

        Pedido pedido;

        switch (tipo) {

            case "Comida":

                pedido = new PedidoComida(
                        0,
                        direccion,
                        0.0
                );

                break;

            case "Encomienda":

                pedido = new PedidoEncomienda(
                        0,
                        direccion,
                        0.0
                );

                break;

            case "Express":

                pedido = new PedidoExpress(
                        0,
                        direccion,
                        0.0
                );

                break;

            default:

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un tipo de pedido.",
                        "Tipo inválido",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
        }

        boolean guardado =
                pedidoDAO.guardar(pedido);

        if (!guardado) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo guardar el pedido en la base de datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        gestorPedidos.agregarPedido(
                pedido
        );

        JOptionPane.showMessageDialog(
                this,
                "Pedido registrado correctamente.\n"
                        + "ID generado: "
                        + pedido.getId(),
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE
        );

        limpiarCampos();
    }

    private void limpiarCampos() {

        txtDireccion.setText("");

        cmbTipo.setSelectedIndex(0);

        txtDireccion.requestFocus();
    }
}