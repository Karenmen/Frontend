package com.example.pozoleria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<CartItem> items;

    public CartAdapter(List<CartItem> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = items.get(position);

        holder.txtName.setText(item.getName());
        holder.txtQuantity.setText(String.valueOf(item.getQuantity()));
        holder.txtPrice.setText("$" + item.getPrice());

        holder.btnPlus.setOnClickListener(v -> {
            int q = item.getQuantity() + 1;
            item.setQuantity(q);
            holder.txtQuantity.setText(String.valueOf(q));
        });

        holder.btnMinus.setOnClickListener(v -> {
            int q = item.getQuantity();
            if (q > 1) {
                q--;
                item.setQuantity(q);
                holder.txtQuantity.setText(String.valueOf(q));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtQuantity, txtPrice;
        Button btnMinus, btnPlus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtItemName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
        }
    }
}
