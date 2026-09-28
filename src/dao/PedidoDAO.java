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

public class PedidoDAO {

    public boolean guardar(Pedido pedido) {

        String sql =
                "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet clavesGeneradas = null;

        try {

            conexion = ConexionBD.conectar();

            statement = conexion.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

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

                clavesGeneradas =
                        statement.getGeneratedKeys();

                if (clavesGeneradas.next()) {
                    pedido.setId(
                            clavesGeneradas.getInt(1)
                    );
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

        } finally {

            if (clavesGeneradas != null) {
                try {
                    clavesGeneradas.close();
                } catch (SQLException e) {
                    System.out.println(
                            "Error al cerrar ResultSet: "
                                    + e.getMessage()
                    );
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(
                            "Error al cerrar PreparedStatement: "
                                    + e.getMessage()
                    );
                }
            }

            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.out.println(
                            "Error al cerrar conexión: "
                                    + e.getMessage()
                    );
                }
            }
        }
    }

    private String obtenerTipo(Pedido pedido) {

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