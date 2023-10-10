package com.dnlab.coffee.user.repository

import com.dnlab.coffee.user.domain.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long> {
    fun existsAdminByUsername(username: String): Boolean
    fun findAdminByUsername(username: String): Admin
}