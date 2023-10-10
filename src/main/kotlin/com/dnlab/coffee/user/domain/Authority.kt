package com.dnlab.coffee.user.domain

import com.dnlab.coffee.global.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Authority(
    @Enumerated(EnumType.STRING)
    val role: Role,
    @ManyToOne(fetch = FetchType.EAGER)
    val user: User
) : BaseEntity()