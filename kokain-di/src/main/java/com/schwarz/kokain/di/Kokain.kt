package com.schwarz.kokain.di

import android.app.Application
import androidx.activity.ComponentActivity
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.KDiFactory
import com.schwarz.kokain.di.observer.BeanLifecycleCurator
import java.lang.RuntimeException
import kotlin.reflect.KClass

class Kokain(diFactory: KDiFactory, app: Application, customCurator: CustomCurator? = null) {

    private val beanLifecycleCurator = BeanLifecycleCurator(diFactory)

    private val customCurator = customCurator

    private val app: Application = app

    var mGuard = ActivityContextGuard(app)


    fun <V : Any> create(thisRef: Any, clazz: KClass<*>): V {

        var bean = beanLifecycleCurator.getInstance(clazz) as V?
        if(bean == null){
            bean = customCurator?.getInstance(clazz)
        }

        mGuard?.updateRefererer(thisRef, bean)

        if(bean == null){
            throw RuntimeException("not able to get instance of $clazz make sure object annotated with ${EBean::class} or CustomCurator registered")
        }

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
        fun create(diFactory: KDiFactory, app: Application, customCurator: CustomCurator? = null): Kokain {
            return Kokain(diFactory, app, customCurator)
        }
    }
}