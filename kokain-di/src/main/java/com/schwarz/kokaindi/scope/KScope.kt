package com.schwarz.kokaindi.scope

import kotlin.reflect.KClass

interface KScope {

    fun <V : Any> getInstance(kClass: KClass<*>) : V?

    fun registerInstance(kClass: KClass<*>, instance : Any)

    fun hasInstance(kClass: KClass<*>) : Boolean

}