package com.example.pozoleria.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pozoleria.R
import com.example.pozoleria.models.CartStorage
import com.example.pozoleria.models.Producto
import com.squareup.picasso.Picasso

class ProductoAdapter(
    private val context: Context,
    private val productos: MutableList<Producto>
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val prod = productos[position]

        holder.txtNombre.text = prod.nombre
        holder.txtPrecio.text = "$${prod.precio}"

        if (prod.imagen.isNotEmpty()) {
            Picasso.get().load(prod.imagen).into(holder.imgProducto)
        } else {
            holder.imgProducto.setImageResource(R.drawable.pozole)
        }


        holder.btnAgregar.setOnClickListener {
            CartStorage.addItem(prod.nombre, prod.precio)
            Toast.makeText(context, "${prod.nombre} añadido al carrito", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = productos.size

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val txtPrecio: TextView = view.findViewById(R.id.txtPrecio)
        val imgProducto: ImageView = view.findViewById(R.id.imgProducto)
        val btnAgregar: Button = view.findViewById(R.id.btnAgregar) // ⭐ IMPORTANTE
    }
}
