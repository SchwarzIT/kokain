package com.schwarz.kokain.coredi

import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.KDiFactory
import com.schwarz.kokain.coredi.observer.BeanLifecycleCurator
import java.lang.RuntimeException
import kotlin.reflect.KClass

open class KokainCore(diFactory: KDiFactory, customCurator: CustomCurator? = null) {

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

    protected open fun onBeanResolved(thisRef: Any?, bean: Any?) {
    }

    fun close() {
    }

    companion object {
        fun create(diFactory: KDiFactory, customCurator: CustomCurator? = null): KokainCore {
            return KokainCore(diFactory, customCurator)
        }
    }
}
