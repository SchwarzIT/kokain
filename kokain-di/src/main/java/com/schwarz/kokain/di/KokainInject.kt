package com.schwarz.kokain.di

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import com.schwarz.kokain.coredi.KokainInstance
import com.schwarz.kokain.coredi.get
import com.schwarz.kokain.coredi.inject
import com.schwarz.kokain.coredi.observer.RefreshingReadonlyProperty
import kotlin.reflect.KClass

inline fun <reified T : ComponentActivity, reified V : Any> ComponentActivity.inject(impl: KClass<V>? = null): RefreshingReadonlyProperty<T, V> {
    (KokainInstance.mInstance!! as Kokain).refreshActivityContext(this)
    return RefreshingReadonlyProperty<T, V> { t: T, desc ->
        return@RefreshingReadonlyProperty get(impl)
    }
}

// This section just wraps the core api to prevent breaking changes
inline fun <reified T : Any, reified V : Any> Any.inject(
    impl: KClass<V>? = null
) = inject<T, V>(impl)

inline fun <reified T : Any> Any.get(
    impl: KClass<T>? = null
) = get(impl)
// end of section

inline fun <reified T : Fragment, reified V : Any> Fragment.inject(
    impl: KClass<V>? = null
): RefreshingReadonlyProperty<T, V> {
    (KokainInstance.mInstance!! as Kokain).refreshActivityContext(activity)
    return RefreshingReadonlyProperty<T, V> { t: T, desc ->
        return@RefreshingReadonlyProperty get(impl)
    }
}

inline fun <reified T : Any> Fragment.get(
    impl: KClass<T>? = null
): T {
    (KokainInstance.mInstance!! as Kokain).refreshActivityContext(activity)
    return KokainInstance.mInstance!!.create(this, impl ?: T::class)
}

inline fun <reified T : Any> ComponentActivity.get(
    impl: KClass<T>? = null
): T {
    (KokainInstance.mInstance!! as Kokain).refreshActivityContext(this)
    return KokainInstance.mInstance!!.create(this, impl ?: T::class)
}

inline fun Any.context(): ActivityContextGuard {
    return (KokainInstance.mInstance!! as Kokain).mGuard
}

inline fun <reified T : Any, reified V : Any> Any.systemService(): RefreshingReadonlyProperty<T, V?> {
    return RefreshingReadonlyProperty<T, V?> { t: T, desc ->
        return@RefreshingReadonlyProperty (KokainInstance.mInstance!! as Kokain).mGuard?.getValue(t, desc)?.getSystemService(V::class.java)
    }
}
