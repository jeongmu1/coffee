package com.dnlab.coffee.vendor.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class SupplyInfoDetail(
    val vendor: String,
    val deliveryDate: LocalDate,
    val actualDeliveryDate: LocalDate?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val items: List<SupplyItemInfo>
)

