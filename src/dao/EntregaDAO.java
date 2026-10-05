package dao;

import modelo.Entrega;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    // Registra una nueva entrega asociando
    // un pedido y un repartidor con fecha y hora.
    public boolean guardar(Entrega entrega) {

        String sql =
                "INSERT INTO entrega " +
                        "(id_pedido, id_repartidor, fecha, hora) " +
                        "VALUES (?, ?, ?, ?)";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setInt(
                    1,
                    entrega.getIdPedido()
            );

            statement.setInt(
                    2,
                    entrega.getIdRepartidor()
            );

            statement.setDate(
                    3,
                    Date.valueOf(entrega.getFecha())
            );

            statement.setTime(
                    4,
                    Time.valueOf(entrega.getHora())
            );

            int filasAfectadas =
                    statement.executeUpdate();

            if (filasAfectadas > 0) {

                try (
                        ResultSet clavesGeneradas =
                                statement.getGeneratedKeys()
                ) {

                    if (clavesGeneradas.next()) {
                        entrega.setId(
                                clavesGeneradas.getInt(1)
                        );
                    }
                }

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar la entrega: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // Obtiene todas las entregas almacenadas
    // y las transforma en objetos Entrega.
    public List<Entrega> listarTodas() {

        List<Entrega> entregas =
                new ArrayList<>();

        String sql =
                "SELECT id, id_pedido, id_repartidor, fecha, hora " +
                        "FROM entrega ORDER BY id";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(sql);
                ResultSet resultado =
                        statement.executeQuery()
        ) {

            while (resultado.next()) {

                Entrega entrega =
                        new Entrega(
                                resultado.getInt("id"),
                                resultado.getInt("id_pedido"),
                                resultado.getInt("id_repartidor"),
                                resultado.getDate("fecha").toLocalDate(),
                                resultado.getTime("hora").toLocalTime()
                        );

                entregas.add(
                        entrega
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar las entregas: "
                            + e.getMessage()
            );
        }

        return entregas;
    }

    // Actualiza el pedido, repartidor, fecha y hora
    // de una entrega existente.
    public boolean actualizar(Entrega entrega) {

        String sql =
                "UPDATE entrega " +
                        "SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ? " +
                        "WHERE id = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    entrega.getIdPedido()
            );

            statement.setInt(
                    2,
                    entrega.getIdRepartidor()
            );

            statement.setDate(
                    3,
                    Date.valueOf(entrega.getFecha())
            );

            statement.setTime(
                    4,
                    Time.valueOf(entrega.getHora())
            );

            statement.setInt(
                    5,
                    entrega.getId()
            );

            int filasAfectadas =
                    statement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar la entrega: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // Elimina una entrega según su ID.
    public boolean eliminar(int id) {

        String sql =
                "DELETE FROM entrega WHERE id = ?";

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
                    "Error al eliminar la entrega: "
                            + e.getMessage()
            );

            return false;
        }
    }
}