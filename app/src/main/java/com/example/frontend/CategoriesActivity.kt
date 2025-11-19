package com.example.frontend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.adapters.CategoryAdapter
import com.example.frontend.models.Category

class CategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        val recycler = findViewById<RecyclerView>(R.id.recyclerCategories)
        recycler.layoutManager = GridLayoutManager(this, 2)

        val categories = listOf(
            Category("1", "Promociones", R.drawable.promociones),
            Category("2", "Platillos", R.drawable.desayunos),
            Category("3", "Postres", R.drawable.postres),
            Category("4", "Bebidas", R.drawable.bebidas),
            Category("5", "Combos", R.drawable.combos)
        )

        recycler.adapter = CategoryAdapter(categories) { category ->
            val intent = Intent(this, ProductsActivity::class.java)
            intent.putExtra("categoryName", category.name)
            startActivity(intent)
        }
    }
}
