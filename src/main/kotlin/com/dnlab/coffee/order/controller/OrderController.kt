package com.dnlab.coffee.order.controller

import com.dnlab.coffee.order.domain.PaymentType
import com.dnlab.coffee.order.dto.Cart
import com.dnlab.coffee.order.dto.CartItem
import com.dnlab.coffee.order.service.OrderService
import com.dnlab.coffee.user.service.CustomerService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService,
    private val customerService: CustomerService
) {
    @ModelAttribute("cart")
    fun getCart(session: HttpSession): Cart =
        getCartFromSession(session)

    @GetMapping("/cart")
    fun showCart(session: HttpSession, model: ModelMap) {
        TODO("return view")
    }

    @PostMapping("/cart")
    fun addItemToCart(
        cartItem: CartItem,
        session: HttpSession
    ): String {
        val cart = getCartFromSession(session)
        cart.items.add(cartItem)

        TODO("return view")
    }

    @PostMapping
    fun processOrder(
        @RequestParam("phone") customerPhone: String,
        @RequestParam("payment") paymentType: PaymentType,
        session: HttpSession
    ): String {
        if (!customerService.isExistsPhone(phone = customerPhone)) {
            TODO("해당 처리 구현")
            return "redirect:/customer/new"
        }

        val cart = getCartFromSession(session)
        orderService.processOrder(customerPhone, paymentType, cart)
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
}