package com.schwarz.kokain.coredi.observer

import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.KDiFactory
import com.schwarz.kokain.api.internal.BeanScope
import com.schwarz.kokain.coredi.scope.DefaultScope
import com.schwarz.kokain.coredi.scope.KScope
import com.schwarz.kokain.coredi.scope.Lockable
import com.schwarz.kokain.coredi.scope.SingletonScope
import kotlin.reflect.KClass

class BeanLifecycleCurator(private val factory: KDiFactory) {

    private val scopes: Map<EBean.Scope, KScope> = mapOf(Pair(EBean.Scope.Default, DefaultScope()), Pair(EBean.Scope.Singleton, SingletonScope()))

    fun <V : Any> getInstance(clazz: KClass<*>): V? {
        try {
            for (scope in scopes.values) {
                (scope as? Lockable)?.lock()
                if (scope.hasInstance(clazz)) {
                    return scope.getInstance<V>(clazz)!!
                }
            }

            val newInstance = factory.createInstance(clazz) as V?

            newInstance?.let {
                scopes[(it as BeanScope).scope]?.registerInstance(clazz, it)
            }

            return newInstance
        } finally {
            for (scope in scopes.values) {
                (scope as? Lockable)?.unlock()
            }
        }
    }
}
