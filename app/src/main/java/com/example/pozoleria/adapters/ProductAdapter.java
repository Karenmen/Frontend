package com.example.pozoleria.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pozoleria.R;
import com.example.pozoleria.models.Producto;
import com.example.pozoleria.models.CartStorage;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Producto> productList;

    public ProductAdapter(Context context, List<Producto> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Producto producto = productList.get(position);

        holder.txtProductName.setText(producto.getNombre());
        holder.txtProductPrice.setText("$" + producto.getPrecio());

        holder.btnAgregar.setOnClickListener(v -> {
            CartStorage.INSTANCE.addItem(producto.getNombre(), producto.getPrecio());
            Toast.makeText(context, "Agregado al carrito", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtProductName, txtProductPrice;
        Button btnAgregar;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            btnAgregar = itemView.findViewById(R.id.btnAgregar);
        }
    }
}
