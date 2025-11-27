package com.example.pozoleria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class CartListAdapter extends BaseAdapter {

    private Context context;
    private List<CartItem> items;

    public CartListAdapter(Context context, List<CartItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_cart, parent, false);

        CartItem item = items.get(position);

        TextView txtName = view.findViewById(R.id.txtItemName);
        TextView txtPrice = view.findViewById(R.id.txtPrice);
        TextView txtQuantity = view.findViewById(R.id.txtQuantity);
        Button btnPlus = view.findViewById(R.id.btnPlus);
        Button btnMinus = view.findViewById(R.id.btnMinus);

        txtName.setText(item.getName());
        txtPrice.setText("$" + item.getPrice());
        txtQuantity.setText(String.valueOf(item.getQuantity()));

        btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyDataSetChanged();
        });

        btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
