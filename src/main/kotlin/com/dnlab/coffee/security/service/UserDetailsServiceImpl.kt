package com.dnlab.coffee.security.service

import com.dnlab.coffee.security.UserDetailsImpl
import com.dnlab.coffee.user.repository.AdminRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(
    private val adminRepository: AdminRepository
): UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = username?.let { adminRepository.findAdminByUsername(username) }
            ?: throw UsernameNotFoundException("User not found.")
        return UserDetailsImpl(user = user, authorities = user.authorities)
    }
}