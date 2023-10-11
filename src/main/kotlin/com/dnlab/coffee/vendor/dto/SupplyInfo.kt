package com.dnlab.coffee.vendor.dto

import java.time.LocalDate

data class SupplyInfo(
    val supplyId: Long,
    val ingredientName: String,
    val measurementUnit: String,
    val price: Int,
    val quantity: Double,
    val deliveryDate: LocalDate,
    val actualDeliveryDate: LocalDate?,
    val vendorName: String
)
