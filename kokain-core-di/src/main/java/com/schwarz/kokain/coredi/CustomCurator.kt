package com.schwarz.kokain.coredi

import kotlin.reflect.KClass

interface CustomCurator {

    fun <V : Any> getInstance(clazz: KClass<*>): V?
}
