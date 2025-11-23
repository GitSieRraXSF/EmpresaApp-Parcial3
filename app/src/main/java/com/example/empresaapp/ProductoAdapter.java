package com.example.empresaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ProductoAdapter extends ArrayAdapter<Productos> {
    public ProductoAdapter(Context context, ArrayList<Productos> productos){
        super(context, 0, productos);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Productos producto = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
        }
        TextView textViewNombre = convertView.findViewById(R.id.textViewNombre);
        TextView textViewPrecio = convertView.findViewById(R.id.textViewPrecio);
        TextView textViewStock = convertView.findViewById(R.id.textViewStock);
        TextView textViewId = convertView.findViewById(R.id.textViewId);
        TextView textViewTipo = convertView.findViewById(R.id.textViewTipo);

        textViewNombre.setText(producto.Nombre);
        textViewPrecio.setText(String.format("Precio: $%.2f", producto.Precio));
        textViewStock.setText(String.format("Stock: %d", producto.Cantidad));
        textViewId.setText(String.format("ID: %d", producto.id));
        textViewTipo.setText(producto.Tipo);

        if (producto.Cantidad < 5){
            textViewStock.setTextColor(getContext().getResources().getColor(android.R.color.holo_red_dark));
        } else {
            textViewStock.setTextColor(getContext().getResources().getColor(android.R.color.holo_green_dark));
        }
        return convertView;
    }
}