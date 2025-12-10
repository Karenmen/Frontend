package com.example.pozoleria;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pozoleria.adapters.CartListAdapter;
import com.example.pozoleria.models.CartStorage;

public class CartActivity extends AppCompatActivity {

    private ListView listaCarrito;
    private CartListAdapter adapter;
    private TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listaCarrito = findViewById(R.id.listaCarrito);
        txtTotal = findViewById(R.id.txtTotal); // <-- Asegúrate que exista en activity_cart.xml

        adapter = new CartListAdapter(this, CartStorage.INSTANCE.getItems());
        listaCarrito.setAdapter(adapter);

        updateTotal();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        updateTotal();
    }

    // ------------------------------
    //  MÉTODO PARA ACTUALIZAR TOTAL
    // ------------------------------
    public void updateTotal() {
        double total = CartStorage.INSTANCE.getTotal();
        txtTotal.setText(String.format("Total: $%.2f", total));
    }
}
