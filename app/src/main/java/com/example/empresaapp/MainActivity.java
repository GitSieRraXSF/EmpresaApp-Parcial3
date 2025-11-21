package com.example.empresaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editTextText, editTextTextPassword;
    ListaUsuarios listaU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        listaU = new ListaUsuarios(this);
        editTextText = findViewById(R.id.editTextText);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editTextText.getText().toString();
                String pass = editTextTextPassword.getText().toString();
                if (listaU.equalUser(user, pass)) {
                    Intent intent = new Intent(MainActivity.this, PresentacionActivity.class);
                    intent.putExtra("Username", user);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Usuario Erroneo!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button button1 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editTextText.getText().toString();
                String pass = editTextTextPassword.getText().toString();
                if (listaU.equalUser(user, pass)){
                    Toast.makeText(MainActivity.this, "El usuario ya esta registrado!...", Toast.LENGTH_LONG).show();
                } else {
                    listaU.agregarAlFinal(user, pass);
                    Toast.makeText(MainActivity.this, "Usuario Agregado!", Toast.LENGTH_LONG).show();
                }
                hideKeyboard();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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