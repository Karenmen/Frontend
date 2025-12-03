package com.example.pozoleria.models

data class CartItem(
    val name: String,
    val price: Double,
    var quantity: Int
)