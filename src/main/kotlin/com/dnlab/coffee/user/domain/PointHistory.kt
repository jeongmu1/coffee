package com.dnlab.coffee.user.domain

import com.dnlab.coffee.global.domain.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class PointHistory(
    @Column(nullable = false)
    val content: String,

    @Column(nullable = false)
    val changes: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    val customer: Customer
) : BaseTimeEntity()