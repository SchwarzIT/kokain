package com.schwarz.kokaindi

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import kotlin.reflect.KClass

class Kokain(diFactory: KDiFactory) {

    private val mDiFactory: KDiFactory = diFactory

    private val mSingletonMap: MutableMap<KClass<*>, Any> = HashMap()


    fun doInject(kClass: KClass<*>): Lazy<*> {
        return createLazy(kClass)
    }

    private fun createLazy(kClass: KClass<*>): Lazy<*> {
        return lazy {
            return@lazy if (mSingletonMap.containsKey(kClass)) {
                mSingletonMap[kClass]
            } else {
                mDiFactory.createLazy(kClass)
            }
        }
    }

    fun close() {


    }

    fun refreshActivityContext(lifecycleOwner: LifecycleOwner) {

        lifecycleOwner.lifecycle.addObserver()
    }

    companion object {
        fun create(diFactory: KDiFactory): Kokain {
            return Kokain(diFactory)
        }
    }

//    private inline val mInstances : HashMap<KClass<*>, Any> = HashMap()
//
//    @JvmOverloads
//    inline fun <reified T> inject(
//            qualifier: String? = null,
//            noinline parameters: String? = null
//    ): Lazy<T> = mInstances.get(T::class) as Lazy<T>
}