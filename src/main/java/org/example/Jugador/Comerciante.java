package org.example.Jugador;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Comerciante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nombre;

    @Column
    @Enumerated(EnumType.STRING)
    private TipoComerciante tipo;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name = "ComercianteIngrediente",
            joinColumns = @JoinColumn(name = "comerciante_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> lIngredientes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoComerciante getTipo() {
        return tipo;
    }

    public void setTipo(TipoComerciante tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Ingrediente> getlIngredientes() {
        return lIngredientes;
    }

    public void setlIngredientes(List<Ingrediente> lIngredientes) {
        this.lIngredientes = lIngredientes;
    }

    public String mostrarComerciante(){
        return "Comerciante: "+nombre+" (Tipo: "+tipo.toString()+") - Ingredientes que puede vender: "+lIngredientes.size();
    }
}
