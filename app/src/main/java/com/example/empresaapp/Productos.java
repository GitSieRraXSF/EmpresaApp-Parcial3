package com.example.empresaapp;

public class Productos {
    int id;
    String Nombre;
    float Precio;
    int Cantidad;
    String Tipo;
    Productos Siguiente;

    public Productos(int id, String Nombre, float Precio, int Cantidad, String Tipo){
        this.id = id;
        this.Nombre = Nombre;
        this.Precio = Precio;
        this.Cantidad = Cantidad;
        this.Tipo = Tipo;
        this.Siguiente = null;
    }
}