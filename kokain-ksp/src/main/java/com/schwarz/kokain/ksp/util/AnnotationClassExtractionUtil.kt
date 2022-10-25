package com.schwarz.kokain.ksp.util

import com.google.devtools.ksp.KSTypesNotPresentException
import com.google.devtools.ksp.KspExperimental
import com.schwarz.kokain.api.EFactory
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toTypeName

@OptIn(KspExperimental::class)
fun extractTypesNamesFromAdditionalFactoriesField(factory: EFactory): List<TypeName> {
    try {
        return factory.additionalFactories.map { it.asTypeName() }
    } catch (e: KSTypesNotPresentException) {
        return e.ksTypes.map { it.toTypeName() }
    }
}
