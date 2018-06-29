package com.example.innova.saborapp.models;

import java.util.ArrayList;
import java.util.List;

public class Receta {

    private String idReceta;
    private String nombreReceta;
    private String fotoReceta;

    private ArrayList<Ingrediente> ingredientes;
    private ArrayList<Paso> pasos;

    public String getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(String idReceta) {
        this.idReceta = idReceta;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    public String getFotoReceta() {
        return fotoReceta;
    }

    public void setFotoReceta(String fotoReceta) {
        this.fotoReceta = fotoReceta;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public ArrayList<Paso> getPasos() {
        return pasos;
    }

    public void setPasos(ArrayList<Paso> pasos) {
        this.pasos = pasos;
    }
}
