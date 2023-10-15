package com.dnlab.coffee.vendor.dto

data class SupplyItemForm(
    var ingredientId: Long = 0,
    var amount: Double = 0.0,
    var price: Int = 0
)
