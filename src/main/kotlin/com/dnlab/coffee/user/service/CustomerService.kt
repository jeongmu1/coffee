package com.dnlab.coffee.user.service

import com.dnlab.coffee.user.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) {

    fun isExistsPhone(phone: String): Boolean =
        customerRepository.existsCustomerByPhone(phone)
}