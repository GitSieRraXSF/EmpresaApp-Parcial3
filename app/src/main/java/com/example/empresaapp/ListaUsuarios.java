package com.example.empresaapp;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListaUsuarios {
    private Usuarios cabeza;
    private Context context;
    private static final String PREF_NAME = "UsuariosPrefs";
    private static final String KEY_USUARIOS = "lista_usuarios";

    public ListaUsuarios(Context context){
        this.context = context;
        this.cabeza = null;
        cargarUsuariosDesdePrefs();
    }
    private void cargarUsuariosDesdePrefs() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String usuariosJson = prefs.getString(KEY_USUARIOS, "[]");
        try {
            JSONArray jsonArray = new JSONArray(usuariosJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String user = jsonObject.getString("user");
                String pass = jsonObject.getString("pass");
                agregarAlFinal(user, pass);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void guardarUsuariosEnPrefs() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jsonArray = new JSONArray();
        Usuarios actual = cabeza;
        while (actual != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user", actual.User);
                jsonObject.put("pass", actual.Pass);
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            actual = actual.Siguiente;
        }
        editor.putString(KEY_USUARIOS, jsonArray.toString());
        editor.apply();
    }
    public void agregarAlFinal(String User, String Pass){
        Usuarios nuevoUsuario = new Usuarios(User, Pass);
        if (cabeza == null){
            cabeza = nuevoUsuario;
            return;
        }
        Usuarios actual = cabeza;
        while (actual.Siguiente != null){
            actual = actual.Siguiente;
        }
        actual.Siguiente = nuevoUsuario;
        guardarUsuariosEnPrefs();
    }
    public boolean equalUser(String user, String pass) {
        Usuarios actual = cabeza;
        while (actual != null) {
            if (actual.User.equals(user) && actual.Pass.equals(pass)) {
                return true;
            }
            actual = actual.Siguiente;
        }
        return false;
    }
}