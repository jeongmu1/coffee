package com.dnlab.coffee.vendor.dto

data class SupplyItemInfo(
    val ingredient: String,
    val measurementUnit: String,
    val amount: Double,
    val price: Int
)