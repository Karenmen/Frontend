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

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private var productList = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        recycler = findViewById(R.id.recyclerProductos)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = ProductoAdapter(this, productList)
        recycler.adapter = adapter

        val categoria = intent.getStringExtra("categoryName")
        if (categoria == null) {
            Toast.makeText(this, "No se recibió categoría", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("ProductsActivity", "Categoría recibida: $categoria")

        cargarProductos(categoria)
    }

    private fun cargarProductos(categoria: String) {
        val categoriaCodificada = URLEncoder.encode(categoria, StandardCharsets.UTF_8.toString())
        val url = "http://10.0.2.2:3000/api/productos/categoria/$categoriaCodificada"
        Log.d("ProductsActivity", "URL: $url")

        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                productList.clear()
                for (i in 0 until response.length()) {
                    val obj = response.getJSONObject(i)
                    val producto = Producto(
                        _id = obj.getString("_id"),
                        nombre = obj.getString("nombre"),
                        precio = obj.getDouble("precio"),
                        categoria = obj.getString("categoria"),
                        imagen = obj.optString("imagen", "")
                    )
                    productList.add(producto)
                }
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Productos cargados: ${productList.size}", Toast.LENGTH_SHORT).show()
                Log.d("ProductsActivity", "Productos cargados: ${productList.size}")
            },
            { error ->
                Toast.makeText(this, "Error al cargar productos", Toast.LENGTH_SHORT).show()
                Log.e("ProductsActivity", "Error: ${error.message}")
            }
        )

        Volley.newRequestQueue(this).add(request)
    }
}
