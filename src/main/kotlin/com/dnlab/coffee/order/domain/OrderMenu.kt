package com.dnlab.coffee.order.domain

import com.dnlab.coffee.global.domain.BaseEntity
import com.dnlab.coffee.menu.domain.Menu
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class OrderMenu(
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    val customerOrder: CustomerOrder,
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    val menu: Menu,
    @Column(nullable = false)
    val quantity: Int,
    @Column(nullable = false)
    val price: Int
): BaseEntity()