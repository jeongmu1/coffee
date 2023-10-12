package com.dnlab.coffee.menu.domain

import com.dnlab.coffee.global.domain.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Menu(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val price: Int,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val productType: ProductType
) : BaseTimeEntity() {
    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private val _recipes: MutableList<Recipe> = mutableListOf()
    val recipes: List<Recipe>
        get() = _recipes

    fun isSoldOuted(): Boolean = this.recipes.any { (it.ingredient.stock - it.quantity) < 0 }
}