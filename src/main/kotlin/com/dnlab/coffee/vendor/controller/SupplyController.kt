package com.dnlab.coffee.vendor.controller

import com.dnlab.coffee.vendor.dto.ActualDeliveryDateForm
import com.dnlab.coffee.vendor.dto.SupplyForm
import com.dnlab.coffee.vendor.service.SupplyService
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/supply")
class SupplyController(
    private val supplyService: SupplyService
) {
    @GetMapping
    fun showSupplyHistory(
        @RequestParam(required = false) ingredient: String?,
        model: ModelMap
    ): String {
        model["supplies"] = ingredient
            ?.let { supplyService.getSupplyInfoList(it) }
            ?: supplyService.getSupplyInfoList()
        return "supply/list"
    }

    @PatchMapping("/actual-delivery-date")
    fun inputActualDeliveryDate(form: ActualDeliveryDateForm): String {
        supplyService.inputActualDeliveryDate(form)
        return "redirect:/supply"
    }

    @GetMapping("/new")
    fun getSupplyForm(): String {
        return "supply/new"
    }

    @PostMapping("/new")
    fun addSupply(supplyForm: SupplyForm): String {
        supplyService.addSupply(supplyForm)
        return "redirect:/supply"
    }
}