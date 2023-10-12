package com.dnlab.coffee.order.dto

data class CartItemDisplay(
    val menu: String,
    val price: Int,
    val quantity: Int,
) {
    val totalPrice: Int
        get() = price * quantity
}
