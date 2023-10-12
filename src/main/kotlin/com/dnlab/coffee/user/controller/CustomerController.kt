package com.dnlab.coffee.user.controller

import com.dnlab.coffee.user.domain.Customer
import com.dnlab.coffee.user.service.CustomerService
import org.springframework.stereotype.Controller

@Controller
class CustomerController(
    private val customerService: CustomerService
) {
    fun addCustomer(customer: Customer): String {
        customerService.addCustomer(customer)
        TODO("view")
    }
}