package com.dnlab.coffee.vendor.controller

import com.dnlab.coffee.vendor.dto.VendorForm
import com.dnlab.coffee.vendor.service.VendorService
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/vendor")
class VendorController(
    private val vendorService: VendorService
) {
    @GetMapping
    fun showVendorList(model: ModelMap): String {
        model["vendors"] = vendorService.getVendorList()
        return "vendor/list"
    }

    @GetMapping("/new")
    fun newVendorForm(): String {
        return "vendor/new"
    }

    @PostMapping("/new")
    fun saveVendor(vendorForm: VendorForm): String {
        vendorService.saveNewVendor(vendorForm)
        return "redirect:/vendor"
    }
}