package com.dnlab.coffee.order.service

import com.dnlab.coffee.menu.repository.MenuRepository
import com.dnlab.coffee.order.domain.CustomerOrder
import com.dnlab.coffee.order.domain.OrderMenu
import com.dnlab.coffee.order.domain.PaymentType
import com.dnlab.coffee.order.dto.Cart
import com.dnlab.coffee.order.dto.CartItem
import com.dnlab.coffee.order.repository.CustomOrderRepository
import com.dnlab.coffee.order.repository.OrderMenuRepository
import com.dnlab.coffee.user.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val customerRepository: CustomerRepository,
    private val customOrderRepository: CustomOrderRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val menuRepository: MenuRepository
) {

    @Transactional
    fun processOrder(customerPhone: String, paymentType: PaymentType, cart: Cart) {
        val customer = customerRepository.findCustomerByPhone(customerPhone)
            ?: throw NoSuchElementException("해당 전화번호를 가진 사용자가 없습니다.")
        val order = customOrderRepository.save(
            CustomerOrder(
                customer = customer,
                paymentType = paymentType
            )
        )
        cart.items.forEach { orderMenuRepository.save(it.toEntity(order)) }
    }

    private fun CartItem.toEntity(order: CustomerOrder): OrderMenu {
        val menu = menuRepository.findMenuById(this.itemId)
            ?: throw NoSuchElementException("해당 메뉴는 존재하지 않습니다, id : ${this.itemId}")

        return OrderMenu(
            customerOrder = order,
            menu = menu,
            quantity = this.quantity
        )
    }
}