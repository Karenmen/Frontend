package com.example.pozoleria

object CartStorage {
    val cartItems = mutableListOf<CartItem>()

    fun addItem(name: String, price: Double) {
        val existing = cartItems.find { it.name == name }
        if (existing != null) {
            existing.quantity += 1
        } else {
            cartItems.add(CartItem(name, price, 1))
        }
    }
}
