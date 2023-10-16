package com.dnlab.coffee.menu.controller

import com.dnlab.coffee.menu.domain.Ingredient
import com.dnlab.coffee.menu.domain.ProductType
import com.dnlab.coffee.menu.dto.MenuForm
import com.dnlab.coffee.menu.service.IngredientService
import com.dnlab.coffee.menu.service.MenuService
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/menu")
class MenuController(
    private val menuService: MenuService,
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
        model["menus"] =
            if (menuName == null) menuService.getMenus() else menuService.getMenus(menuName)
        return "menu/list"
    }

    @GetMapping("/detail")
    fun showMenuDetail(
        @RequestParam("id") menuId: Long,
        model: ModelMap
    ): String {
        model["menu"] = menuService.getMenu(menuId)
        return "menu/detail"
    }

    @GetMapping("/new")
    fun showMenuForm(): String {
        return "/menu/new"
    }

    @PostMapping("/new")
    fun createMenu(menuForm: MenuForm): String {
        menuService.createMenu(menuForm)
        return "redirect:/menu"
    }
}