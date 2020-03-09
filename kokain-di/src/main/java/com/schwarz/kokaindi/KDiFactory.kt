package com.schwarz.kokaindi

import kotlin.reflect.KClass

interface KDiFactory {

    fun createInstance(kClass: KClass<*>) : Any
}