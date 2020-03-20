package com.schwarz.kokaindi

import android.app.Application
import androidx.activity.ComponentActivity
import com.schwarz.kokaindi.observer.BeanLifecycleCurator
import kotlin.reflect.KClass

class Kokain(diFactory: KDiFactory, app: Application) {

    private val beanLifecycleCurator = BeanLifecycleCurator(diFactory)

    private val app: Application = app

    var mGuard = ActivityContextGuard(app)


    fun <V : Any> create(thisRef: Any, kClass: KClass<*>): V {

        var bean = beanLifecycleCurator.getInstance(kClass) as V
        mGuard?.updateRefererer(thisRef, bean)

        return bean
    }

    fun close() {


    }

    fun refreshActivityContext(activity: ComponentActivity?) {
        activity?.let {
            mGuard.onNewContext(it)
        }
    }

    companion object {
        fun create(diFactory: KDiFactory, app: Application): Kokain {
            return Kokain(diFactory, app)
        }
    }
}