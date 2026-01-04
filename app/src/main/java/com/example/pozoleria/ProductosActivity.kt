package com.example.pozoleria

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.pozoleria.adapters.ProductoAdapter
import com.example.pozoleria.models.Producto
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class ProductsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORY = "EXTRA_CATEGORY"
    }

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private val productList = mutableListOf<Producto>()

    private val BASE_URL = "https://backend-pozoleria.onrender.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        recycler = findViewById(R.id.recyclerProductos)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = ProductoAdapter(this, productList)
        recycler.adapter = adapter

        // ✅ CLAVE ÚNICA Y CORRECTA
        val categoria = intent.getStringExtra(EXTRA_CATEGORY)

        if (categoria.isNullOrBlank()) {
            Toast.makeText(this, "No se recibió categoría", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        title = categoria
        cargarProductos(categoria)
    }

    private fun cargarProductos(categoria: String) {
        val categoriaCodificada =
            URLEncoder.encode(categoria, StandardCharsets.UTF_8.toString())

        val url = "$BASE_URL/api/productos/categoria/$categoriaCodificada"

        Log.d("ProductsActivity", "URL: $url")

        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                productList.clear()

                for (i in 0 until response.length()) {
                    val obj = response.getJSONObject(i)
                    productList.add(
                        Producto(
                            _id = obj.getString("_id"),
                            nombre = obj.getString("nombre"),
                            precio = obj.getDouble("precio"),
                            categoria = obj.getString("categoria"),
                            imagen = obj.optString("imagen", "")
                        )
                    )
                }

                adapter.notifyDataSetChanged()
            },
            { error ->
                Toast.makeText(this, "Error al cargar productos", Toast.LENGTH_SHORT).show()
                Log.e("ProductsActivity", "Volley Error", error)
            }
        )

        Volley.newRequestQueue(this).add(request)
    }
}
