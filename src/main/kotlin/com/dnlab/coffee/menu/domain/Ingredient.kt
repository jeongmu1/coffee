package com.dnlab.coffee.menu.domain

import com.dnlab.coffee.global.domain.BaseEntity
import com.dnlab.coffee.vendor.domain.Supply
import jakarta.persistence.*

@Entity
class Ingredient(
    @Column(nullable = false, unique = true)
    val name: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val measurementUnit: MeasurementUnit,
    @Column(nullable = false)
    var stock: Double = 0.0
): BaseEntity() {
    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
    private val _supplies: MutableList<Supply> = mutableListOf()
    val supplies: List<Supply>
        get() = _supplies
}