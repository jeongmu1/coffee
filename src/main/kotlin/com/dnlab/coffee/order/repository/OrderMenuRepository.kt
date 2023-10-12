package com.dnlab.coffee.order.repository

import com.dnlab.coffee.order.domain.OrderMenu
import org.springframework.data.jpa.repository.JpaRepository

interface OrderMenuRepository : JpaRepository<OrderMenu, Long>