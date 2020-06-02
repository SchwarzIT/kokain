package com.schwarz.kokain.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.di.observer.ActivityRefered
import com.schwarz.kokain.di.scope.BeanScope
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class ActivityContextGuard(applicationContext: Application) : LifecycleObserver {

    private var appContext = applicationContext

    private var currentRef: WeakReference<ComponentActivity>? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Context {
        return if (isReferedByActivity(thisRef)) currentRef?.get() ?: appContext else appContext
    }

    private fun isReferedByActivity(thisRef: Any?): Boolean {
        if (thisRef is BeanScope && thisRef.scope == EBean.Scope.Singleton) {
            return false
        }
        if (thisRef is ActivityRefered) {
            return thisRef.activityRef?.equals(currentRef?.get()?.toString()) ?: false
        }
        if (thisRef is Fragment) {
            return thisRef.activity?.equals(currentRef?.get()?.toString()) ?: false
        }
        if (thisRef is Activity) {
            return thisRef?.equals(currentRef?.get()?.toString())
        }
        if(thisRef is View){
            return thisRef?.context?.equals(currentRef?.get()?.toString()) ?: false
        }
        return false
    }

    fun updateRefererer(thisRef: Any?, bean: Any?) {
        if (bean is ActivityRefered) {
            if (thisRef is ActivityRefered) {
                bean.activityRef = thisRef.activityRef
            } else if (thisRef is Fragment) {
                bean.activityRef = thisRef.activity?.toString() ?: null
            }
            if (thisRef is Activity) {
                bean.activityRef = thisRef?.toString()
            }
            if(thisRef is View){
                bean.activityRef = thisRef?.context?.toString()
            }
        }


    }

    fun onNewContext(activity: ComponentActivity) {
        if (currentRef?.get() != activity) {
            currentRef?.clear()
            currentRef = WeakReference(activity)
        }
    }
}
