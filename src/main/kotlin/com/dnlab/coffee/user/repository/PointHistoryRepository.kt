package com.dnlab.coffee.user.repository

import com.dnlab.coffee.user.domain.Customer
import com.dnlab.coffee.user.domain.PointHistory
import org.springframework.data.jpa.repository.JpaRepository

interface PointHistoryRepository: JpaRepository<PointHistory, Long> {
    fun findPointHistoryById(id: Long): PointHistory?
    fun findPointHistoriesByCustomer(customer: Customer): List<PointHistory>
}