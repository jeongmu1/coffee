package com.dnlab.coffee.global.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LoggerDelegate : ReadOnlyProperty<Any?, Logger> {

    companion object {
        private fun <T> createLogger(clazz: Class<T>): Logger =
            LoggerFactory.getLogger(clazz)
    }

    private var logger: Logger? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): Logger =
        logger ?: createLogger(thisRef!!.javaClass)
}
