package com.dnlab.coffee.order.dto

import com.dnlab.coffee.order.domain.PaymentType

data class OrderForm(
    val customerPhone: String,
    val paymentType: PaymentType
)
