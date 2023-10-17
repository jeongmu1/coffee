package com.dnlab.coffee.order.dto

import java.time.LocalDateTime

data class OrderInfo(
    val id: Long,
    val customer: String, // 고객 이름
    val customerPhone: String,
    val paymentType: String,
    val createdAt: LocalDateTime,
    val menus: List<OrderMenuInfo>,
    val totalPrice: Int = menus.sumOf { it.price }
)
