package com.dnlab.coffee.menu.domain

import com.dnlab.coffee.global.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Recipe(
    @ManyToOne(fetch = FetchType.LAZY)
    val menu: Menu,
    @ManyToOne(fetch = FetchType.LAZY)
    val ingredient: Ingredient,
    @Column(nullable = false)
    val quantity: Double
): BaseEntity()