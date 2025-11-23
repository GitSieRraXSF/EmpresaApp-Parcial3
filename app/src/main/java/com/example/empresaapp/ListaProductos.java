package com.example.empresaapp;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaProductos {
    private Productos cabeza;
    private Context context;
    private static final String PREF_NAME = "ProductosPrefs";
    private static final String KEY_PRODUCTOS = "lista_productos";
    public ListaProductos(Context context){
        this.context = context;
        this.cabeza = null;
        cargarProductosDesdePrefs();
    }
    private void cargarProductosDesdePrefs() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String productosJson = prefs.getString(KEY_PRODUCTOS, "[]");
        try {
            JSONArray jsonArray = new JSONArray(productosJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String nombre = jsonObject.getString("nombre");
                float precio = (float) jsonObject.getDouble("precio");
                int cantidad = jsonObject.getInt("cantidad");
                String tipo = jsonObject.getString("tipo");
                agregarAlFinal(id, nombre, precio, cantidad, tipo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void guardarProductosEnPrefs() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jsonArray = new JSONArray();
        Productos actual = cabeza;
        while (actual != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", actual.id);
                jsonObject.put("nombre", actual.Nombre);
                jsonObject.put("precio", actual.Precio);
                jsonObject.put("cantidad", actual.Cantidad);
                jsonObject.put("tipo", actual.Tipo);
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            actual = actual.Siguiente;
        }
        editor.putString(KEY_PRODUCTOS, jsonArray.toString());
        editor.apply();
    }
    public void agregarAlFinal(int id, String nombre, float precio, int cantidad, String tipo){
        Productos nuevoProducto = new Productos(id, nombre, precio, cantidad, tipo);
        if (cabeza == null){
            cabeza = nuevoProducto;
            guardarProductosEnPrefs();
            return;
        }
        Productos actual = cabeza;
        while (actual.Siguiente != null){
            actual = actual.Siguiente;
        }
        actual.Siguiente = nuevoProducto;
        guardarProductosEnPrefs();
    }
    public boolean existeProducto(int id) {
        Productos actual = cabeza;
        while (actual != null) {
            if (actual.id == id) {
                return true;
            }
            actual = actual.Siguiente;
        }
        return false;
    }
    public Productos buscarProducto(int id) {
        Productos actual = cabeza;
        while (actual != null) {
            if (actual.id == id) {
                return actual;
            }
            actual = actual.Siguiente;
        }
        return null;
    }
    public void eliminarProducto(int id) {
        if (cabeza == null) return;

        if (cabeza.id == id) {
            cabeza = cabeza.Siguiente;
            guardarProductosEnPrefs();
            return;
        }

        Productos actual = cabeza;
        while (actual.Siguiente != null) {
            if (actual.Siguiente.id == id) {
                actual.Siguiente = actual.Siguiente.Siguiente;
                guardarProductosEnPrefs();
                return;
            }
            actual = actual.Siguiente;
        }
    }
    public void actualizarProducto(int id, String nuevoNombre, float nuevoPrecio, int nuevaCantidad, String nuevoTipo) {
        Productos producto = buscarProducto(id);
        if (producto != null) {
            producto.Nombre = nuevoNombre;
            producto.Precio = nuevoPrecio;
            producto.Cantidad = nuevaCantidad;
            producto.Tipo = nuevoTipo;
            guardarProductosEnPrefs();
        }
    }
    public int obtenerCantidadProductos() {
        int count = 0;
        Productos actual = cabeza;
        while (actual != null) {
            count++;
            actual = actual.Siguiente;
        }
        return count;
    }
    public ArrayList<Productos> obtenerTodosLosProductos() {
        ArrayList<Productos> lista = new ArrayList<>();
        Productos actual = cabeza;
        while (actual != null) {
            lista.add(actual);
            actual = actual.Siguiente;
        }
        return lista;
    }
}