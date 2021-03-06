package com.schwarz.kokain.processor.util


import com.squareup.kotlinpoet.*

import javax.lang.model.type.TypeMirror
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

object TypeUtil {

    const val KOKAIN_API_BASE_PACKAGE = "com.schwarz.kokain.api"

    fun string(): TypeName {
        return ClassName("kotlin", "String")
    }

    fun nullableString(): TypeName {
        return string().copy(true)
    }

    fun any(): TypeName {
        return ClassName("kotlin", "Any")
    }

    fun anyNullable(): TypeName {
        return any().copy(nullable = true)
    }

    fun star(): TypeName {
        return WildcardTypeName.producerOf(anyNullable())
    }

    fun hashMapStringObject(): ParameterizedTypeName {
        return ClassName("kotlin.collections", "HashMap").parameterizedBy(string(), anyNullable())
    }

    fun mapStringObject(): ParameterizedTypeName {
        return ClassName("kotlin.collections", "Map").parameterizedBy(string(), anyNullable())
    }

    fun mutableMapStringObject(): ParameterizedTypeName {
        return ClassName("kotlin.collections", "MutableMap").parameterizedBy(string(), anyNullable())
    }

    fun listWithMutableMapStringObject(): ParameterizedTypeName {
        return ClassName("kotlin.collections", "List").parameterizedBy(mutableMapStringObject())
    }

    fun list(typeName: TypeName): ParameterizedTypeName {
        return ClassName("kotlin.collections", "List").parameterizedBy(typeName)
    }

    fun arrayList(typeName: TypeName): ParameterizedTypeName {
        return ClassName("kotlin.collections", "ArrayList").parameterizedBy(typeName)
    }

    fun arrayListWithHashMapStringObject(): ParameterizedTypeName {
        return ClassName("kotlin.collections", "ArrayList").parameterizedBy(hashMapStringObject())
    }

    fun activityRefered(): TypeName {
        return ClassName("$KOKAIN_API_BASE_PACKAGE.internal", "ActivityRefered")
    }

    fun beanScope(): TypeName {
        return ClassName("$KOKAIN_API_BASE_PACKAGE.internal", "BeanScope")
    }

    fun scope(): TypeName {
        return ClassName("$KOKAIN_API_BASE_PACKAGE.EBean", "Scope")
    }

    fun ebean(): TypeName {
        return ClassName("$KOKAIN_API_BASE_PACKAGE", "EBean")
    }

    fun kdiFactory(): TypeName {
        return ClassName("$KOKAIN_API_BASE_PACKAGE", "KDiFactory")
    }

    fun arrayKdiFactories() : TypeName{
        return ClassName("kotlin", "Array").parameterizedBy(kdiFactory())
    }

    fun getSimpleName(type: TypeMirror): String {
        val parts = type.toString().split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (parts.size > 1) parts[parts.size - 1] else parts[0]
    }

    fun getPackage(type: TypeMirror): String {
        val lastIndexOf = type.toString().lastIndexOf(".")
        return if (lastIndexOf >= 0) type.toString().substring(0, lastIndexOf) else type.toString()
    }

    fun classStar(): ParameterizedTypeName {
        return ClassName("kotlin.reflect", "KClass").parameterizedBy(star())
    }

    fun void(): TypeName {
        return ClassName("java.lang", "Void")
    }
}
