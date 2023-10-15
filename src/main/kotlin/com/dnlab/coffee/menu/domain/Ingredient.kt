package com.dnlab.coffee.menu.domain

import com.dnlab.coffee.global.domain.BaseEntity
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
): BaseEntity()