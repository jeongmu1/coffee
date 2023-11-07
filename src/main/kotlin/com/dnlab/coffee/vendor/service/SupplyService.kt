package com.dnlab.coffee.vendor.service

import com.dnlab.coffee.menu.service.IngredientService
import com.dnlab.coffee.vendor.domain.Supply
import com.dnlab.coffee.vendor.dto.*
import com.dnlab.coffee.vendor.repository.SupplyItemRepository
import com.dnlab.coffee.vendor.repository.SupplyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SupplyService(
    private val supplyRepository: SupplyRepository,
    private val supplyItemRepository: SupplyItemRepository,
    private val ingredientService: IngredientService,
    private val vendorService: VendorService
) {

    @Transactional(readOnly = true)
    fun getSupplyInfoList(): List<SupplyInfo> =
        supplyRepository.findAll()
            .map { it.toSupplyInfo() }

    @Transactional(readOnly = true)
    fun getSupplyDetail(supplyId: Long): SupplyInfoDetail = getSupply(supplyId).toSupplyInfoDetail()

    @Transactional
    fun updateActualDeliveryDate(form: ActualDeliveryDateForm) {
        val supply = getSupply(form.supplyId)
        supply.actualDeliveryDate = form.actualDeliveryDate
    }

    @Transactional
    fun addSupply(form: SupplyForm) {
        val vendor = vendorService.getVendor(form.vendorId)
        val supply = supplyRepository.save(form.toEntity(vendor))

        supplyItemRepository.saveAll(form.supplyItems.map {
            val ingredient = ingredientService.getIngredientById(it.ingredientId)
            ingredient.addStock(it.amount)
            it.toEntity(supply, ingredient)
        })
    }

    private fun getSupply(supplyId: Long): Supply =
        supplyRepository.findSupplyById(supplyId) ?: throw NoSuchElementException("해당 공급을 찾을 수 없습니다 : $supplyId")
}