package com.example.innova.saborapp;

import android.app.Application;

import com.example.innova.saborapp.models.Usuario;

public class Global extends Application {
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
