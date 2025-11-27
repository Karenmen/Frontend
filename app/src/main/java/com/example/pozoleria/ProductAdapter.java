package com.example.pozoleria;

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

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<ProductItem> productList;

    public ProductAdapter(Context context, List<ProductItem> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductItem product = productList.get(position);

        holder.txtProductName.setText(product.getName());

        String priceText = String.format(Locale.getDefault(), "$%.2f", product.getPrice());
        holder.txtProductPrice.setText(priceText);

        holder.imgProduct.setImageResource(product.getImageResId());

        holder.btnAgregar.setOnClickListener(v -> {
            // 👇 Llamada correcta al object de Kotlin
            CartStorage.INSTANCE.addItem(product.getName(), product.getPrice());

            Toast.makeText(context,
                    product.getName() + " agregado al carrito",
                    Toast.LENGTH_SHORT).show();
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
