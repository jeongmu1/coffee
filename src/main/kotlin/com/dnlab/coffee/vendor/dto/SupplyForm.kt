package com.dnlab.coffee.vendor.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class SupplyForm(
    val ingredientName: String,
    val vendorId: Long,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val deliveryDate: LocalDate,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val actualDeliveryDate: LocalDate?,
    val price: Int,
    val quantity: Double
)
