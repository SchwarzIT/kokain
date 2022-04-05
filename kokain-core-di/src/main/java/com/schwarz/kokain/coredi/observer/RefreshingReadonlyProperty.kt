package com.schwarz.kokain.coredi.observer

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class RefreshingReadonlyProperty<T, V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {

    private var value: Any? = null

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (value == null) {
            value = initializer(thisRef, property)
        }
        return value as V
    }
}
