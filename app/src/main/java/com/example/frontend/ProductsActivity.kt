package com.example.frontend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.adapters.ProductAdapter
import com.example.frontend.models.Product

class ProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val recycler = findViewById<RecyclerView>(R.id.recyclerProducts)
        recycler.layoutManager = LinearLayoutManager(this)

        val categoryName = intent.getStringExtra("categoryName")

        // Productos según la categoría (ejemplo)
        val products = when(categoryName) {
            "Postres" -> listOf(
                Product("Fresas con Crema", 30.0, R.drawable.postres),
                Product("Flan Napolitano", 25.0, R.drawable.postres)
            )
            "Bebidas" -> listOf(
                Product("Refresco", 20.0, R.drawable.bebidas),
                Product("Agua", 15.0, R.drawable.bebidas)
            )
            else -> listOf(
                Product("Hamburguesa", 80.0, R.drawable.hamburguesa),
                Product("Hot Dog", 45.0, R.drawable.hotdog)
            )
        }

        recycler.adapter = ProductAdapter(products)
    }
}
