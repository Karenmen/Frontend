package com.example.pozoleria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class ProductListAdapter extends BaseAdapter {

    private Context context;
    private List<ProductItem> productList;
    private LayoutInflater inflater;

    public ProductListAdapter(Context context, List<ProductItem> productList) {
        this.context = context;
        this.productList = productList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            row = inflater.inflate(R.layout.item_product, parent, false);
        }

        ProductItem product = productList.get(position);

        ImageView img = row.findViewById(R.id.imgProduct);
        TextView name = row.findViewById(R.id.txtProductName);
        TextView price = row.findViewById(R.id.txtProductPrice);
        Button btnAgregar = row.findViewById(R.id.btnAgregar);

        img.setImageResource(product.getImageResId());
        name.setText(product.getName());
        price.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));

        btnAgregar.setOnClickListener(v -> {

            CartStorage.INSTANCE.addItem(product.getName(), product.getPrice());

            Toast.makeText(context,
                    product.getName() + " agregado al carrito",
                    Toast.LENGTH_SHORT).show();
        });

        return row;
    }
}
