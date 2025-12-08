package com.example.pozoleria.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.pozoleria.R;
import com.example.pozoleria.models.CartItem;

import java.util.List;
import java.util.Locale;

public class CartListAdapter extends BaseAdapter {

    private Context context;
    private List<CartItem> items;
    private LayoutInflater inflater;


    public interface OnCartChangeListener {
        void onCartUpdated();
    }

    private OnCartChangeListener listener;

    public void setOnCartChangeListener(OnCartChangeListener listener) {
        this.listener = listener;
    }

    public CartListAdapter(Context context, List<CartItem> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
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

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_cart, parent, false);
        }

        CartItem item = items.get(position);

        TextView name = view.findViewById(R.id.txtCartName);
        TextView quantity = view.findViewById(R.id.txtCartQuantity);
        TextView price = view.findViewById(R.id.txtCartPrice);
        TextView total = view.findViewById(R.id.txtTotal);

        Button btnMinus = view.findViewById(R.id.btnMinus);
        Button btnPlus = view.findViewById(R.id.btnPlus);

        // DATOS INICIALES
        name.setText(item.getName());
        quantity.setText(String.valueOf(item.getQuantity()));
        price.setText(String.format(Locale.getDefault(), "$%.2f", item.getPrice()));

        // TOTAL INDIVIDUAL
        double totalProducto = item.getPrice() * item.getQuantity();
        total.setText(String.format(Locale.getDefault(), "Total: $%.2f", totalProducto));


        btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            quantity.setText(String.valueOf(item.getQuantity()));

            double nuevoTotal = item.getPrice() * item.getQuantity();
            total.setText(String.format(Locale.getDefault(), "Total: $%.2f", nuevoTotal));

            notifyDataSetChanged();

            if (listener != null) listener.onCartUpdated();
        });

        btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                quantity.setText(String.valueOf(item.getQuantity()));

                double nuevoTotal = item.getPrice() * item.getQuantity();
                total.setText(String.format(Locale.getDefault(), "Total: $%.2f", nuevoTotal));

                notifyDataSetChanged();

                if (listener != null) listener.onCartUpdated();
            }
        });

        return view;
    }
}
