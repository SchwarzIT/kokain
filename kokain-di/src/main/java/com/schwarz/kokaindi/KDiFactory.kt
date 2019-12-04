package com.schwarz.kokaindi

import kotlin.reflect.KClass

interface KDiFactory {

    fun createLazy(kClass: KClass<*>) : Any
}