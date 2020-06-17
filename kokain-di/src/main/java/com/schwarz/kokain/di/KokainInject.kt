package com.schwarz.kokain.di

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import com.schwarz.kokain.di.observer.RefreshingReadonlyProperty
import kotlin.reflect.KClass


inline fun <reified T : ComponentActivity, reified V : Any> ComponentActivity.inject(impl : KClass<V>? = null): RefreshingReadonlyProperty<T, V> {
    KokainInstance.mInstance!!.refreshActivityContext(this)
   return RefreshingReadonlyProperty<T, V> { t:T, desc ->
       return@RefreshingReadonlyProperty get(impl)
    }
}

inline fun <reified T : Fragment, reified V:Any> Fragment.inject(impl : KClass<V>? = null
): RefreshingReadonlyProperty<T, V> {
    KokainInstance.mInstance!!.refreshActivityContext(activity)
    return RefreshingReadonlyProperty<T, V>{ t:T, desc ->
        return@RefreshingReadonlyProperty get(impl)
    }
}

inline fun <reified T : Any, reified V:Any> Any.inject(impl : KClass<V>? = null
): RefreshingReadonlyProperty<T, V> {
    return RefreshingReadonlyProperty<T, V> { t:T, desc ->
        return@RefreshingReadonlyProperty get(impl)
    }
}

inline fun <reified T : Any> Any.get(impl : KClass<T>? = null
): T {
    return KokainInstance.mInstance!!.create(this , impl ?: T::class)
}

inline fun <reified T : Any> Fragment.get(impl : KClass<T>? = null
): T {
    KokainInstance.mInstance!!.refreshActivityContext(activity)
    return KokainInstance.mInstance!!.create(this , impl ?: T::class)
}

inline fun <reified T : Any> ComponentActivity.get(impl : KClass<T>? = null
): T {
    KokainInstance.mInstance!!.refreshActivityContext(this)
    return KokainInstance.mInstance!!.create(this , impl ?: T::class)
}

inline fun Any.context(
): ActivityContextGuard {
    return KokainInstance.mInstance!!.mGuard
}

inline fun <reified T : Any, reified V:Any> Any.systemService(
): RefreshingReadonlyProperty<T, V?> {
    return RefreshingReadonlyProperty<T, V?> { t:T, desc ->
        return@RefreshingReadonlyProperty KokainInstance.mInstance?.mGuard?.getValue(t, desc)?.getSystemService(V::class.java)
    }
}



