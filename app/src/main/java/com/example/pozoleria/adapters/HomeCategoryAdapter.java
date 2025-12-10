package com.example.pozoleria.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pozoleria.R;
import com.example.pozoleria.models.CategoryItem;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    private Context context;
    private List<CategoryItem> lista;

    public HomeCategoryAdapter(Context context, List<CategoryItem> lista) {
        this.context = context;
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCategoryHome;
        TextView txtCategoryHomeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCategoryHome = itemView.findViewById(R.id.imgCategoryHome);
            txtCategoryHomeName = itemView.findViewById(R.id.txtCategoryHomeName);
        }
    }

    @NonNull
    @Override
    public HomeCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_home, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryAdapter.ViewHolder holder, int position) {
        CategoryItem item = lista.get(position);

        holder.txtCategoryHomeName.setText(item.getNombre());
        holder.imgCategoryHome.setImageResource(item.getImagen()); // ← CORREGIDO
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
