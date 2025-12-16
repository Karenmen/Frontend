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

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        recyclerCategoriasHome = findViewById(R.id.recyclerCategoriasHome);
        txtVerTodas = findViewById(R.id.txtVerTodas);
        btnComoLlegar = findViewById(R.id.btnComoLlegar);

        // ---------- CATEGORÍAS ----------
        List<CategoryItem> categoriasDestacadas = new ArrayList<>();
        categoriasDestacadas.add(new CategoryItem("Pozole", R.drawable.pozole));
        categoriasDestacadas.add(new CategoryItem("Hamburguesas", R.drawable.hamburguesa));
        categoriasDestacadas.add(new CategoryItem("Postres", R.drawable.postres));
        categoriasDestacadas.add(new CategoryItem("PlatillosMexicanos", R.drawable.refresco));
        categoriasDestacadas.add(new CategoryItem("Bebidas", R.drawable.refresco));
        categoriasDestacadas.add(new CategoryItem("HotDogs", R.drawable.desayunos));

        HomeCategoryAdapter homeCategoryAdapter = new HomeCategoryAdapter(this, categoriasDestacadas);

        recyclerCategoriasHome.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerCategoriasHome.setAdapter(homeCategoryAdapter);

        txtVerTodas.setOnClickListener(v -> startActivity(new Intent(this, CategoryActivity.class)));

        btnComoLlegar.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RutaPozoleriaActivity.class);
            startActivity(intent);
        });

        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

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
