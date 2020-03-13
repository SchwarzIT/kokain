package com.schwarz.kokaindi.observer

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.schwarz.kokain.api.EBean
import com.schwarz.kokaindi.KDiFactory
import com.schwarz.kokaindi.scope.BeanScope
import com.schwarz.kokaindi.scope.DefaultScope
import com.schwarz.kokaindi.scope.KScope
import com.schwarz.kokaindi.scope.SingletonScope
import kotlin.reflect.KClass


class BeanLifecycleCurator(factory: KDiFactory) : LifecycleObserver{

    private val factory = factory

    private val scopes : Map<EBean.Scope, KScope> = mapOf( Pair(EBean.Scope.Default, DefaultScope()), Pair(EBean.Scope.Singleton, SingletonScope()))


    fun <V : Any> getInstance(kClass: KClass<*>): V {

        for (scope in scopes.values) {
            if(scope.hasInstance(kClass)){
                return scope.getInstance<V>(kClass)!!
            }
        }

        var newInstance = factory.createInstance(kClass) as V
        scopes[(newInstance as BeanScope).scope]?.registerInstance(kClass, newInstance)
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
