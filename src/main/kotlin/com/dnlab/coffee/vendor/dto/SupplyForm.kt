package com.dnlab.coffee.vendor.dto

import java.time.LocalDate

data class SupplyForm(
    val deliveryDate: LocalDate,
    val actualDeliveryDate: LocalDate?,
    val vendorId: Long,
    val supplyItems: List<SupplyItemForm> = mutableListOf()
)
