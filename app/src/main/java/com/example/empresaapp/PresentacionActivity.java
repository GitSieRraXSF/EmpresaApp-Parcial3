package com.example.empresaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class PresentacionActivity extends AppCompatActivity {
    private ImageView imageView;
    private int indice = 0;
    private final int[] images = {R.drawable.trebol};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(images[indice]);
        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PresentacionActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        Button button1 = findViewById(R.id.button11);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PresentacionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}