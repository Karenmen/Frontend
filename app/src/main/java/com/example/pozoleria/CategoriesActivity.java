package com.example.pozoleria;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // 1. Referencia al RecyclerView
        RecyclerView recycler = findViewById(R.id.recyclerCategoriasFull);

        // 2. Lista de categorías con imágenes
        List<CategoryItem> categorias = new ArrayList<>();
        categorias.add(new CategoryItem("Pozole", R.drawable.pozole));
        categorias.add(new CategoryItem("Tacos Dorados", R.drawable.tacos_dorados));
        categorias.add(new CategoryItem("Postres", R.drawable.postres));
        categorias.add(new CategoryItem("Bebidas", R.drawable.refresco));
        categorias.add(new CategoryItem("Combos", R.drawable.combos));
        categorias.add(new CategoryItem("Promociones", R.drawable.promociones));

        // 3. Configurar Adapter
        CategoryAdapter adapter = new CategoryAdapter(this, categorias);

        // 4. Layout tipo grid de 2 columnas
        recycler.setLayoutManager(new GridLayoutManager(this, 2));

        // 5. Asignar el adapter al RecyclerView
        recycler.setAdapter(adapter);
    }
}
