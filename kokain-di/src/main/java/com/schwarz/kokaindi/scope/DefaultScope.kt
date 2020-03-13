package com.schwarz.kokaindi.scope

import kotlin.reflect.KClass

class DefaultScope : KScope{

    override fun registerInstance(kClass: KClass<*>, instance: Any) {

    }

    override fun hasInstance(kClass: KClass<*>) : Boolean{
        return false
    }

    override fun <V : Any> getInstance(kClass: KClass<*>): V? {
       return null
    }

}
