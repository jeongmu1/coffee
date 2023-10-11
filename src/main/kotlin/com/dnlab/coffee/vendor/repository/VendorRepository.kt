package com.dnlab.coffee.vendor.repository

import com.dnlab.coffee.vendor.domain.Vendor
import org.springframework.data.jpa.repository.JpaRepository

interface VendorRepository : JpaRepository<Vendor, Long>