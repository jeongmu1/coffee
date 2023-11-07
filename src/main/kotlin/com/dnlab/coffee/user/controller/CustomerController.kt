package com.dnlab.coffee.user.controller

import com.dnlab.coffee.user.domain.Customer
import com.dnlab.coffee.user.service.CustomerService
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/customer")
class CustomerController(
    private val customerService: CustomerService
) {

    @GetMapping("/new")
    fun showCustomerForm(): String {
        return "customer/new"
    }

    @PostMapping("/new")
    fun addCustomer(customer: Customer,
                    @RequestParam(required = false, defaultValue = "true") fromPayment: Boolean): String {
        customerService.addCustomer(customer)
        return if (fromPayment) "redirect:/order" else "redirect:/"
    }

    @GetMapping("/point")
    fun getPointHistories(@RequestParam(required = true) phone: String,
                          modelMap: ModelMap): String {
        modelMap["histories"] = customerService.getPointHistories(phone)
        return "user/pointhistory"
    }
}