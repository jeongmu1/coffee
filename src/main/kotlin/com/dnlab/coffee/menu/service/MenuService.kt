package com.dnlab.coffee.menu.service

import com.dnlab.coffee.global.util.LoggerDelegate
import com.dnlab.coffee.menu.domain.Menu
import com.dnlab.coffee.menu.dto.MenuDisplay
import com.dnlab.coffee.menu.repository.MenuRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MenuService(
    private val menuRepository: MenuRepository
) {
    private val logger by LoggerDelegate()

    @Transactional(readOnly = true)
    fun getMenus(): List<MenuDisplay> {
        val menus = menuRepository.findAll().map { it.toDisplay() }
        logger.info("menus : $menus")
        return menus
    }

    @Transactional(readOnly = true)
    fun getMenus(name: String): List<MenuDisplay> {
        val menus = menuRepository.findMenusByNameContains(name).map { it.toDisplay() }
        logger.info("menus : $menus")
        return menus
    }

    @Transactional(readOnly = true)
    fun getMenu(id: Long): MenuDisplay =
        menuRepository.findMenuById(id)?.toDisplay()
            ?: throw NoSuchElementException("해당 메뉴는 존재하지 않습니다.")

    private fun Menu.toDisplay(): MenuDisplay =
        MenuDisplay(
            id = this.id,
            name = this.name,
            price = this.price,
            productType = this.productType.title,
            soldOuted = this.isSoldOuted()
        )
}