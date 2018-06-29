package com.example.innova.saborapp.models;

public class Ingrediente {
    private String NombreIngrediente;
    private String Cantidad;

    public String getNombreIngrediente() {
        return NombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        NombreIngrediente = nombreIngrediente;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }
}
