package com.example.pozoleria.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pozoleria.ProductsActivity;
import com.example.pozoleria.R;
import com.example.pozoleria.models.CategoryItem;

import java.util.List;

public class HomeCategoryAdapter
        extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    private final Context context;
    private final List<CategoryItem> lista;
    private final OnCategoryClickListener listener;

    // 🔹 Constructor SIMPLE (EL QUE YA USAS)
    public HomeCategoryAdapter(Context context, List<CategoryItem> lista) {
        this.context = context;
        this.lista = lista;

        // ✅ CLICK CORRECTO (MISMA CLAVE QUE PRODUCTS)
        this.listener = category -> {
            Intent intent = new Intent(context, ProductsActivity.class);
            intent.putExtra(ProductsActivity.EXTRA_CATEGORY, category.getTitle());
            context.startActivity(intent);
        };
    }

    // 🔹 Constructor COMPLETO (opcional)
    public HomeCategoryAdapter(Context context,
                               List<CategoryItem> lista,
                               OnCategoryClickListener listener) {
        this.context = context;
        this.lista = lista;
        this.listener = listener;
    }

    // 🔹 Interfaz de click
    public interface OnCategoryClickListener {
        void onCategoryClick(CategoryItem category);
    }

    // ================= ViewHolder =================
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCategoryHome;
        TextView txtCategoryHomeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategoryHome = itemView.findViewById(R.id.imgCategoryHome);
            txtCategoryHomeName = itemView.findViewById(R.id.txtCategoryHomeName);
        }

        public void bind(CategoryItem item, OnCategoryClickListener listener) {
            itemView.setOnClickListener(v -> listener.onCategoryClick(item));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_home, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryItem item = lista.get(position);

        holder.txtCategoryHomeName.setText(item.getTitle());
        holder.imgCategoryHome.setImageResource(item.getImageResId());
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
