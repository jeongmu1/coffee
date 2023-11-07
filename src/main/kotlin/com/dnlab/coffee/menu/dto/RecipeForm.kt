package com.dnlab.coffee.menu.dto

import com.dnlab.coffee.menu.domain.Ingredient
import com.dnlab.coffee.menu.domain.Menu
import com.dnlab.coffee.menu.domain.Recipe

data class RecipeForm(
    var ingredientId: Long = 0,
    var amount: Double = 0.0
) {
    fun toEntity(menu: Menu, ingredient: Ingredient): Recipe =
        Recipe(
            amount = this.amount,
            menu = menu,
            ingredient = ingredient
        )
}
