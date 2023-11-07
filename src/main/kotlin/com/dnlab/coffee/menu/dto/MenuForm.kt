package com.dnlab.coffee.menu.dto

import com.dnlab.coffee.menu.domain.Menu
import com.dnlab.coffee.menu.domain.ProductType

data class MenuForm(
    val name: String,
    val price: Int,
    val productType: ProductType,
    val specialMenu: Boolean = false,
    val recipes: List<RecipeForm> = mutableListOf()
) {
    fun toEntity(): Menu =
        Menu(
            name = this.name,
            productType = this.productType,
            price = this.price,
            specialMenu = this.specialMenu
        )
}
