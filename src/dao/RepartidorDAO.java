package dao;

import modelo.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    public boolean guardar(Repartidor repartidor) {

        String sql =
                "INSERT INTO repartidor (nombre) VALUES (?)";

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
                    repartidor.getNombre()
            );

            int filasAfectadas =
                    statement.executeUpdate();

            if (filasAfectadas > 0) {

                try (
                        ResultSet clavesGeneradas =
                                statement.getGeneratedKeys()
                ) {

                    if (clavesGeneradas.next()) {
                        repartidor.setId(
                                clavesGeneradas.getInt(1)
                        );
                    }
                }

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar el repartidor: "
                            + e.getMessage()
            );

            return false;
        }
    }

    public List<Repartidor> listarTodos() {

        List<Repartidor> repartidores =
                new ArrayList<>();

        String sql =
                "SELECT id, nombre FROM repartidor ORDER BY id";

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

                String nombre =
                        resultado.getString("nombre");

                Repartidor repartidor =
                        new Repartidor(
                                id,
                                nombre,
                                null
                        );

                repartidores.add(repartidor);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar los repartidores: "
                            + e.getMessage()
            );
        }

        return repartidores;
    }
}