package com.dnlab.coffee.vendor.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class ActualDeliveryDateForm(
    val supplyId: Long,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val actualDeliveryDate: LocalDate
)
