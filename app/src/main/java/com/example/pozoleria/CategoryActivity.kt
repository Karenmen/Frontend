package com.example.pozoleria

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pozoleria.adapters.CategoryAdapter
import com.example.pozoleria.models.CategoryItem

class CategoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categorias: MutableList<CategoryItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        recyclerView = findViewById(R.id.recyclerCategorias)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 🔥 CATEGORÍAS EXACTAMENTE COMO EN TU BACKEND/MONGO
        categorias = mutableListOf(
            CategoryItem("Pozole", R.drawable.pozole),
            CategoryItem("Hamburguesas", R.drawable.hamburguesa),
            CategoryItem("Postres", R.drawable.postres),
            CategoryItem("Platillos Mexicanos", R.drawable.refresco),
            CategoryItem("Bebidas", R.drawable.bebidas),
            CategoryItem("HotDogs", R.drawable.desayunos)
        )

        categoryAdapter = CategoryAdapter(this, categorias)
        recyclerView.adapter = categoryAdapter
    }
}
