package com.dnlab.coffee.menu.controller

import com.dnlab.coffee.menu.domain.Ingredient
import com.dnlab.coffee.menu.domain.MeasurementUnit
import com.dnlab.coffee.menu.dto.AmountForm
import com.dnlab.coffee.menu.service.IngredientService
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ingredient")
class IngredientController(
    private val ingredientService: IngredientService
) {
    @ModelAttribute("measurementUnits")
    fun findAllMeasurementUnits(): List<MeasurementUnit>{
        return MeasurementUnit.values().toList()
    }

    @GetMapping
    fun showIngredientList(model: ModelMap): String {
        model["ingredients"] = ingredientService.getAllIngredients()
        return "ingredient/list"
    }

    @GetMapping("/new")
    fun showIngredientForm(): String {
        return "ingredient/new"
    }

    @PostMapping("/new")
    fun inputIngredient(ingredient: Ingredient): String {
        ingredientService.saveIngredient(ingredient)
        return "redirect:/ingredient"
    }

    @PatchMapping("/amount")
    fun updateIngredientAmount(amountForm: AmountForm): String {
        ingredientService.updateAmount(amountForm)
        return "redirect:/ingredient"
    }
}