package com.dnlab.coffee.order.controller

import com.dnlab.coffee.global.util.LoggerDelegate
import com.dnlab.coffee.order.dto.Cart
import com.dnlab.coffee.order.dto.CartItem
import com.dnlab.coffee.order.dto.CartItemDisplay
import com.dnlab.coffee.order.dto.OrderForm
import com.dnlab.coffee.order.service.OrderService
import com.dnlab.coffee.user.service.CustomerService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService,
    private val customerService: CustomerService
) {
    private val logger by LoggerDelegate()
    private val cartInfoUrlRedirect = "redirect:/order/cart/added"

    @ModelAttribute("cart")
    fun getCart(session: HttpSession): List<CartItemDisplay> =
        orderService.convertCartToDtoList(getCartFromSession(session))

    @GetMapping("/cart/added")
    fun showAddedCartResult(model: ModelMap, session: HttpSession): String {
        model["totalPrice"] = orderService.convertCartToDtoList(getCartFromSession(session))
            .sumOf { it.totalPrice }
        return "cart/added"
    }

    @PostMapping("/cart")
    fun addItemToCart(
        cartItem: CartItem,
        session: HttpSession
    ): String {
        val cart = getCartFromSession(session)
        cart.items.find { it.itemId == cartItem.itemId }?.apply {
            quantity += cartItem.quantity
        } ?: cart.items.add(cartItem)
        logger.info("Cart Currently : ${cart.items}")

        return cartInfoUrlRedirect
    }

    @DeleteMapping("/cart")
    fun resetCart(session: HttpSession): String {
        setNewCartToSession(session)
        return "redirect:/menu"
    }

    @PatchMapping("/cart/{itemId}/quantity")
    fun updateMenuQuantity(
        @PathVariable itemId: Long,
        quantity: Int,
        session: HttpSession
    ): String {
        getMenuFromCart(itemId, session).quantity = quantity
        return cartInfoUrlRedirect
    }

    @DeleteMapping("/cart/{itemId}")
    fun deleteMenu(
        @PathVariable itemId: Long,
        session: HttpSession
    ): String {
        val cart = getCartFromSession(session)
        cart.items.removeIf { it.itemId == itemId }
        return if (cart.items.isEmpty()) "redirect:/menu" else cartInfoUrlRedirect
    }

    @GetMapping
    fun showOrderForm(): String {
        return "order/payment"
    }

    @PostMapping
    fun processOrder(
        orderForm: OrderForm,
        session: HttpSession
    ): String {
        if (!customerService.isExistsPhone(phone = orderForm.customerPhone)) {
            return "redirect:/customer/new"
        }

        val cart = getCartFromSession(session)
        orderService.processOrder(orderForm.customerPhone, orderForm.paymentType, cart)
        cart.items.clear()

        return "redirect:/order/complete"
    }

    private fun getCartFromSession(session: HttpSession): Cart =
        session.getAttribute("cart") as? Cart
            ?: setNewCartToSession(session)

    private fun setNewCartToSession(session: HttpSession): Cart {
        val newCart = Cart()
        session.setAttribute("cart", newCart)
        return newCart
    }

    private fun getMenuFromCart(itemId: Long, session: HttpSession): CartItem =
        getCartFromSession(session).items.find { it.itemId == itemId }!!
}