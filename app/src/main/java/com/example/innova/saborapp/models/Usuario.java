package com.example.innova.saborapp.models;

public class Usuario {
    private String nombres;
    private String apellidos;
    private String email;
    private String uid;

    private Usuario(){

    }

    public Usuario(String nombres, String apellidos, String email, String uid) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.uid = uid;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
