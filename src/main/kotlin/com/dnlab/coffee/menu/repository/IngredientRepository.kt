package com.dnlab.coffee.menu.repository

import com.dnlab.coffee.menu.domain.Ingredient
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository : JpaRepository<Ingredient, Long> {
    fun findIngredientByName(name: String): Ingredient?
    fun findIngredientById(id: Long): Ingredient?
}