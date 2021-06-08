package com.schwarz.kokain.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.internal.ActivityRefered
import com.schwarz.kokain.api.internal.BeanScope
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class ActivityContextGuard(applicationContext: Application) : LifecycleObserver {

    private var appContext = applicationContext

    private var activityRefs: HashMap<String, ActivityGuard> = HashMap()

    private class ActivityGuard(activity: ComponentActivity, val map: HashMap<String, ActivityGuard>) : LifecycleObserver {

        private val reference = activity.toString()

        private var currentRef: WeakReference<ComponentActivity> = WeakReference(activity)

        init {
            if(activity.lifecycle.currentState != Lifecycle.State.DESTROYED){
                activity.lifecycle.addObserver(this)
                map[reference] = this
            }else{
                currentRef.clear()
            }
        }

        fun isSame(activity: ComponentActivity) : Boolean{
           return currentRef.get() == activity
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy(){
            currentRef.clear()
            map.remove(reference)?.onDestroy()
        }

        fun getRef(): Context? {
            return currentRef?.get()
        }

    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Context {
        return findReferredGuard(thisRef)?.getRef() ?: appContext
    }

    private fun findReferredGuard(thisRef: Any?): ActivityGuard?{
        if (thisRef is BeanScope && thisRef.scope == EBean.Scope.Singleton) {
            return null
        }

        val ref : String? = when(thisRef){
            is ActivityRefered -> thisRef.activityRef
            is Fragment -> thisRef.activity?.toString()
            is Activity -> thisRef?.toString()
            is View -> thisRef.context?.toString()
            else -> null
        }

        return activityRefs[ref]
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

        if(activityRefs[activity.toString()]?.isSame(activity) == true){
            return
        }
        ActivityGuard(activity, activityRefs)
    }
}
