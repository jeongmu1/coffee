package com.dnlab.coffee.vendor.domain

import com.dnlab.coffee.global.domain.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class Supply(
    @Column(nullable = false)
    val deliveryDate: LocalDate,
    var actualDeliveryDate: LocalDate?,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val vendor: Vendor,
): BaseTimeEntity()