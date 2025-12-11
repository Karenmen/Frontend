package com.example.pozoleria.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pozoleria.ProductsActivity
import com.example.pozoleria.R
import com.example.pozoleria.models.CategoryItem

class CategoryAdapter(
    private val context: Context,
    private val categorias: List<CategoryItem>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCategory: ImageView = view.findViewById(R.id.imgCategory)
        val txtCategoryName: TextView = view.findViewById(R.id.txtCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(context)
            .inflate(R.layout.item_category, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = categorias[position]

        // MOSTRAR NOMBRE E IMAGEN
        holder.txtCategoryName.text = item.title
        holder.imgCategory.setImageResource(item.imageResId)

        // ANIMACIÓN
        animateItem(holder.itemView)

        // EVENTO DE CLIC
        holder.itemView.setOnClickListener {
            val categoriaFinal = item.title.replace(" ", "")
            val intent = Intent(context, ProductsActivity::class.java)
            intent.putExtra("categoryName", categoriaFinal)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = categorias.size

    // ANIMACIÓN SUAVE AL APARECER
    private fun animateItem(view: View) {
        view.alpha = 0f
        view.translationY = 80f

        view.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(350)
            .start()
    }
}
