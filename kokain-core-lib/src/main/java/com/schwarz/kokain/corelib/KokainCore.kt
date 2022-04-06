package com.schwarz.kokain.corelib

import com.schwarz.kokain.api.CustomCurator
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.KDiFactory
import com.schwarz.kokain.corelib.observer.BeanLifecycleCurator
import java.lang.RuntimeException
import kotlin.reflect.KClass

abstract class KokainCore(diFactory: KDiFactory, customCurator: CustomCurator? = null) {

    private val beanLifecycleCurator = BeanLifecycleCurator(diFactory)

    private val customCurator = customCurator

    fun <V : Any> create(thisRef: Any, clazz: KClass<*>): V {

        var bean = beanLifecycleCurator.getInstance(clazz) as V?
        if (bean == null) {
            bean = customCurator?.getInstance(clazz)
        }

        onBeanResolved(thisRef, bean)

        if (bean == null) {
            throw RuntimeException("not able to get instance of $clazz make sure object annotated with ${EBean::class} or CustomCurator registered")
        }

        return bean
    }

    abstract fun onBeanResolved(thisRef: Any?, bean: Any?)

    fun close() {
    }
}
