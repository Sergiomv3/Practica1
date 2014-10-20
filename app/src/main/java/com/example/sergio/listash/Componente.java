package com.example.sergio.listash;

/**
 * Created by Sergio on 18/10/2014.
 */
public class Componente {
    private String tipo;
    private String nombre;
    private int precio;

    public Componente(int precio, String tipo, String nombre) {
        this.precio = precio;
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public Componente() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
