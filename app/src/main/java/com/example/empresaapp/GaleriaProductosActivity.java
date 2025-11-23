package com.example.empresaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GaleriaProductosActivity extends AppCompatActivity {
    private ListView listViewProductos;
    private ListaProductos listaProductos;
    private ProductoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeriaproductos);
        listViewProductos = findViewById(R.id.listViewProductos);
        listaProductos = new ListaProductos(this);
        configurarListView();
        Button button = findViewById(R.id.button19);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GaleriaProductosActivity.this, RegistroProductosActivity.class);
                startActivity(intent);
            }
        });
        Button button1 = findViewById(R.id.button20);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GaleriaProductosActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
    private void configurarListView() {
        ArrayList<Productos> productos = listaProductos.obtenerTodosLosProductos();
        adapter = new ProductoAdapter(this, productos);
        listViewProductos.setAdapter(adapter);
        listViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Productos productoSeleccionado = productos.get(position);
                mostrarDetallesProducto(productoSeleccionado);
            }
        });
        listViewProductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Productos productoSeleccionado = productos.get(position);
                mostrarOpcionesProducto(productoSeleccionado);
                return true; // Indica que el evento fue consumido
            }
        });
    }
    private void mostrarDetallesProducto(Productos producto) {
        String mensaje = String.format(
                "Detalles del Producto:\n\n" +
                        "ID: %d\n" +
                        "Nombre: %s\n" +
                        "Precio: $%.2f\n" +
                        "Cantidad en stock: %d\n" +
                        "Tipo: %s\n" +
                        "Valor total: $%.2f",
                producto.id, producto.Nombre, producto.Precio,
                producto.Cantidad, producto.Tipo,
                producto.Precio * producto.Cantidad
        );
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
    private void mostrarOpcionesProducto(Productos producto) {
        // Aquí puedes implementar un diálogo con opciones
        // como editar, eliminar, etc.
        String mensaje = "Mantén presionado para: Editar o Eliminar\n" +
                "Producto: " + producto.Nombre;
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
    public void actualizarLista() {
        ArrayList<Productos> productosActualizados = listaProductos.obtenerTodosLosProductos();
        adapter.clear();
        adapter.addAll(productosActualizados);
        adapter.notifyDataSetChanged();

        Toast.makeText(this, "Lista actualizada", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista cuando la activity se reanude
        actualizarLista();
    }
}