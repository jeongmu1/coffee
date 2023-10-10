package com.dnlab.coffee.order

import com.dnlab.coffee.global.domain.BaseEntity
import com.dnlab.coffee.user.domain.User
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class CustomerOrder(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val user: User,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val paymentType: PaymentType
) : BaseEntity() {
    @OneToMany(mappedBy = "customerOrder", fetch = FetchType.LAZY)
    private val _orderMenus: MutableList<OrderMenu> = mutableListOf()
    val orderMenus: List<OrderMenu>
        get() = _orderMenus

    @CreatedDate
    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        private set
}