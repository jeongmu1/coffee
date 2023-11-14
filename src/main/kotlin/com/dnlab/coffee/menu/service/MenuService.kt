package com.dnlab.coffee.menu.service

import com.dnlab.coffee.menu.domain.Ingredient
import com.dnlab.coffee.menu.domain.Menu
import com.dnlab.coffee.menu.domain.Recipe
import com.dnlab.coffee.menu.domain.SpecialMenu
import com.dnlab.coffee.menu.dto.*
import com.dnlab.coffee.menu.repository.MenuRepository
import com.dnlab.coffee.vendor.domain.Supply
import com.dnlab.coffee.vendor.domain.SupplyItem
import com.dnlab.coffee.vendor.repository.SupplyItemRepository
import com.dnlab.coffee.vendor.repository.SupplyRepository
import com.dnlab.coffee.vendor.repository.VendorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class MenuService(
    private val menuRepository: MenuRepository,
    private val recipeService: RecipeService,
    private val vendorRepository: VendorRepository,
    private val supplyRepository: SupplyRepository,
    private val supplyItemRepository: SupplyItemRepository
) {

    @Transactional(readOnly = true)
    fun getMenuDisplays(): List<MenuDisplay> = menuRepository.findAll().map { it.toDisplay() }

    @Transactional(readOnly = true)
    fun getMenuDisplays(menuName: String): List<MenuDisplay> =
        menuRepository.findMenusByNameContains(menuName).map { it.toDisplay() }

    @Transactional(readOnly = true)
    fun getMenuDisplay(menuId: Long): MenuDisplay = getMenuById(menuId).toDisplay()

    @Transactional
    fun createMenu(menuForm: MenuForm) {
        val menu = menuRepository.save(menuForm.toEntity())
        recipeService.addRecipesToMenu(menuForm.recipes, menu)
    }

    @Transactional(readOnly = true)
    fun getRecipesOfMenu(menuId: Long): List<RecipeInfo> {
        val menu = getMenuById(menuId)
        return recipeService.getRecipesOfMenu(menu).map { it.toRecipeInfo() }
    }

    @Transactional
    fun removeMenu(menuId: Long) {
        val menu = getMenuById(menuId)
        menuRepository.delete(menu)
    }

    @Transactional
    fun addRecipes(menuId: Long, recipeForm: NewRecipeForm) {
        val menu = getMenuById(menuId)
        recipeService.addRecipesToMenu(recipeForm.recipes, menu)
    }

    @Transactional
    fun updateSpecialMenu(menuId: Long, specialMenu: Boolean) {
        val menu = getMenuById(menuId)
        menu.specialMenu = specialMenu
    }

    @Transactional
    fun updateRecommend(menuId: Long, recommendForm: RecommendForm) {
        val menu = getMenuById(menuId)
        val str = recommendForm.kind
        if (str == "대표") {
            menu.kind = SpecialMenu.대표
        } else if (str == "추천") {
            menu.kind = SpecialMenu.추천
            checkStock(menuId)
        } else {
            menu.kind = null
        }
    }

    fun checkStock(menuId: Long) {
        val menu = getMenuById(menuId)
        var no: MutableList<Recipe> = mutableListOf()
        for (recipe in menu.recipes) {
            val ingredient = recipe.ingredient
            if (ingredient.stock - (recipe.amount * 30) < 0) {
                no.add(recipe)
            }
        }

        if (no.size > 0) {
            val vendor = vendorRepository.findAll().first()
            val supply = supplyRepository.save(Supply(
                vendor = vendor,
                deliveryDate = LocalDate.now(),
                actualDeliveryDate = LocalDate.now()
            ))

            supplyItemRepository.saveAll(no.map {
                it.ingredient.stock += it.amount * 30
                SupplyItem(
                ingredient = it.ingredient,
                supply = supply,
                amount = it.amount * 30,
                price = 50000
            )})
        }
    }

    fun getMenuById(menuId: Long): Menu = menuRepository.findMenuById(menuId)
        ?: throw NoSuchElementException("해당 메뉴를 찾을 수 없습니다. : $menuId")
}