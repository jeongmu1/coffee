package com.dnlab.coffee.user.service

import com.dnlab.coffee.user.domain.Customer
import com.dnlab.coffee.user.domain.PointHistory
import com.dnlab.coffee.user.repository.CustomerRepository
import com.dnlab.coffee.user.repository.PointHistoryRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository, private val pointHistoryRepository: PointHistoryRepository
) {

    fun isExistsPhone(phone: String): Boolean =
        customerRepository.existsCustomerByPhone(phone)

    fun addCustomer(customer: Customer) {
        customerRepository.save(customer)
    }

    fun getCustomerByPhone(customerPhone: String): Customer = customerRepository.findCustomerByPhone(customerPhone)
        ?: throw NoSuchElementException("해당 고객을 찾을 수 없습니다 : $customerPhone")

    fun getPointHistories(phone: String): List<PointHistory> {
        return pointHistoryRepository.findPointHistoriesByCustomer(getCustomerByPhone(phone))
    }
}