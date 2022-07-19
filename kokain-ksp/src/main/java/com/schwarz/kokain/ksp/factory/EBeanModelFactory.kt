package com.schwarz.kokain.ksp.factory

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getVisibility
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.Visibility
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.ksp.model.EBeanModel
import com.squareup.kotlinpoet.KModifier

class EBeanModelFactory {

    companion object {
        @OptIn(KspExperimental::class)
        fun create(logger: KSPLogger, element: KSAnnotated): EBeanModel? {
            (element as? KSClassDeclaration)?.let {
                val scope = it.getAnnotationsByType(EBean::class).first().scope
                val simpleName = it.simpleName.asString()
                val sPackage = it.packageName.asString()
                val visibility = when (it.getVisibility()) {
                    Visibility.INTERNAL -> KModifier.INTERNAL
                    else -> KModifier.PUBLIC
                }
                return EBeanModel(scope, simpleName, sPackage, visibility)
            } ?: logger.error("failed to process EBean annotation not a class file", element)
            return null

        }
    }
}