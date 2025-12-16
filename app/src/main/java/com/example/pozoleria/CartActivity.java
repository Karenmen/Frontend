package com.example.pozoleria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pozoleria.adapters.CartListAdapter;
import com.example.pozoleria.models.CartStorage;
import com.example.pozoleria.view.SeleccionarMetodoPagoActivity;

public class CartActivity extends AppCompatActivity {

    private ListView listaCarrito;
    private CartListAdapter adapter;
    private TextView txtTotal;
    private Button btnPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Vistas
        listaCarrito = findViewById(R.id.listaCarrito);
        txtTotal = findViewById(R.id.txtTotal);
        btnPagar = findViewById(R.id.btnPagar);

        // Adapter del carrito (NO se modifica)
        adapter = new CartListAdapter(this, CartStorage.INSTANCE.getItems());
        listaCarrito.setAdapter(adapter);

        // Botón proceder al pago (NUEVO)
        btnPagar.setOnClickListener(v -> {
            Intent intent = new Intent(
                    CartActivity.this,
                    SeleccionarMetodoPagoActivity.class
            );
            startActivity(intent);
        });

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
