package com.schwarz.kokain.corelib.scope

import java.util.Collections
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.HashMap
import kotlin.reflect.KClass

class SingletonScope : KScope, Lockable {

    private val lock: Lock = ReentrantLock()
    private val mInstances = Collections.synchronizedMap(HashMap<KClass<*>, Any>())

    override fun registerInstance(kClass: KClass<*>, instance: Any) {
        mInstances[kClass] = instance
    }

    override fun hasInstance(kClass: KClass<*>): Boolean {
        return mInstances.containsKey(kClass)
    }

    override fun lock() {
        lock.lock()
    }

    override fun unlock() {
        lock.unlock()
    }

    override fun <V : Any> getInstance(kClass: KClass<*>): V? {
        return mInstances[kClass] as V?
    }
}
