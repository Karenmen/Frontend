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
    private val items: List<CategoryItem>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCategory: ImageView = view.findViewById(R.id.imgCategory)
        val txtCategoryName: TextView = view.findViewById(R.id.txtCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = items[position]
        holder.txtCategoryName.text = item.title
        holder.imgCategory.setImageResource(item.imageResId)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductsActivity::class.java)
            intent.putExtra("categoryName", item.title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}
