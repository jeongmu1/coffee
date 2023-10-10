package com.dnlab.coffee.security

import com.dnlab.coffee.user.domain.Admin
import com.dnlab.coffee.user.domain.Authority
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    private val user: Admin,
    private val authorities: Set<Authority>
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        authorities.map { GrantedAuthority { it.role.toString() } }.toMutableSet()

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = user.enabled
}