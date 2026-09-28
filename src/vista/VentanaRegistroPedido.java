package vista;

import modelo.GestorPedidos;
import modelo.Pedido;
import modelo.PedidoComida;
import modelo.PedidoEncomienda;
import modelo.PedidoExpress;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {

    private final GestorPedidos gestorPedidos;

    private JTextField txtId;
    private JTextField txtDireccion;
    private JComboBox<String> cmbTipo;
    private JButton btnGuardar;
    private JButton btnVolver;

    public VentanaRegistroPedido(GestorPedidos gestorPedidos) {
        this.gestorPedidos = gestorPedidos;

        setTitle("SpeedFast - Registrar Pedido");
        setSize(450, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JLabel lblTitulo = new JLabel(
                "Registro de Pedido",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        JPanel panelFormulario = new JPanel(
                new GridLayout(3, 2, 10, 10)
        );

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField();

        JLabel lblDireccion = new JLabel("Dirección:");
        txtDireccion = new JTextField();

        JLabel lblTipo = new JLabel("Tipo de pedido:");

        cmbTipo = new JComboBox<>(new String[]{
                "Comida",
                "Encomienda",
                "Express"
        });

        panelFormulario.add(lblId);
        panelFormulario.add(txtId);

        panelFormulario.add(lblDireccion);
        panelFormulario.add(txtDireccion);

        panelFormulario.add(lblTipo);
        panelFormulario.add(cmbTipo);

        btnGuardar = new JButton("Guardar Pedido");
        btnVolver = new JButton("Volver al Menú");

        btnGuardar.addActionListener(e ->
                guardarPedido()
        );

        btnVolver.addActionListener(e ->
                dispose()
        );

        JPanel panelBotones = new JPanel(
                new GridLayout(1, 2, 10, 0)
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

        String idTexto =
                txtId.getText().trim();

        String direccion =
                txtDireccion.getText().trim();

        if (idTexto.isEmpty() || direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id;

        try {

            id = Integer.parseInt(idTexto);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un número entero.",
                    "ID inválido",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (id <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser mayor que cero.",
                    "ID inválido",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (gestorPedidos.existeId(id)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ya existe un pedido con ese ID.",
                    "ID duplicado",
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
                        id,
                        direccion,
                        0.0
                );
                break;

            case "Encomienda":
                pedido = new PedidoEncomienda(
                        id,
                        direccion,
                        0.0
                );
                break;

            case "Express":
                pedido = new PedidoExpress(
                        id,
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

        gestorPedidos.agregarPedido(pedido);

        JOptionPane.showMessageDialog(
                this,
                "Pedido registrado correctamente.",
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE
        );

        limpiarCampos();
    }

    private void limpiarCampos() {

        txtId.setText("");
        txtDireccion.setText("");

        cmbTipo.setSelectedIndex(0);

        txtId.requestFocus();
    }
}