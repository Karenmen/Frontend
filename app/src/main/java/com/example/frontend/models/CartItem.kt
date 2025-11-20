package com.example.frontend.models

data class CartItem(
    val name: String,
    val price: Double,
    var quantity: Int
)