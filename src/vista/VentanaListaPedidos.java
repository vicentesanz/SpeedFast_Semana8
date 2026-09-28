package vista;

import dao.PedidoDAO;
import modelo.GestorPedidos;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListaPedidos extends JFrame {

    private final PedidoDAO pedidoDAO;

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;
    private JButton btnVolver;

    public VentanaListaPedidos(GestorPedidos gestorPedidos) {

        this.pedidoDAO = new PedidoDAO();

        setTitle("SpeedFast - Lista de Pedidos");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        actualizarTabla();
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
                "Pedidos Registrados",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        modeloTabla = new DefaultTableModel(
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
                new JTable(modeloTabla);

        tablaPedidos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(tablaPedidos);

        btnActualizar =
                new JButton("Actualizar Lista");

        btnVolver =
                new JButton("Volver al Menú");

        btnActualizar.addActionListener(
                e -> actualizarTabla()
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

        panelBotones.add(btnActualizar);
        panelBotones.add(btnVolver);

        panelPrincipal.add(
                lblTitulo,
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

        modeloTabla.setRowCount(0);

        List<Pedido> pedidos =
                pedidoDAO.listarTodos();

        for (Pedido pedido : pedidos) {

            String tipoPedido =
                    pedido
                            .getClass()
                            .getSimpleName()
                            .replace(
                                    "Pedido",
                                    ""
                            );

            modeloTabla.addRow(
                    new Object[]{
                            pedido.getId(),
                            pedido.getDireccionEntrega(),
                            tipoPedido,
                            pedido.getRepartidor(),
                            pedido.getEstado()
                    }
            );
        }
    }
}