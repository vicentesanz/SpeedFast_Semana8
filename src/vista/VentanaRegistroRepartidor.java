package vista;

import dao.RepartidorDAO;
import modelo.Repartidor;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroRepartidor extends JFrame {

    private final RepartidorDAO repartidorDAO;

    private JTextField txtNombre;
    private JButton btnGuardar;
    private JButton btnVolver;

    public VentanaRegistroRepartidor() {

        repartidorDAO = new RepartidorDAO();

        setTitle("SpeedFast - Registrar Repartidor");
        setSize(420, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        inicializarComponentes();
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
                        "Registro de Repartidor",
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
                new JLabel("Nombre:");

        txtNombre =
                new JTextField();

        panelFormulario.add(
                lblNombre
        );

        panelFormulario.add(
                txtNombre
        );

        btnGuardar =
                new JButton(
                        "Guardar Repartidor"
                );

        btnVolver =
                new JButton(
                        "Volver al Menú"
                );

        btnGuardar.addActionListener(
                e -> guardarRepartidor()
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

        panelBotones.add(
                btnGuardar
        );

        panelBotones.add(
                btnVolver
        );

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

        add(
                panelPrincipal
        );
    }

    private void guardarRepartidor() {

        String nombre =
                txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el nombre del repartidor.",
                    "Campo incompleto",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Repartidor repartidor =
                new Repartidor(
                        nombre,
                        null
                );

        boolean guardado =
                repartidorDAO.guardar(
                        repartidor
                );

        if (!guardado) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo guardar el repartidor en la base de datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Repartidor registrado correctamente.\n"
                        + "ID generado: "
                        + repartidor.getId(),
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE
        );

        limpiarCampo();
    }

    private void limpiarCampo() {

        txtNombre.setText("");

        txtNombre.requestFocus();
    }
}