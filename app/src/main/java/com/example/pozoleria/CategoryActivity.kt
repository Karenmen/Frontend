package com.example.pozoleria

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pozoleria.adapters.CategoryAdapter
import com.example.pozoleria.models.CategoryItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CategoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categorias: MutableList<CategoryItem>
    private lateinit var fabCarrito: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        // ---------- RECYCLER ----------
        recyclerView = findViewById(R.id.recyclerCategories)
        recyclerView.layoutManager = LinearLayoutManager(this)

        categorias = mutableListOf(
            CategoryItem("Pozoles", R.drawable.pozole),
            CategoryItem("Hamburguesas", R.drawable.hamburguesa),
            CategoryItem("Postres", R.drawable.postres),
            CategoryItem("Platillos Mexicanos", R.drawable.refresco),
            CategoryItem("Bebidas", R.drawable.bebidas),
            CategoryItem("Hot Dogs", R.drawable.desayunos)
        )

        categoryAdapter = CategoryAdapter(this, categorias)
        recyclerView.adapter = categoryAdapter

        // ---------- FLOATING BUTTON (CARRITO) ----------
        fabCarrito = findViewById(R.id.fabCarrito)

        fabCarrito.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }
}
