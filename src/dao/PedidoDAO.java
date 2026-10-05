package dao;

import modelo.Pedido;
import modelo.PedidoComida;
import modelo.PedidoEncomienda;
import modelo.PedidoExpress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // Registra un nuevo pedido en la base de datos
    // y recupera el ID generado automáticamente.
    public boolean guardar(Pedido pedido) {

        String sql =
                "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setString(
                    1,
                    pedido.getDireccionEntrega()
            );

            statement.setString(
                    2,
                    obtenerTipo(pedido)
            );

            statement.setString(
                    3,
                    pedido.getEstado().name()
            );

            int filasAfectadas =
                    statement.executeUpdate();

            if (filasAfectadas > 0) {

                try (
                        ResultSet clavesGeneradas =
                                statement.getGeneratedKeys()
                ) {

                    if (clavesGeneradas.next()) {

                        pedido.setId(
                                clavesGeneradas.getInt(1)
                        );
                    }
                }

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar el pedido: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // Obtiene todos los pedidos almacenados y carga,
    // cuando corresponde, el repartidor asociado a la entrega.
    public List<Pedido> listarTodos() {

        List<Pedido> pedidos =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "p.id, " +
                        "p.direccion, " +
                        "p.tipo, " +
                        "p.estado, " +
                        "r.nombre AS repartidor " +
                        "FROM pedido p " +
                        "LEFT JOIN entrega e ON e.id = (" +
                        "SELECT MAX(e2.id) " +
                        "FROM entrega e2 " +
                        "WHERE e2.id_pedido = p.id" +
                        ") " +
                        "LEFT JOIN repartidor r " +
                        "ON r.id = e.id_repartidor " +
                        "ORDER BY p.id";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(sql);
                ResultSet resultado =
                        statement.executeQuery()
        ) {

            while (resultado.next()) {

                int id =
                        resultado.getInt("id");

                String direccion =
                        resultado.getString("direccion");

                String tipo =
                        resultado.getString("tipo");

                String estado =
                        resultado.getString("estado");

                String repartidor =
                        resultado.getString("repartidor");

                Pedido pedido;

                switch (tipo) {

                    case "COMIDA":

                        pedido = new PedidoComida(
                                id,
                                direccion,
                                0.0
                        );

                        break;

                    case "ENCOMIENDA":

                        pedido = new PedidoEncomienda(
                                id,
                                direccion,
                                0.0
                        );

                        break;

                    case "EXPRESS":

                        pedido = new PedidoExpress(
                                id,
                                direccion,
                                0.0
                        );

                        break;

                    default:

                        System.out.println(
                                "Tipo de pedido desconocido: "
                                        + tipo
                        );

                        continue;
                }

                pedido.setEstado(
                        estado
                );

                if (
                        repartidor != null
                                && !repartidor.isBlank()
                ) {

                    pedido.asignarRepartidor(
                            repartidor
                    );
                }

                pedidos.add(
                        pedido
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar los pedidos: "
                            + e.getMessage()
            );
        }

        return pedidos;
    }

    // Actualiza la dirección, el tipo y el estado
    // de un pedido existente.
    public boolean actualizar(Pedido pedido) {

        String sql =
                "UPDATE pedido " +
                        "SET direccion = ?, tipo = ?, estado = ? " +
                        "WHERE id = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    pedido.getDireccionEntrega()
            );

            statement.setString(
                    2,
                    obtenerTipo(pedido)
            );

            statement.setString(
                    3,
                    pedido.getEstado().name()
            );

            statement.setInt(
                    4,
                    pedido.getId()
            );

            int filasAfectadas =
                    statement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar el pedido: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // Elimina un pedido según su ID.
    public boolean eliminar(int id) {

        String sql =
                "DELETE FROM pedido WHERE id = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    id
            );

            int filasAfectadas =
                    statement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar el pedido: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // Actualiza únicamente el estado del pedido.
    // Se utiliza, por ejemplo, al registrar una entrega.
    public boolean actualizarEstado(
            int idPedido,
            String nuevoEstado
    ) {

        String sql =
                "UPDATE pedido SET estado = ? WHERE id = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    nuevoEstado
            );

            statement.setInt(
                    2,
                    idPedido
            );

            int filasAfectadas =
                    statement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar el estado del pedido: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // Determina el tipo de pedido según la subclase utilizada.
    private String obtenerTipo(
            Pedido pedido
    ) {

        if (pedido instanceof PedidoComida) {

            return "COMIDA";
        }

        if (pedido instanceof PedidoEncomienda) {

            return "ENCOMIENDA";
        }

        if (pedido instanceof PedidoExpress) {

            return "EXPRESS";
        }

        throw new IllegalArgumentException(
                "Tipo de pedido no reconocido."
        );
    }
}