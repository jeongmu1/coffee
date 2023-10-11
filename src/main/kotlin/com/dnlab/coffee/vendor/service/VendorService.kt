package com.dnlab.coffee.vendor.service

import com.dnlab.coffee.vendor.domain.Vendor
import com.dnlab.coffee.vendor.dto.VendorForm
import com.dnlab.coffee.vendor.repository.VendorRepository
import org.springframework.stereotype.Service

@Service
class VendorService(
    private val vendorRepository: VendorRepository
) {
    fun getVendorList(): List<Vendor> {
        return vendorRepository.findAll()
    }

    fun saveNewVendor(vendorForm: VendorForm) {
        vendorRepository.save(Vendor(name = vendorForm.name, address = vendorForm.address))
    }
}