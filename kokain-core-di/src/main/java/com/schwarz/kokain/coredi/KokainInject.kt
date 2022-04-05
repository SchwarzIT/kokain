package com.schwarz.kokain.coredi

import com.schwarz.kokain.coredi.observer.RefreshingReadonlyProperty
import kotlin.reflect.KClass

inline fun <reified T : Any, reified V : Any> Any.inject(
    impl: KClass<V>? = null
): RefreshingReadonlyProperty<T, V> {
    return RefreshingReadonlyProperty<T, V> { t: T, desc ->
        return@RefreshingReadonlyProperty get(impl)
    }
}

inline fun <reified T : Any> Any.get(
    impl: KClass<T>? = null
): T {
    return KokainInstance.mInstance!!.create(this, impl ?: T::class)
}
