package com.example.pozoleria;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private ListView listaCarrito;
    private CartListAdapter adapter;
    private ArrayList<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        listaCarrito = findViewById(R.id.listaCarrito);


        cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Pozole Verde", 85.0, 1));
        cartItems.add(new CartItem("Tostada", 12.0, 3));


        adapter = new CartListAdapter(this, cartItems);
        listaCarrito.setAdapter(adapter);
    }
}
