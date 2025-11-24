package com.example.empresaapp;

import  android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CalculoVentaActivity extends AppCompatActivity {
    private int indice = 0;
    private ImageView imageView2;
    private final int[] i = {
            R.drawable.formulacalculoventa
    };
    EditText editTextNumber, editTextNumber2;
    TextView textView19;
    float margen1;
    float precioventa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculodeventa);
        imageView2 = findViewById(R.id.imageView2);
        imageView2.setImageResource(i[indice]);
        textView19 = findViewById(R.id.textView19);
        Button button = findViewById(R.id.button15);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int costoventa = Integer.parseInt(editTextNumber.getText().toString());
                    float margenventa = Float.parseFloat(editTextNumber2.getText().toString());
                    if (margenventa != 0) {
                        margen1 = margenventa;
                    } else {
                        Toast.makeText(CalculoVentaActivity.this, "Ingrese un porcentaje (decimal) valido!", Toast.LENGTH_SHORT).show();
                    }
                    precioventa = costoventa / (1 - margen1);
                    textView19.setText("Precio de venta segun la formula: " + precioventa);
                } catch (NumberFormatException e) {
                    // Manejar error si no es un número válido
                    Toast.makeText(CalculoVentaActivity.this, "Ingresa un numero para calcular!", Toast.LENGTH_SHORT).show();
                }
                hideKeyboard();
            }
        });
        Button button1 = findViewById(R.id.button16);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculoVentaActivity.this, MenuActivity.class);
                startActivity(intent);
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
}