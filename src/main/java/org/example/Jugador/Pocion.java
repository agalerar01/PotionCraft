package org.example.Jugador;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Pocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nombre;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name = "Ingredientes_Pociones",
            joinColumns = @JoinColumn(name = "pocion_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> lIngredientes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String mostrarIngredientesCrear(){
        String linea = "";

        for(Ingrediente i : lIngredientes){
            linea += "- "+i.getNombre()+"(Tipo: "+i.getTipo()+"), ";
        }

        return linea;
    }

    public void mostrarIngredientes(){

        System.out.println("Pocion: "+nombre);
        System.out.println("Ingredientes necesarios: ");
        for(Ingrediente i : lIngredientes){
            System.out.println("- "+i.getNombre()+" (Tipo: "+i.getTipo().toString()+")");
        }
        System.out.println("");
    }
}
