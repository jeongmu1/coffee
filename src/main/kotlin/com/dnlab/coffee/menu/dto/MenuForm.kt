package com.dnlab.coffee.menu.dto

import com.dnlab.coffee.menu.domain.ProductType

data class MenuForm(
    val name: String,
    val price: Int,
    val productType: ProductType,
    val recipes: List<RecipeForm> = mutableListOf()
)
