package com.schwarz.kokaindi.observer

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.KDiFactory
import com.schwarz.kokaindi.scope.BeanScope
import com.schwarz.kokaindi.scope.DefaultScope
import com.schwarz.kokaindi.scope.KScope
import com.schwarz.kokaindi.scope.SingletonScope
import java.lang.RuntimeException
import kotlin.reflect.KClass


class BeanLifecycleCurator(factory: KDiFactory) : LifecycleObserver{

    private val factory = factory

    private val scopes : Map<EBean.Scope, KScope> = mapOf( Pair(EBean.Scope.Default, DefaultScope()), Pair(EBean.Scope.Singleton, SingletonScope()))


    fun <V : Any> getInstance(clazz: KClass<*>): V? {

        for (scope in scopes.values) {
            if(scope.hasInstance(clazz)){
                return scope.getInstance<V>(clazz)!!
            }
        }

        var newInstance = factory.createInstance(clazz) as V?

        newInstance?.let {
            scopes[(it as BeanScope).scope]?.registerInstance(clazz, it)
        }

        return newInstance
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
