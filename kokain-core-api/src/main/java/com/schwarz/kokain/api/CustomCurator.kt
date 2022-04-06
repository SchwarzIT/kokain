package com.schwarz.kokain.api

import kotlin.reflect.KClass

interface CustomCurator {

    fun <V : Any> getInstance(clazz: KClass<*>): V?
}
