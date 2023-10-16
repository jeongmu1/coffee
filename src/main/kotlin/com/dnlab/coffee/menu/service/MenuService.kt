package com.dnlab.coffee.menu.service

import com.dnlab.coffee.global.util.LoggerDelegate
import com.dnlab.coffee.menu.domain.Menu
import com.dnlab.coffee.menu.domain.Recipe
import com.dnlab.coffee.menu.dto.MenuDisplay
import com.dnlab.coffee.menu.dto.MenuForm
import com.dnlab.coffee.menu.dto.RecipeForm
import com.dnlab.coffee.menu.repository.IngredientRepository
import com.dnlab.coffee.menu.repository.MenuRepository
import com.dnlab.coffee.menu.repository.RecipeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MenuService(
    private val menuRepository: MenuRepository,
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository
) {
    private val logger by LoggerDelegate()

    @Transactional(readOnly = true)
    fun getMenus(): List<MenuDisplay> {
        val menus = menuRepository.findAll().map { it.toDisplay() }
        logger.info("menus : $menus")
        return menus
    }

    @Transactional(readOnly = true)
    fun getMenus(name: String): List<MenuDisplay> {
        val menus = menuRepository.findMenusByNameContains(name).map { it.toDisplay() }
        logger.info("menus : $menus")
        return menus
    }

    @Transactional(readOnly = true)
    fun getMenu(id: Long): MenuDisplay =
        menuRepository.findMenuById(id)?.toDisplay()
            ?: throw NoSuchElementException("해당 메뉴는 존재하지 않습니다.")

    @Transactional
    fun createMenu(menuForm: MenuForm) {
        val menu = menuRepository.save(
            Menu(
                name = menuForm.name,
                productType = menuForm.productType,
                price = menuForm.price
            )
        )
        recipeRepository.saveAll(menuForm.recipes.map { it.toEntity(menu) })
    }

    private fun RecipeForm.toEntity(menu: Menu): Recipe {
        val ingredient = ingredientRepository.findIngredientById(this.ingredientId)
            ?: throw NoSuchElementException("해당 재료를 찾을 수 없습니다 : ingredientId = $ingredientId")

        return Recipe(
            amount = this.amount,
            menu = menu,
            ingredient = ingredient
        )
    }
}