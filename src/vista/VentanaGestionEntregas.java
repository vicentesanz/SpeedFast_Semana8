package vista;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import modelo.Entrega;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class VentanaGestionEntregas extends JFrame {

    private final EntregaDAO entregaDAO;
    private final PedidoDAO pedidoDAO;
    private final RepartidorDAO repartidorDAO;

    private List<Pedido> pedidos;
    private List<Repartidor> repartidores;

    private JComboBox<String> cmbPedido;
    private JComboBox<String> cmbRepartidor;

    private JTextField txtFecha;
    private JTextField txtHora;

    private JTable tablaEntregas;
    private DefaultTableModel modeloTabla;

    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnVolver;

    public VentanaGestionEntregas() {

        entregaDAO = new EntregaDAO();
        pedidoDAO = new PedidoDAO();
        repartidorDAO = new RepartidorDAO();

        setTitle("SpeedFast - Gestión de Entregas");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        inicializarComponentes();
        cargarDatos();
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
                        "Gestión de Entregas",
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
                                4,
                                2,
                                10,
                                10
                        )
                );

        JLabel lblPedido =
                new JLabel(
                        "Pedido:"
                );

        JLabel lblRepartidor =
                new JLabel(
                        "Repartidor:"
                );

        JLabel lblFecha =
                new JLabel(
                        "Fecha (AAAA-MM-DD):"
                );

        JLabel lblHora =
                new JLabel(
                        "Hora (HH:MM:SS):"
                );

        cmbPedido =
                new JComboBox<>();

        cmbRepartidor =
                new JComboBox<>();

        txtFecha =
                new JTextField();

        txtHora =
                new JTextField();

        panelFormulario.add(
                lblPedido
        );

        panelFormulario.add(
                cmbPedido
        );

        panelFormulario.add(
                lblRepartidor
        );

        panelFormulario.add(
                cmbRepartidor
        );

        panelFormulario.add(
                lblFecha
        );

        panelFormulario.add(
                txtFecha
        );

        panelFormulario.add(
                lblHora
        );

        panelFormulario.add(
                txtHora
        );

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

        modeloTabla =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Pedido",
                                "Repartidor",
                                "Fecha",
                                "Hora"
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

        tablaEntregas =
                new JTable(
                        modeloTabla
                );

        tablaEntregas.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tablaEntregas
                .getSelectionModel()
                .addListSelectionListener(
                        e -> cargarSeleccion()
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        tablaEntregas
                );

        btnRegistrar =
                new JButton(
                        "Registrar"
                );

        btnActualizar =
                new JButton(
                        "Actualizar"
                );

        btnEliminar =
                new JButton(
                        "Eliminar"
                );

        btnLimpiar =
                new JButton(
                        "Limpiar"
                );

        btnVolver =
                new JButton(
                        "Volver"
                );

        btnRegistrar.addActionListener(
                e -> registrarEntrega()
        );

        btnActualizar.addActionListener(
                e -> actualizarEntrega()
        );

        btnEliminar.addActionListener(
                e -> eliminarEntrega()
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

        panelBotones.add(
                btnRegistrar
        );

        panelBotones.add(
                btnActualizar
        );

        panelBotones.add(
                btnEliminar
        );

        panelBotones.add(
                btnLimpiar
        );

        panelBotones.add(
                btnVolver
        );

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

        add(
                panelPrincipal
        );
    }

    private void cargarDatos() {

        cargarCombos();
        cargarTabla();
        limpiarSeleccion();
    }

    private void cargarCombos() {

        pedidos =
                pedidoDAO.listarTodos();

        repartidores =
                repartidorDAO.listarTodos();

        cmbPedido.removeAllItems();
        cmbRepartidor.removeAllItems();

        for (Pedido pedido : pedidos) {

            cmbPedido.addItem(
                    "Pedido #"
                            + pedido.getId()
                            + " - "
                            + pedido.getDireccionEntrega()
            );
        }

        for (Repartidor repartidor : repartidores) {

            cmbRepartidor.addItem(
                    "Repartidor #"
                            + repartidor.getId()
                            + " - "
                            + repartidor.getNombre()
            );
        }
    }

    private void cargarTabla() {

        modeloTabla.setRowCount(
                0
        );

        List<Entrega> entregas =
                entregaDAO.listarTodas();

        for (Entrega entrega : entregas) {

            String textoPedido =
                    obtenerTextoPedido(
                            entrega.getIdPedido()
                    );

            String textoRepartidor =
                    obtenerTextoRepartidor(
                            entrega.getIdRepartidor()
                    );

            modeloTabla.addRow(
                    new Object[]{
                            entrega.getId(),
                            textoPedido,
                            textoRepartidor,
                            entrega.getFecha(),
                            entrega.getHora()
                    }
            );
        }
    }

    private String obtenerTextoPedido(
            int idPedido
    ) {

        for (Pedido pedido : pedidos) {

            if (
                    pedido.getId()
                            == idPedido
            ) {

                return "#"
                        + pedido.getId()
                        + " - "
                        + pedido.getDireccionEntrega();
            }
        }

        return "#"
                + idPedido;
    }

    private String obtenerTextoRepartidor(
            int idRepartidor
    ) {

        for (Repartidor repartidor : repartidores) {

            if (
                    repartidor.getId()
                            == idRepartidor
            ) {

                return "#"
                        + repartidor.getId()
                        + " - "
                        + repartidor.getNombre();
            }
        }

        return "#"
                + idRepartidor;
    }

    private void cargarSeleccion() {

        int fila =
                tablaEntregas.getSelectedRow();

        if (fila == -1) {
            return;
        }

        int idEntrega =
                Integer.parseInt(
                        modeloTabla
                                .getValueAt(
                                        fila,
                                        0
                                )
                                .toString()
                );

        List<Entrega> entregas =
                entregaDAO.listarTodas();

        for (Entrega entrega : entregas) {

            if (
                    entrega.getId()
                            == idEntrega
            ) {

                seleccionarPedido(
                        entrega.getIdPedido()
                );

                seleccionarRepartidor(
                        entrega.getIdRepartidor()
                );

                txtFecha.setText(
                        entrega.getFecha()
                                .toString()
                );

                txtHora.setText(
                        entrega.getHora()
                                .toString()
                );

                break;
            }
        }
    }

    private void seleccionarPedido(
            int idPedido
    ) {

        for (
                int i = 0;
                i < pedidos.size();
                i++
        ) {

            if (
                    pedidos.get(i)
                            .getId()
                            == idPedido
            ) {

                cmbPedido.setSelectedIndex(
                        i
                );

                break;
            }
        }
    }

    private void seleccionarRepartidor(
            int idRepartidor
    ) {

        for (
                int i = 0;
                i < repartidores.size();
                i++
        ) {

            if (
                    repartidores.get(i)
                            .getId()
                            == idRepartidor
            ) {

                cmbRepartidor.setSelectedIndex(
                        i
                );

                break;
            }
        }
    }

    private void registrarEntrega() {

        if (!validarDatos()) {
            return;
        }

        Pedido pedido =
                pedidos.get(
                        cmbPedido.getSelectedIndex()
                );

        Repartidor repartidor =
                repartidores.get(
                        cmbRepartidor.getSelectedIndex()
                );

        LocalDate fecha =
                LocalDate.parse(
                        txtFecha.getText().trim()
                );

        LocalTime hora =
                LocalTime.parse(
                        txtHora.getText().trim()
                );

        Entrega entrega =
                new Entrega(
                        pedido.getId(),
                        repartidor.getId(),
                        fecha,
                        hora
                );

        if (
                entregaDAO.guardar(
                        entrega
                )
        ) {

            pedidoDAO.actualizarEstado(
                    pedido.getId(),
                    EstadoPedido.EN_REPARTO.name()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega registrada correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarDatos();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar la entrega.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void actualizarEntrega() {

        int fila =
                tablaEntregas.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar una entrega de la tabla.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!validarDatos()) {
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
                pedidos.get(
                        cmbPedido.getSelectedIndex()
                );

        Repartidor repartidor =
                repartidores.get(
                        cmbRepartidor.getSelectedIndex()
                );

        LocalDate fecha =
                LocalDate.parse(
                        txtFecha.getText().trim()
                );

        LocalTime hora =
                LocalTime.parse(
                        txtHora.getText().trim()
                );

        Entrega entrega =
                new Entrega(
                        id,
                        pedido.getId(),
                        repartidor.getId(),
                        fecha,
                        hora
                );

        if (
                entregaDAO.actualizar(
                        entrega
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega actualizada correctamente.",
                    "Actualización exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarDatos();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar la entrega.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarEntrega() {

        int fila =
                tablaEntregas.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar una entrega de la tabla.",
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
                        "¿Desea eliminar la entrega #"
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

        if (
                entregaDAO.eliminar(
                        id
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega eliminada correctamente.",
                    "Eliminación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarDatos();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar la entrega.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private boolean validarDatos() {

        if (
                pedidos.isEmpty()
                        || cmbPedido.getSelectedIndex()
                        == -1
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay pedidos disponibles.",
                    "Sin pedidos",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        if (
                repartidores.isEmpty()
                        || cmbRepartidor.getSelectedIndex()
                        == -1
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay repartidores disponibles.",
                    "Sin repartidores",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        if (
                txtFecha.getText()
                        .trim()
                        .isEmpty()
                        || txtHora.getText()
                        .trim()
                        .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar fecha y hora.",
                    "Campos obligatorios",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        try {

            LocalDate.parse(
                    txtFecha.getText().trim()
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La fecha debe tener formato AAAA-MM-DD.",
                    "Fecha inválida",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        try {

            LocalTime.parse(
                    txtHora.getText().trim()
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La hora debe tener formato HH:MM:SS.",
                    "Hora inválida",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        return true;
    }

    private void limpiarSeleccion() {

        tablaEntregas.clearSelection();

        if (!pedidos.isEmpty()) {
            cmbPedido.setSelectedIndex(0);
        }

        if (!repartidores.isEmpty()) {
            cmbRepartidor.setSelectedIndex(0);
        }

        txtFecha.setText(
                LocalDate.now().toString()
        );

        txtHora.setText(
                LocalTime.now()
                        .withNano(0)
                        .toString()
        );
    }
}