package com.schwarz.kokain.kokaingeneratorlib.util

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.WildcardTypeName

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

    fun arrayKdiFactories(): TypeName {
        return ClassName("kotlin", "Array").parameterizedBy(kdiFactory())
    }

    fun classStar(): ParameterizedTypeName {
        return ClassName("kotlin.reflect", "KClass").parameterizedBy(star())
    }

    fun void(): TypeName {
        return ClassName("java.lang", "Void")
    }
}
