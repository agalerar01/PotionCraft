package org.example.Jugador;

import jakarta.persistence.*;

@Entity
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @Enumerated(EnumType.STRING)
    private TipoIngrediente tipo;

    @Column
    private String nombre;

    @Column
    private String descripcion;

    @Column
    private Double precioCompra;

    @Column
    private String efectoPositivo;

    @Column
    private String efectoNegativo;

    @Column
    private Integer nivelToxicidad;

    @Column
    private Double dureza;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoIngrediente getTipo() {
        return tipo;
    }

    public void setTipo(TipoIngrediente tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEfectoPositivo() {
        return efectoPositivo;
    }

    public void setEfectoPositivo(String efectoPositivo) {
        this.efectoPositivo = efectoPositivo;
    }

    public String getEfectoNegativo() {
        return efectoNegativo;
    }

    public void setEfectoNegativo(String efectoNegativo) {
        this.efectoNegativo = efectoNegativo;
    }

    public int getNivelToxicidad() {
        return nivelToxicidad;
    }

    public void setNivelToxicidad(int nivelToxicidad) {
        this.nivelToxicidad = nivelToxicidad;
    }

    public double getDureza() {
        return dureza;
    }

    public void setDureza(double dureza) {
        this.dureza = dureza;
    }

    public String mostrarIngrediente(){
        return nombre+"(Tipo: "+tipo.toString()+") - Precio: "+String.format("%.2f",precioCompra);
    }
}
