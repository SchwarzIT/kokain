package com.schwarz.kokaindi.scope

import kotlin.reflect.KClass

class SingletonScope : KScope{

    private val mInstances = HashMap<KClass<*>, Any>()

    override fun registerInstance(kClass: KClass<*>, instance: Any) {
        mInstances[kClass] = instance
    }

    override fun hasInstance(kClass: KClass<*>) : Boolean{
        return mInstances.containsKey(kClass)
    }

    override fun <V : Any> getInstance(kClass: KClass<*>): V? {
       return mInstances[kClass] as V?
    }

}
