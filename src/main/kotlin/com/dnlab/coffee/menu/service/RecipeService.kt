package com.dnlab.coffee.menu.service

import com.dnlab.coffee.menu.domain.Menu
import com.dnlab.coffee.menu.domain.Recipe
import com.dnlab.coffee.menu.dto.RecipeForm
import com.dnlab.coffee.menu.repository.RecipeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecipeService(
    private val recipeRepository: RecipeRepository,
    private val ingredientService: IngredientService
) {

    @Transactional
    fun addRecipesOfMenu(recipeForms: List<RecipeForm>, menu: Menu): List<Recipe> =
        recipeRepository.saveAll(recipeForms.map { it.toEntity(menu) })

    fun getRecipesOfMenu(menu: Menu): List<Recipe> = recipeRepository.findRecipesByMenu(menu)

    fun getRecipeById(recipeId: Long): Recipe =
        recipeRepository.findRecipeById(recipeId) ?: throw NoSuchElementException("해당 레시피를 찾을 수 없습니다 : $recipeId")

    fun removeRecipe(recipeId: Long) {
        val recipe = getRecipeById(recipeId)
        recipeRepository.delete(recipe)
    }

    @Transactional
    fun updateAmountOfRecipe(recipeId: Long, amount: Double) {
        val recipe = getRecipeById(recipeId)
        recipe.amount = amount
    }

    private fun RecipeForm.toEntity(menu: Menu): Recipe {
        val ingredient = ingredientService.findIngredientById(this.ingredientId)

        return Recipe(
            amount = this.amount,
            menu = menu,
            ingredient = ingredient
        )
    }
}