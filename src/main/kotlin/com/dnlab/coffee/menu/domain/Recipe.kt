package com.dnlab.coffee.menu.domain

import com.dnlab.coffee.global.domain.BaseEntity
import com.dnlab.coffee.menu.dto.RecipeInfo
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Recipe(
    @ManyToOne(fetch = FetchType.LAZY)
    val menu: Menu,
    @ManyToOne(fetch = FetchType.LAZY)
    val ingredient: Ingredient,
    @Column(nullable = false)
    val amount: Double
) : BaseEntity() {
    fun toRecipeInfo(): RecipeInfo =
        RecipeInfo(
            ingredient = this.ingredient.name,
            amount = this.amount,
            measurementUnit = this.ingredient.measurementUnit.unit
        )
}