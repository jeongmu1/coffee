package com.dnlab.coffee.vendor.repository

import com.dnlab.coffee.vendor.domain.Supply
import org.springframework.data.jpa.repository.JpaRepository

interface SupplyRepository : JpaRepository<Supply, Long> {
    fun findSupplyById(id: Long): Supply?
}