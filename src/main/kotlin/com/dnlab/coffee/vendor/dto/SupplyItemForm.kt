package com.dnlab.coffee.vendor.dto

import com.dnlab.coffee.menu.domain.Ingredient
import com.dnlab.coffee.vendor.domain.Supply
import com.dnlab.coffee.vendor.domain.SupplyItem

data class SupplyItemForm(
    var ingredientId: Long = 0,
    var amount: Double = 0.0,
    var price: Int = 0
) {
    fun toEntity(supply: Supply, ingredient: Ingredient) =
        SupplyItem(
            supply = supply,
            ingredient = ingredient,
            amount = this.amount,
            price = this.price
        )
}
