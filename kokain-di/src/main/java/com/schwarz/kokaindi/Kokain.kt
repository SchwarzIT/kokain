package com.schwarz.kokaindi

import android.app.Application
import androidx.activity.ComponentActivity
import kotlin.reflect.KClass

class Kokain(diFactory: KDiFactory, app: Application) {

    private val mDiFactory: KDiFactory = diFactory

    private val app: Application = app

    private val mSingletonMap: MutableMap<KClass<*>, Any> = HashMap()

    var mGuard = ActivityContextGuard(app)


    fun <V : Any> create(thisRef: Any, kClass: KClass<*>): V {
        var bean = if (mSingletonMap.containsKey(kClass)) {
            mSingletonMap[kClass] as V
        } else {
            mDiFactory.createInstance(kClass) as V
        }
        mGuard?.updateRefererer(thisRef, bean)

        return bean
    }

    fun close() {


    }

    fun refreshActivityContext(activity: ComponentActivity) {
        mGuard.onNewContext(activity)
    }

    companion object {
        fun create(diFactory: KDiFactory, app: Application): Kokain {
            return Kokain(diFactory, app)
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