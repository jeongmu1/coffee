package com.dnlab.coffee.menu.controller

import com.dnlab.coffee.menu.domain.Ingredient
import com.dnlab.coffee.menu.domain.ProductType
import com.dnlab.coffee.menu.dto.MenuForm
import com.dnlab.coffee.menu.dto.NewRecipeForm
import com.dnlab.coffee.menu.service.IngredientService
import com.dnlab.coffee.menu.service.MenuService
import com.dnlab.coffee.menu.service.RecipeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/menu")
class MenuController(
    private val menuService: MenuService,
    private val recipeService: RecipeService,
    private val ingredientService: IngredientService
) {
    @ModelAttribute("productTypes")
    fun getProductTypes(): List<ProductType> = ProductType.values().toList()

    @ModelAttribute("ingredients")
    fun getIngredients(): List<Ingredient> = ingredientService.getAllIngredients()

    @GetMapping
    fun showMenus(
        @RequestParam("menu", required = false) menuName: String?,
        model: ModelMap
    ): String {
        model["menus"] = menuName?.let { menuService.getMenuDisplays(it) } ?: menuService.getMenuDisplays()
        return "menu/list"
    }

    @GetMapping("/{menuId}")
    fun showMenuDetail(
        @PathVariable("menuId") menuId: Long,
        model: ModelMap
    ): String {
        model["menu"] = menuService.getMenuDisplay(menuId)
        model["recipes"] = menuService.getRecipesOfMenu(menuId)
        return "menu/detail"
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    fun showMenuForm(): String {
        return "menu/new"
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    fun createMenu(menuForm: MenuForm): String {
        menuService.createMenu(menuForm)
        return "redirect:/menu"
    }

    @DeleteMapping("/{menuId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun removeMenu(@PathVariable("menuId") menuId: Long): String {
        menuService.removeMenu(menuId)
        return "redirect:/menu"
    }

    @PostMapping("/{menuId}/recipe")
    @PreAuthorize("hasRole('ADMIN')")
    fun addRecipe(
        @PathVariable("menuId") menuId: Long,
        newRecipeForm: NewRecipeForm,
    ): String {
        menuService.addRecipes(menuId, newRecipeForm)
        return "redirect:/menu/$menuId"
    }

    @PatchMapping("/{menuId}/recipe/{recipeId}/amount")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateAmountOfRecipe(
        @PathVariable("menuId") menuId: Long,
        @PathVariable("recipeId") recipeId: Long,
        amount: Double
    ): String {
        recipeService.updateAmountOfRecipe(recipeId, amount)
        return "redirect:/menu/$menuId"
    }

    @DeleteMapping("/{menuId}/recipe/{recipeId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteRecipe(
        @PathVariable("recipeId") recipeId: Long,
        @PathVariable("menuId") menuId: Long
    ): String {
        recipeService.removeRecipe(recipeId)
        return "redirect:/menu/$menuId"
    }

    @PatchMapping("/{menuId}/special-menu")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateSpecialMenu(
        @PathVariable("menuId") menuId: Long,
        specialMenu: Boolean
    ): String {
        menuService.updateSpecialMenu(menuId, specialMenu)
        return "redirect:/menu/$menuId"
    }
}