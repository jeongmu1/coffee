package com.dnlab.coffee.order.repository

import com.dnlab.coffee.order.domain.CustomerOrder
import org.springframework.data.jpa.repository.JpaRepository

interface CustomOrderRepository : JpaRepository<CustomerOrder, Long>