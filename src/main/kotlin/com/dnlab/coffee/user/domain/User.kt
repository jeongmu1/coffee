package com.dnlab.coffee.user.domain

import com.dnlab.coffee.global.domain.BaseTimeEntity
import com.dnlab.coffee.order.CustomerOrder
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany


@Entity
class User(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false, unique = true)
    val phone: String,
    @Column(nullable = false)
    val address: String,
    @Column(nullable = false)
    val enabled: Boolean = true
) : BaseTimeEntity() {
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private val _customerOrders: MutableList<CustomerOrder> = mutableListOf()
    val customerOrders: List<CustomerOrder>
        get() = _customerOrders

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private val _authorities: MutableSet<Authority> = mutableSetOf()
    val authorities: Set<Authority>
        get() = _authorities
}