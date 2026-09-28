package modelo;

import java.util.ArrayList;

public class ControladorDeEnvios implements Rastreable {

    private ArrayList<String> historial;

    public ControladorDeEnvios() {
        historial = new ArrayList<>();
    }

    public void registrarEntrega(Pedido pedido) {
        String registro = "- "
                + pedido.getClass().getSimpleName()
                + " #" + pedido.getIdPedido()
                + " - entregado por "
                + pedido.getRepartidor();

        historial.add(registro);
    }

    @Override
    public void verHistorial() {
        System.out.println("=== HISTORIAL DE ENTREGAS ===");

        if (historial.isEmpty()) {
            System.out.println("No hay entregas registradas.");
        } else {
            for (String entrega : historial) {
                System.out.println(entrega);
            }
        }

        System.out.println();
    }
}