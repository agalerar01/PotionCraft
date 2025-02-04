package org.example.Jugador;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class InventarioPocion {

    public InventarioPocion() {
    }

    public InventarioPocion(Integer cantidad, Pocion pocion) {
        this.cantidad = cantidad;
        this.pocion = pocion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Integer cantidad;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "pocion_id")
    private Pocion pocion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Pocion getPocion() {
        return pocion;
    }

    public void setPocion(Pocion pocion) {
        this.pocion = pocion;
    }

    public String mostrarPocion(){
        return "- Pocion: "+pocion.getNombre()+" - Cantidad: "+cantidad;
    }
}
