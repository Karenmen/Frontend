package com.example.pozoleria;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
        categoriasDestacadas.add(new CategoryItem("Platillos Mexicanos", R.drawable.refresco));
        categoriasDestacadas.add(new CategoryItem("Bebidas", R.drawable.refresco));
        categoriasDestacadas.add(new CategoryItem("Hot Dogs", R.drawable.desayunos));

        HomeCategoryAdapter adapter = new HomeCategoryAdapter(this, categoriasDestacadas);
        recyclerCategoriasHome.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerCategoriasHome.setAdapter(adapter);

        txtVerTodas.setOnClickListener(v ->
                startActivity(new Intent(this, CategoryActivity.class)));

        btnComoLlegar.setOnClickListener(v ->
                startActivity(new Intent(this, RutaPozoleriaActivity.class)));

        // ---------- BOTTOM NAV ----------
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    // ===============================
    //  MANEJO DEL MENÚ INFERIOR
    // ===============================
    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            return true;
        }

        if (id == R.id.nav_categories) {
            startActivity(new Intent(this, CategoryActivity.class));
            return true;
        }

        if (id == R.id.nav_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }

        // 🔴 CERRAR SESIÓN (ESTO ES LO QUE FALTABA)
        if (id == R.id.nav_logout) {
            mostrarDialogoCerrarSesion();
            return true;
        }

        return false;
    }

    // ===============================
    //  CERRAR SESIÓN
    // ===============================
    private void mostrarDialogoCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Deseas cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> cerrarSesion())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void cerrarSesion() {
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.logout();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
