package com.dnlab.coffee.vendor.dto

import java.time.LocalDate

data class SupplyInfo(
    val supplyId: Long,
    val vendor: String,
    val deliveryDate: LocalDate,
    val actualDeliveryDate: LocalDate? = null,
    val createdAt: LocalDate,
    val updatedAt: LocalDate
)
