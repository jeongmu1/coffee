package com.dnlab.coffee.menu.service

import com.dnlab.coffee.menu.domain.Ingredient
import com.dnlab.coffee.menu.dto.AmountForm
import com.dnlab.coffee.menu.repository.IngredientRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    @Transactional
    fun updateAmount(amountForm: AmountForm) {
        ingredientRepository.findIngredientById(amountForm.ingredientId)
            ?.let { it.stock = amountForm.amount }
            ?: throw NoSuchElementException("해당 재료를 찾을 수 없습니다.")
    }
}