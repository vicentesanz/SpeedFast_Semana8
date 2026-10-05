package vista;

import dao.PedidoDAO;
import modelo.EstadoPedido;
import modelo.GestorPedidos;
import modelo.Pedido;
import modelo.PedidoComida;
import modelo.PedidoEncomienda;
import modelo.PedidoExpress;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListaPedidos extends JFrame {

    private final PedidoDAO pedidoDAO;

    private JTextField txtDireccion;
    private JComboBox<String> cmbTipo;
    private JComboBox<EstadoPedido> cmbEstado;

    private JComboBox<String> cmbFiltroTipo;
    private JComboBox<String> cmbFiltroEstado;

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnVolver;

    public VentanaListaPedidos(GestorPedidos gestorPedidos) {

        pedidoDAO = new PedidoDAO();

        setTitle("SpeedFast - Gestión de Pedidos");
        setSize(900, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        inicializarComponentes();
        actualizarTabla();
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        JLabel lblTitulo =
                new JLabel(
                        "Gestión de Pedidos",
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
                                3,
                                2,
                                10,
                                10
                        )
                );

        JLabel lblDireccion =
                new JLabel("Dirección:");

        JLabel lblTipo =
                new JLabel("Tipo:");

        JLabel lblEstado =
                new JLabel("Estado:");

        txtDireccion =
                new JTextField();

        cmbTipo =
                new JComboBox<>(
                        new String[]{
                                "Comida",
                                "Encomienda",
                                "Express"
                        }
                );

        cmbEstado =
                new JComboBox<>(
                        EstadoPedido.values()
                );

        panelFormulario.add(lblDireccion);
        panelFormulario.add(txtDireccion);

        panelFormulario.add(lblTipo);
        panelFormulario.add(cmbTipo);

        panelFormulario.add(lblEstado);
        panelFormulario.add(cmbEstado);

        JPanel panelFiltros =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                10,
                                10
                        )
                );

        JLabel lblFiltroTipo =
                new JLabel("Filtrar por tipo:");

        JLabel lblFiltroEstado =
                new JLabel("Filtrar por estado:");

        cmbFiltroTipo =
                new JComboBox<>(
                        new String[]{
                                "Todos",
                                "Comida",
                                "Encomienda",
                                "Express"
                        }
                );

        cmbFiltroEstado =
                new JComboBox<>(
                        new String[]{
                                "Todos",
                                "PENDIENTE",
                                "EN_REPARTO",
                                "ENTREGADO"
                        }
                );

        cmbFiltroTipo.addActionListener(
                e -> actualizarTabla()
        );

        cmbFiltroEstado.addActionListener(
                e -> actualizarTabla()
        );

        panelFiltros.add(lblFiltroTipo);
        panelFiltros.add(cmbFiltroTipo);
        panelFiltros.add(lblFiltroEstado);
        panelFiltros.add(cmbFiltroEstado);

        JPanel panelSuperior =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panelSuperior.add(
                lblTitulo,
                BorderLayout.NORTH
        );

        panelSuperior.add(
                panelFormulario,
                BorderLayout.CENTER
        );

        panelSuperior.add(
                panelFiltros,
                BorderLayout.SOUTH
        );

        modeloTabla =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Dirección",
                                "Tipo",
                                "Repartidor",
                                "Estado"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        tablaPedidos =
                new JTable(
                        modeloTabla
                );

        tablaPedidos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tablaPedidos
                .getSelectionModel()
                .addListSelectionListener(
                        e -> cargarSeleccion()
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        tablaPedidos
                );

        btnRegistrar =
                new JButton("Registrar");

        btnActualizar =
                new JButton("Actualizar");

        btnEliminar =
                new JButton("Eliminar");

        btnLimpiar =
                new JButton("Limpiar");

        btnVolver =
                new JButton("Volver");

        btnRegistrar.addActionListener(
                e -> registrarPedido()
        );

        btnActualizar.addActionListener(
                e -> actualizarPedido()
        );

        btnEliminar.addActionListener(
                e -> eliminarPedido()
        );

        btnLimpiar.addActionListener(
                e -> limpiarSeleccion()
        );

        btnVolver.addActionListener(
                e -> dispose()
        );

        JPanel panelBotones =
                new JPanel(
                        new GridLayout(
                                1,
                                5,
                                10,
                                0
                        )
                );

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);

        panelPrincipal.add(
                panelSuperior,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                scrollPane,
                BorderLayout.CENTER
        );

        panelPrincipal.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        add(panelPrincipal);
    }

    public void actualizarTabla() {

        if (modeloTabla == null) {
            return;
        }

        modeloTabla.setRowCount(0);

        List<Pedido> pedidos =
                pedidoDAO.listarTodos();

        String filtroTipo =
                cmbFiltroTipo != null
                        ? (String) cmbFiltroTipo.getSelectedItem()
                        : "Todos";

        String filtroEstado =
                cmbFiltroEstado != null
                        ? (String) cmbFiltroEstado.getSelectedItem()
                        : "Todos";

        for (Pedido pedido : pedidos) {

            String tipo =
                    pedido
                            .getClass()
                            .getSimpleName()
                            .replace(
                                    "Pedido",
                                    ""
                            );

            String estado =
                    pedido
                            .getEstado()
                            .name();

            boolean cumpleTipo =
                    filtroTipo == null
                            || filtroTipo.equals("Todos")
                            || filtroTipo.equals(tipo);

            boolean cumpleEstado =
                    filtroEstado == null
                            || filtroEstado.equals("Todos")
                            || filtroEstado.equals(estado);

            if (
                    !cumpleTipo
                            || !cumpleEstado
            ) {
                continue;
            }

            modeloTabla.addRow(
                    new Object[]{
                            pedido.getId(),
                            pedido.getDireccionEntrega(),
                            tipo,
                            pedido.getRepartidor(),
                            pedido.getEstado()
                    }
            );
        }
    }

    private void cargarSeleccion() {

        int fila =
                tablaPedidos.getSelectedRow();

        if (fila == -1) {
            return;
        }

        txtDireccion.setText(
                modeloTabla
                        .getValueAt(
                                fila,
                                1
                        )
                        .toString()
        );

        String tipo =
                modeloTabla
                        .getValueAt(
                                fila,
                                2
                        )
                        .toString();

        cmbTipo.setSelectedItem(tipo);

        String estado =
                modeloTabla
                        .getValueAt(
                                fila,
                                4
                        )
                        .toString();

        cmbEstado.setSelectedItem(
                EstadoPedido.valueOf(
                        estado
                )
        );
    }

    private void registrarPedido() {

        String direccion =
                txtDireccion
                        .getText()
                        .trim();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar una dirección.",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Pedido pedido =
                crearPedido(
                        0,
                        direccion
                );

        if (pedido == null) {
            return;
        }

        EstadoPedido estado =
                (EstadoPedido)
                        cmbEstado.getSelectedItem();

        pedido.setEstado(estado);

        if (pedidoDAO.guardar(pedido)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido registrado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            actualizarTabla();
            limpiarSeleccion();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el pedido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void actualizarPedido() {

        int fila =
                tablaPedidos.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un pedido de la tabla.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String direccion =
                txtDireccion
                        .getText()
                        .trim();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "La dirección no puede estar vacía.",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id =
                Integer.parseInt(
                        modeloTabla
                                .getValueAt(
                                        fila,
                                        0
                                )
                                .toString()
                );

        Pedido pedido =
                crearPedido(
                        id,
                        direccion
                );

        if (pedido == null) {
            return;
        }

        EstadoPedido estado =
                (EstadoPedido)
                        cmbEstado.getSelectedItem();

        pedido.setEstado(estado);

        if (pedidoDAO.actualizar(pedido)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido actualizado correctamente.",
                    "Actualización exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            actualizarTabla();
            limpiarSeleccion();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el pedido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarPedido() {

        int fila =
                tablaPedidos.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un pedido de la tabla.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id =
                Integer.parseInt(
                        modeloTabla
                                .getValueAt(
                                        fila,
                                        0
                                )
                                .toString()
                );

        int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea eliminar el pedido #"
                                + id
                                + "?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                opcion
                        != JOptionPane.YES_OPTION
        ) {
            return;
        }

        if (pedidoDAO.eliminar(id)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido eliminado correctamente.",
                    "Eliminación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            actualizarTabla();
            limpiarSeleccion();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el pedido. "
                            + "Verifique si está asociado a una entrega.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private Pedido crearPedido(
            int id,
            String direccion
    ) {

        String tipo =
                (String)
                        cmbTipo.getSelectedItem();

        if (tipo == null) {
            return null;
        }

        switch (tipo) {

            case "Comida":

                return new PedidoComida(
                        id,
                        direccion,
                        0.0
                );

            case "Encomienda":

                return new PedidoEncomienda(
                        id,
                        direccion,
                        0.0
                );

            case "Express":

                return new PedidoExpress(
                        id,
                        direccion,
                        0.0
                );

            default:

                return null;
        }
    }

    private void limpiarSeleccion() {

        tablaPedidos.clearSelection();

        txtDireccion.setText("");

        cmbTipo.setSelectedIndex(0);

        cmbEstado.setSelectedItem(
                EstadoPedido.PENDIENTE
        );

        txtDireccion.requestFocus();
    }
}