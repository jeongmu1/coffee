package com.dnlab.coffee.menu.controller

import com.dnlab.coffee.menu.service.MenuService
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/menu")
class MenuController(
    private val menuService: MenuService
) {
    @GetMapping
    fun showMenus(
        @RequestParam("menu", required = false) menuName: String?,
        model: ModelMap
    ): String {
        model["menus"] = if (menuName == null) menuService.getMenus() else menuService.getMenus(menuName)
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
}