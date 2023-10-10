package com.dnlab.coffee.user.service

import com.dnlab.coffee.user.domain.Admin
import com.dnlab.coffee.user.domain.Authority
import com.dnlab.coffee.user.domain.Role
import com.dnlab.coffee.user.dto.AdminRegistrationForm
import com.dnlab.coffee.user.repository.AdminRepository
import com.dnlab.coffee.user.repository.AuthorityRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminService(
    private val adminRepository: AdminRepository,
    private val authorityRepository: AuthorityRepository,
    private val encoder: PasswordEncoder
) {

    @Transactional
    fun processRegistration(form: AdminRegistrationForm) {
        require(!adminRepository.existsAdminByUsername(form.username)) {
            throw IllegalArgumentException("아이디가 중복되었습니다.")
        }
        val admin = adminRepository.save(
            Admin(
                username = form.username,
                password = encoder.encode(form.password)
            )
        )
        authorityRepository.save(Authority(admin = admin, role = Role.ROLE_ADMIN))
    }
}