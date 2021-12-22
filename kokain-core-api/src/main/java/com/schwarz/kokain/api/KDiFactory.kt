package com.schwarz.kokain.api

import kotlin.reflect.KClass

interface KDiFactory {

    fun createInstance(clazz: KClass<*>): Any?
}
