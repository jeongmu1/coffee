package com.dnlab.coffee.menu.repository

import com.dnlab.coffee.menu.domain.Menu
import com.dnlab.coffee.menu.domain.Recipe
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository: JpaRepository<Recipe, Long> {
    fun findRecipeById(id: Long): Recipe?
    fun findRecipesByMenu(menu: Menu): List<Recipe>
}