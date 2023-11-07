package com.dnlab.coffee.order.service

import com.dnlab.coffee.menu.service.MenuService
import com.dnlab.coffee.menu.service.RecipeService
import com.dnlab.coffee.order.domain.CustomerOrder
import com.dnlab.coffee.order.domain.PaymentType
import com.dnlab.coffee.order.dto.*
import com.dnlab.coffee.order.repository.CustomOrderRepository
import com.dnlab.coffee.order.repository.OrderMenuRepository
import com.dnlab.coffee.user.domain.PointHistory
import com.dnlab.coffee.user.repository.PointHistoryRepository
import com.dnlab.coffee.user.service.CustomerService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val customOrderRepository: CustomOrderRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val customerService: CustomerService,
    private val menuService: MenuService,
    private val recipeService: RecipeService, private val pointHistoryRepository: PointHistoryRepository
) {
    @Transactional
    fun processOrder(customerPhone: String, paymentType: PaymentType, cart: Cart, pointUse: Int) {
        val customer = customerService.getCustomerByPhone(customerPhone)

        if (pointUse == 0) {
            customer.stamp += cart.items.sumOf { it.quantity }

            val changes = cart.items.sumOf {
                menuService.getMenuById(it.itemId).price * it.quantity / 10
            }
            customer.point += changes
            pointHistoryRepository.save(PointHistory(
                changes = changes,
                content = "주문적립",
                customer = customer
            ))
        } else {
            if (customer.stamp >= 10 && pointUse % 1000 == 0) {
                customer.stamp -= 10
                customer.point -= pointUse

                pointHistoryRepository.save(PointHistory(
                    changes = -1 * pointUse,
                    customer = customer,
                    content = "포인트주문"
                ))
            } else throw IllegalArgumentException("포인트 사용량은 1000단위여야 합니다.")
        }

        val order = customOrderRepository.save(
            CustomerOrder(
                customer = customer,
                paymentType = paymentType,
                pointUse = pointUse
            )
        )

        cart.items.forEach { cartItem ->
            val orderMenu = orderMenuRepository.save(cartItem.toEntity(menuService.getMenuById(cartItem.itemId), order))
            recipeService.updateRecipesOnOrder(orderMenu)
        }
    }

    @Transactional(readOnly = true)
    fun getOrderHistories(): List<OrderInfo> =
        customOrderRepository.findAll().map { it.toOrderInfo() }

    @Transactional(readOnly = true)
    fun getOrderHistory(orderId: Long): OrderInfo =
        customOrderRepository.findCustomerOrderById(orderId)?.toOrderInfo()
            ?: throw NoSuchElementException("해당 주문을 찾을 수 없습니다 : $orderId")

    fun convertCartToDtoList(cart: Cart): List<CartItemDisplay> =
        cart.items.map { it.toCartItemDisplay(menuService.getMenuById(it.itemId)) }

}