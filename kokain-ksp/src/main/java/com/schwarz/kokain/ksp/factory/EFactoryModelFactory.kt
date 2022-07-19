package com.schwarz.kokain.ksp.factory

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.schwarz.kokain.api.EFactory
import com.schwarz.kokain.ksp.model.EFactoryModel
import com.squareup.kotlinpoet.asTypeName

class EFactoryModelFactory {

    companion object {
        @OptIn(KspExperimental::class)
        fun create(logger: KSPLogger, element: KSAnnotated): EFactoryModel? {
            (element as? KSClassDeclaration)?.let {
                val additionalFactories = element.getAnnotationsByType(EFactory::class)
                    .first()
                    .additionalFactories.map { it.asTypeName() }
                    .toList()

                val simpleName = element.simpleName.asString()
                val sPackage = element.packageName.asString()
                return EFactoryModel(additionalFactories, simpleName, sPackage)
            } ?: logger.error("failed to process EFactory annotation not a class file", element)
            return null

        }
    }
}