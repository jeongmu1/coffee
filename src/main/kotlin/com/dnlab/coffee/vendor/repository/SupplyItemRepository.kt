package com.dnlab.coffee.vendor.repository

import com.dnlab.coffee.vendor.domain.SupplyItem
import org.springframework.data.jpa.repository.JpaRepository

interface SupplyItemRepository : JpaRepository<SupplyItem, Long>