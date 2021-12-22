package com.schwarz.kokain.di.observer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.KDiFactory
import com.schwarz.kokain.api.internal.BeanScope
import kotlin.reflect.KClass

class BeanLifecycleCurator(private val factory: KDiFactory) : LifecycleObserver {

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

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
    }
}
