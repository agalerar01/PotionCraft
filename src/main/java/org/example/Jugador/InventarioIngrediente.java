package org.example.Jugador;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class InventarioIngrediente {

    public InventarioIngrediente() {
    }

    public InventarioIngrediente(Integer cantidad, Ingrediente ingrediente) {
        this.cantidad = cantidad;
        this.ingrediente = ingrediente;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Integer cantidad;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "ingrediente_id")
    private Ingrediente ingrediente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String mostrarIngrediente(){
        return "- Ingrediente: "+ingrediente.getNombre()+" ("+ingrediente.getTipo().toString()+") - Cantidad: "+cantidad;
    }
}
