package com.example.frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.frontend.databinding.ActivityCartBinding
import com.example.frontend.adapters.CartAdapter
import com.example.frontend.models.CartItem


class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cartItems = listOf(
            CartItem("Taco Dorado", 25.0, 1),
            CartItem("Pozole Grande", 80.0, 2),
            CartItem("Aguas Frescas", 20.0, 1)
        )
        val recycler = binding.recyclerCart
        recycler.adapter = CartAdapter(cartItems)

    }
}