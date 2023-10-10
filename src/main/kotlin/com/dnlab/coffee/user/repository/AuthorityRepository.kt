package com.dnlab.coffee.user.repository

import com.dnlab.coffee.user.domain.Admin
import com.dnlab.coffee.user.domain.Authority
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository : JpaRepository<Authority, Long> {
    fun findAuthoritiesByAdmin(admin: Admin): Set<Authority>
}