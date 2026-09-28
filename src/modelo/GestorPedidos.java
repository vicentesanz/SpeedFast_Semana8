package modelo;

import java.util.ArrayList;
import java.util.List;

public class GestorPedidos {

    private final ArrayList<Pedido> pedidos;

    public GestorPedidos() {
        pedidos = new ArrayList<>();
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public boolean existeId(int id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return true;
            }
        }

        return false;
    }
}