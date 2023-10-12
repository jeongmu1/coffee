package com.dnlab.coffee.menu.dto

data class MenuDisplay(
    val id: Long,
    val name: String,
    val price: Int,
    val productType: String,
    val soldOuted: Boolean
)
