package com.dnlab.coffee.vendor.service

import com.dnlab.coffee.vendor.domain.Supply
import com.dnlab.coffee.vendor.dto.ActualDeliveryDateForm
import com.dnlab.coffee.vendor.dto.SupplyInfo
import com.dnlab.coffee.vendor.repository.SupplyItemRepository
import com.dnlab.coffee.vendor.repository.SupplyRepository
import com.dnlab.coffee.vendor.repository.VendorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SupplyService(
    private val supplyRepository: SupplyRepository,
    private val supplyItemRepository: SupplyItemRepository,
    private val vendorRepository: VendorRepository
) {

    fun getSupplyInfoList(): List<SupplyInfo> =
        supplyRepository.findAll()
            .map { it.toSupplyInfo() }

    @Transactional
    fun inputActualDeliveryDate(form: ActualDeliveryDateForm) {
        val supply = supplyRepository.findSupplyById(form.supplyId)
            ?: throw NoSuchElementException("해당 공급을 찾을 수 없습니다.")
        supply.actualDeliveryDate = form.actualDeliveryDate
    }

    private fun Supply.toSupplyInfo(): SupplyInfo {
        return SupplyInfo(
            supplyId = this.id,
            vendor = this.vendor.name,
            deliveryDate = this.deliveryDate,
            actualDeliveryDate = this.actualDeliveryDate,
            createdAt = this.createdAt.toLocalDate(),
            updatedAt = this.updatedAt.toLocalDate()
        )
    }
}