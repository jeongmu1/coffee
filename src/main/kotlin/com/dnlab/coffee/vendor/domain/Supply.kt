package com.dnlab.coffee.vendor.domain

import com.dnlab.coffee.global.domain.BaseTimeEntity
import com.dnlab.coffee.vendor.dto.SupplyInfo
import com.dnlab.coffee.vendor.dto.SupplyInfoDetail
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDate

@Entity
class Supply(
    @Column(nullable = false)
    val deliveryDate: LocalDate,
    var actualDeliveryDate: LocalDate?,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val vendor: Vendor,
) : BaseTimeEntity() {
    @OneToMany(mappedBy = "supply", fetch = FetchType.LAZY)
    private val _supplyItems: MutableList<SupplyItem> = mutableListOf()
    val supplyItems: List<SupplyItem>
        get() = _supplyItems

    fun toSupplyInfo(): SupplyInfo =
        SupplyInfo(
            supplyId = this.id,
            vendor = this.vendor.name,
            deliveryDate = this.deliveryDate,
            actualDeliveryDate = this.actualDeliveryDate,
            createdAt = this.createdAt.toLocalDate(),
            updatedAt = this.updatedAt.toLocalDate()
        )

    fun toSupplyInfoDetail(): SupplyInfoDetail =
        SupplyInfoDetail(
            vendor = this.vendor.name,
            deliveryDate = this.deliveryDate,
            actualDeliveryDate = this.actualDeliveryDate,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            items = this.supplyItems.map { it.toSupplyItemInfo() }
        )
}