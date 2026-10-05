package vista;

import dao.RepartidorDAO;
import modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionRepartidores extends JFrame {

    private final RepartidorDAO repartidorDAO;

    private JTextField txtNombre;
    private JTable tablaRepartidores;
    private DefaultTableModel modeloTabla;

    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnVolver;

    public VentanaGestionRepartidores() {

        repartidorDAO = new RepartidorDAO();

        setTitle("SpeedFast - Gestión de Repartidores");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        inicializarComponentes();
        cargarTabla();
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
                        "Gestión de Repartidores",
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
                                1,
                                2,
                                10,
                                10
                        )
                );

        JLabel lblNombre =
                new JLabel(
                        "Nombre:"
                );

        txtNombre =
                new JTextField();

        panelFormulario.add(
                lblNombre
        );

        panelFormulario.add(
                txtNombre
        );

        modeloTabla =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Nombre"
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

        tablaRepartidores =
                new JTable(
                        modeloTabla
                );

        tablaRepartidores.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tablaRepartidores
                .getSelectionModel()
                .addListSelectionListener(
                        e -> cargarSeleccion()
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        tablaRepartidores
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
                e -> registrarRepartidor()
        );

        btnActualizar.addActionListener(
                e -> actualizarRepartidor()
        );

        btnEliminar.addActionListener(
                e -> eliminarRepartidor()
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

    private void cargarTabla() {

        modeloTabla.setRowCount(
                0
        );

        List<Repartidor> repartidores =
                repartidorDAO.listarTodos();

        for (Repartidor repartidor : repartidores) {

            modeloTabla.addRow(
                    new Object[]{
                            repartidor.getId(),
                            repartidor.getNombre()
                    }
            );
        }
    }

    private void cargarSeleccion() {

        int fila =
                tablaRepartidores.getSelectedRow();

        if (fila == -1) {
            return;
        }

        txtNombre.setText(
                modeloTabla
                        .getValueAt(
                                fila,
                                1
                        )
                        .toString()
        );
    }

    private void registrarRepartidor() {

        String nombre =
                txtNombre
                        .getText()
                        .trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el nombre del repartidor.",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Repartidor repartidor =
                new Repartidor(
                        nombre,
                        null
                );

        if (
                repartidorDAO.guardar(
                        repartidor
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor registrado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarTabla();
            limpiarSeleccion();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el repartidor.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void actualizarRepartidor() {

        int fila =
                tablaRepartidores.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un repartidor de la tabla.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String nombre =
                txtNombre
                        .getText()
                        .trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre no puede estar vacío.",
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

        Repartidor repartidor =
                new Repartidor(
                        id,
                        nombre,
                        null
                );

        if (
                repartidorDAO.actualizar(
                        repartidor
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor actualizado correctamente.",
                    "Actualización exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarTabla();
            limpiarSeleccion();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el repartidor.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarRepartidor() {

        int fila =
                tablaRepartidores.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un repartidor de la tabla.",
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

        String nombre =
                modeloTabla
                        .getValueAt(
                                fila,
                                1
                        )
                        .toString();

        int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea eliminar al repartidor "
                                + nombre
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
                repartidorDAO.eliminar(
                        id
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor eliminado correctamente.",
                    "Eliminación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarTabla();
            limpiarSeleccion();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el repartidor.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiarSeleccion() {

        tablaRepartidores.clearSelection();

        txtNombre.setText(
                ""
        );

        txtNombre.requestFocus();
    }
}