package com.dnlab.coffee.vendor.service

import com.dnlab.coffee.menu.repository.IngredientRepository
import com.dnlab.coffee.vendor.domain.Supply
import com.dnlab.coffee.vendor.domain.SupplyItem
import com.dnlab.coffee.vendor.dto.ActualDeliveryDateForm
import com.dnlab.coffee.vendor.dto.SupplyForm
import com.dnlab.coffee.vendor.dto.SupplyInfo
import com.dnlab.coffee.vendor.dto.SupplyItemForm
import com.dnlab.coffee.vendor.repository.SupplyItemRepository
import com.dnlab.coffee.vendor.repository.SupplyRepository
import com.dnlab.coffee.vendor.repository.VendorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SupplyService(
    private val supplyRepository: SupplyRepository,
    private val supplyItemRepository: SupplyItemRepository,
    private val vendorRepository: VendorRepository,
    private val ingredientRepository: IngredientRepository
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

    @Transactional
    fun addSupply(form: SupplyForm) {
        val vendor = vendorRepository.findVendorById(form.vendorId) ?: throw NoSuchElementException(
            "해당 공급업체를 찾을 수 없습니다."
        )
        val supply = supplyRepository.save(
            Supply(
                deliveryDate = form.deliveryDate,
                actualDeliveryDate = form.actualDeliveryDate,
                vendor = vendor
            )
        )

        supplyItemRepository.saveAll(form.supplyItems.map { it.toEntity(supply) })
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

    private fun SupplyItemForm.toEntity(supply: Supply): SupplyItem {
        val ingredient = ingredientRepository.findIngredientById(this.ingredientId)
            ?: throw NoSuchElementException("해당 재료를 찾을 수 없습니다.")
        ingredient.stock += this.amount
        return SupplyItem(
            supply = supply,
            ingredient = ingredient,
            amount = this.amount,
            price = this.price
        )
    }
}