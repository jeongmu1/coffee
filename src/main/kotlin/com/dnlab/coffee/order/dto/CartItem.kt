package com.dnlab.coffee.order.dto

import com.dnlab.coffee.menu.domain.Menu
import com.dnlab.coffee.order.domain.CustomerOrder
import com.dnlab.coffee.order.domain.OrderMenu

data class CartItem(
    val itemId: Long,
    var quantity: Int
) {
    fun toCartItemDisplay(menu: Menu): CartItemDisplay =
        CartItemDisplay(
            itemId = menu.id,
            menu = menu.name,
            price = menu.price,
            quantity = this.quantity
        )

    fun toEntity(menu: Menu, order: CustomerOrder): OrderMenu {
        return OrderMenu(
            customerOrder = order,
            menu = menu,
            quantity = this.quantity,
            price = this.quantity * menu.price
        )
    }
}
