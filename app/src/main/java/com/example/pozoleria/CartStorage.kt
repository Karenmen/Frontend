package com.example.pozoleria.models

import com.example.pozoleria.models.CartItem


object CartStorage {

    private val cartItems = mutableListOf<CartItem>()

    fun addItem(name: String, price: Double) {
        val existing = cartItems.find { it.name == name }

        if (existing != null) {
            existing.quantity += 1
        } else {
            cartItems.add(CartItem(name, price, quantity = 1))
        }
    }

    fun clear() {
        cartItems.clear()
    }

    fun getItems(): MutableList<CartItem> = cartItems

    fun decreaseItem(name: String) {
        val existing = cartItems.find { it.name == name } ?: return
        existing.quantity--

        if (existing.quantity <= 0) {
            cartItems.remove(existing)
        }
    }

    fun getTotal(): Double {
        return cartItems.sumOf { it.price * it.quantity }
    }


}
