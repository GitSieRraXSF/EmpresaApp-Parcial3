package com.example.empresaapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroProductosActivity extends AppCompatActivity {
    ListaProductos listap;
    EditText editTextNumber4, editTextNumber5, editTextNumber6;
    EditText editTextText2, editTextText3;
    String TipoV, Nombre;
    int ID1, ID, Cantidad, Cantidad1;
    Float Precio, Precio1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroproducto);
        listap = new ListaProductos(this);
        editTextNumber4 = findViewById(R.id.editTextNumber4);
        editTextText2 = findViewById(R.id.editTextText2);
        editTextNumber5 = findViewById(R.id.editTextNumber5);
        editTextNumber6 = findViewById(R.id.editTextNumber6);
        editTextText3 = findViewById(R.id.editTextText3);
        Button button = findViewById(R.id.button17);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarIDunico(editTextNumber4)){
                    ID = Integer.parseInt(editTextNumber4.getText().toString());
                    ID1 = ID;
                } else {
                    Toast.makeText(RegistroProductosActivity.this, "El id ya existe!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validarCampo(editTextText2)){
                    Toast.makeText(RegistroProductosActivity.this, "El campo no puede estar vacio!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Nombre = editTextText2.getText().toString();
                }
                if (validarNumeroPositivo(editTextNumber5, "El numero tiene que ser positivo!")){
                    Precio = Float.parseFloat(editTextNumber5.getText().toString());
                    Precio1 = Precio;
                } else {
                    Toast.makeText(RegistroProductosActivity.this, "El precio debe ser positivo!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (validarNumeroPositivo(editTextNumber6, "El numero tiene que ser positivo!")){
                    Cantidad = Integer.parseInt(editTextNumber6.getText().toString());
                    Cantidad1 = Cantidad;
                } else {
                    Toast.makeText(RegistroProductosActivity.this, "La cantidad debe ser positivo!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tipo = editTextText3.getText().toString();
                if (tipo.equals("Fruta") || tipo.equals("Verdura")) {
                    TipoV = tipo;
                } else {
                    Toast.makeText(RegistroProductosActivity.this, "Tiene que ser fruta o verdura!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listap.agregarAlFinal(ID1, Nombre, Precio1, Cantidad1, TipoV);
                Toast.makeText(RegistroProductosActivity.this, "El producto se ha registrado exitosamente!", Toast.LENGTH_SHORT).show();
                hideKeyboard();
            }
        });
    }
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public boolean validarIDunico(EditText editTextNumber4) {
        try {
            String texto = editTextNumber4.getText().toString();
            if (texto.isEmpty()){
                editTextNumber4.setError("El campo no puede estar vacio");
                editTextNumber4.requestFocus();
                return false;
            }
            int n = Integer.parseInt(editTextNumber4.getText().toString());
            if (listap.existeProducto(n)){
                editTextNumber4.setError("Este numero ID ya existe");
                editTextNumber4.requestFocus();
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            editTextNumber4.setError("ID invalido");
            return false;
        }
    }
    private boolean validarNumeroPositivo(EditText editTextNumber6, String mensajeError) {
        try {
            String texto = editTextNumber6.getText().toString().trim();
            if (texto.isEmpty()) {
                editTextNumber6.setError("Campo requerido");
                return false;
            }
            float numero = Float.parseFloat(texto);
            if (numero <= 0) {
                editTextNumber6.setError(mensajeError);
                editTextNumber6.requestFocus();
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            editTextNumber6.setError("Formato de número inválido");
            editTextNumber6.requestFocus();
            return false;
        }
    }
    public boolean validarCampo(EditText editTextText2) {
        String texto = editTextText2.getText().toString().trim();
        if (texto.isEmpty()) {
            editTextText2.setError("Este campo no puede estar vacío");
            editTextText2.requestFocus();
            return false;
        }
        return true;
    }
}