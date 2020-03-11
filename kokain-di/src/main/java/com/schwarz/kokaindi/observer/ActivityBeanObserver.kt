package com.schwarz.kokaindi.observer

import androidx.lifecycle.LifecycleObserver
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import kotlin.reflect.KClass


class ActivityBeanObserver : LifecycleObserver{

    private val mBeans : MutableMap<KClass<*>, Lazy<*>> = HashMap<KClass<*>, Lazy<*>>()


    fun registerBean(clazz: KClass<*> ,bean : Lazy<*>){

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
