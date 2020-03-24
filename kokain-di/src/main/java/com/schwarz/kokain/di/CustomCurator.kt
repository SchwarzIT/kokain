package com.schwarz.kokain.di

import kotlin.reflect.KClass

interface CustomCurator{

    fun <V : Any> getInstance(clazz: KClass<*>): V?
}