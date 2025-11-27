package com.example.pozoleria;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private ListView listaProductos;
    private ProductListAdapter adapter;
    private List<ProductItem> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        listaProductos = findViewById(R.id.listaProductos);

        String categoryName = getIntent().getStringExtra("category");

        productList = getProductsByCategory(categoryName);

        adapter = new ProductListAdapter(this, productList);

        listaProductos.setAdapter(adapter);
    }

    private List<ProductItem> getProductsByCategory(String category) {
        List<ProductItem> list = new ArrayList<>();

        if (category == null) category = "";

        switch (category) {
            case "Pozoles":
                list.add(new ProductItem("Pozole Tradicional", 70.0, R.drawable.pozole));
                break;

            case "Tacos":
                list.add(new ProductItem("Tacos Dorados", 45.0, R.drawable.tacos_dorados));
                break;

            default:
                list.add(new ProductItem("Producto de ejemplo", 50.0, R.drawable.pozole));
                break;
        }

        return list;
    }
}
