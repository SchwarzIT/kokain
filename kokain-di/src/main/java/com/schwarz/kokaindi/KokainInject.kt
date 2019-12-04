package com.schwarz.kokaindi

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import com.schwarz.kokaindi.observer.ContextBean
import java.lang.RuntimeException


inline fun <reified T> ComponentActivity.inject(
): Lazy<T> {
    KokainInstance.mInstance!!.refreshActivityContext(this)
    return KokainInstance.mInstance!!.doInject(T::class) as Lazy<T>
}

inline fun <reified T> Fragment.inject(
): Lazy<T> {
    KokainInstance.mInstance!!.refreshActivityContext(activity!!)
    return KokainInstance.mInstance!!.doInject(T::class) as Lazy<T>
}

inline fun <reified T> inject(
): Lazy<T> {
    return KokainInstance.mInstance!!.doInject(T::class) as Lazy<T>
}

inline fun Any.context(
): Lazy<Context> {
    return lazy {
        if (this is ContextBean) {
            context
        }
        throw RuntimeException()
    }
}



