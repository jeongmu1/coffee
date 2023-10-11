package com.dnlab.coffee.menu.dto

import com.dnlab.coffee.menu.domain.ProductType

data class MenuDisplay(
    val id: Long,
    val name: String,
    val price: Int,
    val productType: ProductType,
    val soldOuted: Boolean
)
