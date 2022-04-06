package com.schwarz.kokain.dijvm

import kotlin.reflect.KClass
import com.schwarz.kokain.corelib.observer.RefreshingReadonlyProperty

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
