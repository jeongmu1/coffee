package com.dnlab.coffee.menu.service

import com.dnlab.coffee.menu.domain.Ingredient
import com.dnlab.coffee.menu.repository.IngredientRepository
import org.springframework.stereotype.Service

@Service
class IngredientService(
    private val ingredientRepository: IngredientRepository
) {

    fun getAllIngredients(): List<Ingredient> {
        return ingredientRepository.findAll()
    }

    fun saveIngredient(ingredient: Ingredient) {
        ingredientRepository.save(ingredient)
    }
}