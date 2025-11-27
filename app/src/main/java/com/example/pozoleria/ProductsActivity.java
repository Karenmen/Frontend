package com.example.pozoleria;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products); // ya lo tienes

        TextView txtTitulo = findViewById(R.id.txtTituloProductos); // pon este id en tu XML
        String categoryName = getIntent().getStringExtra("categoryName");

        if (categoryName != null) {
            txtTitulo.setText(categoryName);
        }
    }
}
