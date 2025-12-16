package com.example.pozoleria;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pozoleria.adapters.HomeCategoryAdapter;
import com.example.pozoleria.models.CategoryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerCategoriasHome;
    private TextView txtVerTodas;
    private Button btnComoLlegar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ------------------ VINCULACIÓN DE VISTAS ------------------
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        recyclerCategoriasHome = findViewById(R.id.recyclerCategoriasHome);
        txtVerTodas = findViewById(R.id.txtVerTodas);
        btnComoLlegar = findViewById(R.id.btnComoLlegar);

        // ------------------ CATEGORÍAS DESTACADAS ------------------
        List<CategoryItem> categoriasDestacadas = new ArrayList<>();
        categoriasDestacadas.add(new CategoryItem("Pozole", R.drawable.pozole));
        categoriasDestacadas.add(new CategoryItem("Hamburguesas", R.drawable.hamburguesa));
        categoriasDestacadas.add(new CategoryItem("Postres", R.drawable.postres));
        categoriasDestacadas.add(new CategoryItem("Platillos Mexicanos", R.drawable.refresco));
        categoriasDestacadas.add(new CategoryItem("Bebidas", R.drawable.refresco));
        categoriasDestacadas.add(new CategoryItem("Hot Dogs", R.drawable.desayunos));

        // ------------------ ADAPTER CON CLICK ------------------
        HomeCategoryAdapter homeCategoryAdapter =
                new HomeCategoryAdapter(
                        this,
                        categoriasDestacadas,
                        category -> {

                            Intent intent = new Intent(
                                    HomeActivity.this,
                                    ProductsActivity.class
                            );
                            intent.putExtra("CATEGORIA", category.getTitle());
                            startActivity(intent);
                        }
                );

        recyclerCategoriasHome.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerCategoriasHome.setAdapter(homeCategoryAdapter);

        // ------------------ VER TODAS LAS CATEGORÍAS ------------------
        txtVerTodas.setOnClickListener(v ->
                startActivity(new Intent(this, CategoryActivity.class))
        );

        // ------------------ CÓMO LLEGAR ------------------
        btnComoLlegar.setOnClickListener(v -> {
            Intent intent = new Intent(
                    HomeActivity.this,
                    RutaPozoleriaActivity.class
            );
            startActivity(intent);
        });

        // ------------------ BOTTOM NAVIGATION ------------------
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    // ------------------ NAVEGACIÓN INFERIOR ------------------
    private boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) return true;

        if (id == R.id.nav_categories) {
            startActivity(new Intent(this, CategoryActivity.class));
            return true;
        }

        if (id == R.id.nav_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }

        return false;
    }
}
