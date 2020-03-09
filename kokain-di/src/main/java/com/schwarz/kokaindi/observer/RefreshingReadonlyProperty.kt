package com.schwarz.kokaindi.observer

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class RefreshingReadonlyProperty<T, V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        return initializer(thisRef, property)
    }
}