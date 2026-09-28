package modelo;

import java.util.ArrayList;

public abstract class Pedido implements Despachable, Cancelable, Rastreable {

    private int id;
    private String direccionEntrega;
    private double distanciaKm;
    private String repartidor;
    private EstadoPedido estado;
    private ArrayList<String> historial;

    public Pedido(int id, String direccionEntrega, double distanciaKm) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.repartidor = "Sin asignar";
        this.estado = EstadoPedido.PENDIENTE;
        this.historial = new ArrayList<>();

        historial.add("Pedido creado - Estado: PENDIENTE");
    }

    public int getIdPedido() {
        return id;
    }

    public int getId() {
        return id;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public String getRepartidor() {
        return repartidor;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public void setEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
        historial.add("Estado actualizado a: " + nuevoEstado);
    }

    public void setEstado(String nuevoEstado) {
        setEstado(
                EstadoPedido.valueOf(
                        nuevoEstado.trim().toUpperCase()
                )
        );
    }

    public void mostrarResumen() {
        System.out.println("Pedido #" + id);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");
        System.out.println("Repartidor asignado: " + repartidor);
        System.out.println("Estado: " + estado);
    }

    public void reservar() {
        estado = EstadoPedido.EN_REPARTO;
        historial.add("Pedido reservado");

        System.out.println(
                "Pedido #" + id + " reservado correctamente."
        );
    }

    @Override
    public void despachar() {
        estado = EstadoPedido.ENTREGADO;
        historial.add("Pedido entregado");

        System.out.println(
                "Pedido #" + id + " entregado correctamente."
        );
    }

    @Override
    public void cancelar() {
        historial.add("Pedido cancelado");

        System.out.println(
                "Pedido #" + id + " cancelado exitosamente."
        );
    }

    @Override
    public void verHistorial() {
        System.out.println(
                "=== HISTORIAL PEDIDO #" + id + " ==="
        );

        for (String registro : historial) {
            System.out.println("- " + registro);
        }

        System.out.println();
    }

    public abstract void asignarRepartidor();

    public void asignarRepartidor(String nombre) {
        this.repartidor = nombre;
        historial.add("Repartidor asignado: " + nombre);
    }

    public abstract int calcularTiempoEntrega();

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", direccionEntrega='" + direccionEntrega + '\'' +
                ", estado=" + estado +
                '}';
    }
}