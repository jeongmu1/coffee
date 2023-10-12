package com.dnlab.coffee.user.repository

import com.dnlab.coffee.user.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long> {
    fun findCustomerByPhone(phone: String): Customer?
}