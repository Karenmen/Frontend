package com.example.pozoleria;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pozoleria.adapters.CategoryAdapter;
import com.example.pozoleria.models.CategoryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 viewPagerBanners;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPagerBanners = findViewById(R.id.viewPagerBanners);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // ---------- CARRUSEL ----------
        List<Integer> banners = new ArrayList<>();
        banners.add(R.drawable.promociones);
        banners.add(R.drawable.combos);

        BannerAdapter bannerAdapter = new BannerAdapter(banners);
        viewPagerBanners.setAdapter(bannerAdapter);

        // ---------- CATEGORÍAS ----------
        List<CategoryItem> categorias = new ArrayList<>();

        // 🚨 Usa los nombres EXACTOS que están en MongoDB
        categorias.add(new CategoryItem("Pozoles", R.drawable.pozole));
        categorias.add(new CategoryItem("Tacos Dorados", R.drawable.tacos_dorados));
        categorias.add(new CategoryItem("Postres", R.drawable.postres));
        categorias.add(new CategoryItem("Bebidas", R.drawable.refresco));
        categorias.add(new CategoryItem("Combos", R.drawable.combos));
        categorias.add(new CategoryItem("Promociones", R.drawable.promociones));

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categorias);

        RecyclerView recyclerCategorias = findViewById(R.id.recyclerCategorias);
        recyclerCategorias.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerCategorias.setAdapter(categoryAdapter);

        // ---------- BOTTOM NAV ----------
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
