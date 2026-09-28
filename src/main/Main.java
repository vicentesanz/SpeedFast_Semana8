package main;

import dao.ConexionBD;
import vista.VentanaPrincipal;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        Connection conexion = null;

        try {

            conexion = ConexionBD.conectar();

            System.out.println(
                    "Conexión a speedfast_db realizada correctamente."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo conectar a la base de datos:\n"
                            + e.getMessage(),
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );

            return;

        } finally {

            if (conexion != null) {

                try {

                    conexion.close();

                } catch (SQLException e) {

                    System.out.println(
                            "Error al cerrar la conexión: "
                                    + e.getMessage()
                    );
                }
            }
        }

        SwingUtilities.invokeLater(() -> {

            VentanaPrincipal ventanaPrincipal =
                    new VentanaPrincipal();

            ventanaPrincipal.setVisible(true);
        });
    }
}