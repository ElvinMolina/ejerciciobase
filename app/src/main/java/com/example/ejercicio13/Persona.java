package com.example.ejercicio13;

import android.provider.BaseColumns;

public class Persona {
    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private String correo;
    private String direccion;

    public Persona(int id, String nombre, String apellido, int edad, String correo, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.correo = correo;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }
}

