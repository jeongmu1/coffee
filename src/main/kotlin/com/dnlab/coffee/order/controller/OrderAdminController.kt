package com.dnlab.coffee.order.controller

import com.dnlab.coffee.order.service.OrderService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/order/admin")
@PreAuthorize("hasRole('ADMIN')")
class OrderAdminController(
    private val orderService: OrderService
) {
    @GetMapping
    fun showOrderHistory(model: ModelMap): String {
        model["orders"] = orderService.getOrderHistories()
        return "order/admin/history"
    }

    @GetMapping("/{orderId}")
    fun showOrderHistoryDetail(
        @PathVariable orderId: Long,
        model: ModelMap
    ): String {
        model["order"] = orderService.getOrderHistory(orderId)
        return "order/admin/detail"
    }
}