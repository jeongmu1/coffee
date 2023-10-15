package com.dnlab.coffee.vendor.controller

import com.dnlab.coffee.vendor.dto.ActualDeliveryDateForm
import com.dnlab.coffee.vendor.service.SupplyService
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("supply")
class SupplyController(
    private val supplyService: SupplyService
) {
    @GetMapping
    fun showSupplyHistory(model: ModelMap): String {
        model["supplies"] = supplyService.getSupplyInfoList()
        return "supply/info"
    }

    @PostMapping("delivery-date")
    fun addActualDeliveryDate(form: ActualDeliveryDateForm) {
        supplyService.inputActualDeliveryDate(form)
    }
}