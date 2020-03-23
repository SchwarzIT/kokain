package com.schwarz.kokaindi

import kotlin.reflect.KClass

interface CustomCurator{

    fun <V : Any> getInstance(clazz: KClass<*>): V?
}