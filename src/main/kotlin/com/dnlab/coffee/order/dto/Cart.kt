package com.dnlab.coffee.order.dto

data class Cart(
    val items: MutableList<CartItem> = mutableListOf()
)
