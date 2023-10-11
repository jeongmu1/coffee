package com.dnlab.coffee.vendor.service

import com.dnlab.coffee.menu.repository.IngredientRepository
import com.dnlab.coffee.vendor.domain.Supply
import com.dnlab.coffee.vendor.dto.ActualDeliveryDateForm
import com.dnlab.coffee.vendor.dto.SupplyForm
import com.dnlab.coffee.vendor.dto.SupplyInfo
import com.dnlab.coffee.vendor.repository.SupplyRepository
import com.dnlab.coffee.vendor.repository.VendorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SupplyService(
    private val supplyRepository: SupplyRepository,
    private val ingredientRepository: IngredientRepository,
    private val vendorRepository: VendorRepository
) {

    fun getSupplyInfoList(): List<SupplyInfo> {
        return supplyRepository.findAll().map { it.toDto() }
    }

    @Transactional(readOnly = true)
    fun getSupplyInfoList(ingredientName: String): List<SupplyInfo> {
        return ingredientRepository.findIngredientByName(ingredientName)
            ?.let { it.supplies.map { supply -> supply.toDto() } }
            ?: emptyList()
    }

    @Transactional
    fun addSupply(supplyForm: SupplyForm) {
        val ingredient = ingredientRepository.findIngredientByName(supplyForm.ingredientName)
            ?: throw NoSuchElementException("해당 재료는 존재하지 않습니다.")
        val vendor = vendorRepository.findVendorById(supplyForm.vendorId)
            ?: throw NoSuchElementException("해당 공급업체는 존재하지 않습니다.")

        if (supplyForm.actualDeliveryDate != null) {
            ingredient.stock += supplyForm.quantity
        }

        supplyRepository.save(
            Supply(
                price = supplyForm.price,
                quantity = supplyForm.quantity,
                ingredient = ingredient,
                vendor = vendor,
                deliveryDate = supplyForm.deliveryDate,
                actualDeliveryDate = supplyForm.actualDeliveryDate
            )
        )
    }

    @Transactional
    fun inputActualDeliveryDate(form: ActualDeliveryDateForm) {
        supplyRepository.findSupplyById(form.supplyId)
            ?.let {
                it.actualDeliveryDate = form.actualDeliveryDate
                it.ingredient.stock += it.quantity
            }
            ?: throw NoSuchElementException("해당 공급 내역은 존재하지 않습니다.")
    }

    private fun Supply.toDto(): SupplyInfo {
        val ingredient = this.ingredient
        val vendor = this.vendor

        return SupplyInfo(
            ingredientName = ingredient.name,
            measurementUnit = ingredient.measurementUnit.unit,
            price = this.price,
            quantity = this.quantity,
            deliveryDate = this.deliveryDate,
            actualDeliveryDate = this.actualDeliveryDate,
            vendorName = vendor.name,
            supplyId = this.id
        )
    }
}