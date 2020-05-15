package com.schwarz.kokain.di

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import com.schwarz.kokain.di.observer.RefreshingReadonlyProperty


inline fun <reified T : ComponentActivity, reified V : Any> ComponentActivity.inject(
): RefreshingReadonlyProperty<T, V> {
    KokainInstance.mInstance!!.refreshActivityContext(this)
   return RefreshingReadonlyProperty<T, V> { t:T, desc ->
       return@RefreshingReadonlyProperty get()
    }
}

inline fun <reified T : Fragment, reified V:Any> Fragment.inject(
): RefreshingReadonlyProperty<T, V> {
    KokainInstance.mInstance!!.refreshActivityContext(activity)
    return RefreshingReadonlyProperty<T, V>{ t:T, desc ->
        return@RefreshingReadonlyProperty get()
    }
}

inline fun <reified T : Any, reified V:Any> Any.inject(
): RefreshingReadonlyProperty<T, V> {
    return RefreshingReadonlyProperty<T, V> { t:T, desc ->
        return@RefreshingReadonlyProperty get()
    }
}

inline fun <reified T : Any> Any.get(
): T {
    return KokainInstance.mInstance!!.create(this , T::class)
}

inline fun <reified T : Any> Fragment.get(
): T {
    KokainInstance.mInstance!!.refreshActivityContext(activity)
    return KokainInstance.mInstance!!.create(this , T::class)
}

inline fun <reified T : Any> ComponentActivity.get(
): T {
    KokainInstance.mInstance!!.refreshActivityContext(this)
    return KokainInstance.mInstance!!.create(this , T::class)
}

inline fun Any.context(
): ActivityContextGuard {
    return KokainInstance.mInstance!!.mGuard
}



