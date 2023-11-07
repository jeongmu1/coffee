package com.dnlab.coffee.menu.service

import com.dnlab.coffee.menu.domain.Menu
import com.dnlab.coffee.menu.dto.*
import com.dnlab.coffee.menu.repository.MenuRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MenuService(
    private val menuRepository: MenuRepository,
    private val recipeService: RecipeService
) {

    @Transactional(readOnly = true)
    fun getMenus(): List<MenuDisplay> = menuRepository.findAll().map { it.toDisplay() }

    @Transactional(readOnly = true)
    fun getMenus(name: String): List<MenuDisplay> = menuRepository.findMenusByNameContains(name).map { it.toDisplay() }

    @Transactional(readOnly = true)
    fun getMenu(id: Long): MenuDisplay = findMenuById(id).toDisplay()

    @Transactional
    fun createMenu(menuForm: MenuForm) {
        val menu = menuRepository.save(menuForm.toEntity())
        recipeService.addRecipesOfMenu(menuForm.recipes, menu)
    }

    fun getRecipesOfMenu(menuId: Long): List<RecipeInfo> {
        val menu = findMenuById(menuId)
        return recipeService.getRecipesOfMenu(menu).map { it.toRecipeInfo() }
    }

    fun removeMenu(menuId: Long) {
        val menu = findMenuById(menuId)
        menuRepository.delete(menu)
    }

    @Transactional
    fun addRecipes(menuId: Long, recipeForm: NewRecipeForm) {
        val menu = findMenuById(menuId)
        recipeService.addRecipesOfMenu(recipeForm.recipes, menu)
    }

    @Transactional
    fun updateSpecialMenu(menuId: Long, specialMenu: Boolean) {
        val menu = findMenuById(menuId)
        menu.specialMenu = specialMenu
    }

    private fun findMenuById(menuId: Long): Menu = menuRepository.findMenuById(menuId)
        ?: throw NoSuchElementException("해당 메뉴를 찾을 수 없습니다. : $menuId")
}