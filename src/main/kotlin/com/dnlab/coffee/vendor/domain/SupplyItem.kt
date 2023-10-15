package com.dnlab.coffee.vendor.domain

import com.dnlab.coffee.global.domain.BaseTimeEntity
import com.dnlab.coffee.menu.domain.Ingredient
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class SupplyItem(
    @ManyToOne(fetch = FetchType.LAZY)
    val ingredient: Ingredient,
    @ManyToOne(fetch = FetchType.LAZY)
    val supply: Supply,
    @Column(nullable = false)
    val amount: Double,
    @Column(nullable = false)
    val price: Int
) : BaseTimeEntity()