package com.schwarz.kokain.ksp.factory

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.schwarz.kokain.api.EFactory
import com.schwarz.kokain.ksp.model.EFactoryModel
import com.schwarz.kokain.ksp.util.getKSAnnotationsByType
import com.schwarz.kokain.ksp.util.getKSValueArgumentByName
import com.schwarz.kokain.ksp.validation.PreValidator
import com.squareup.kotlinpoet.ksp.toTypeName

class EFactoryModelFactory(val logger: KSPLogger, resolver: Resolver) {

    private val preValidator = PreValidator(logger, resolver)

    @OptIn(KspExperimental::class)
    fun create(element: KSAnnotated): EFactoryModel? {
        (element as? KSClassDeclaration)?.let {
            if (preValidator.validateFactory(it)) {
                val additionalFactories = element.getKSAnnotationsByType(EFactory::class)
                    .first()
                    .getKSValueArgumentByName("additionalFactories")?.value as List<KSType>

                val simpleName = element.simpleName.asString()
                val sPackage = element.packageName.asString()
                return EFactoryModel(additionalFactories.map { it.toTypeName() }, simpleName, sPackage, element.containingFile)
            }
        } ?: logger.error("failed to process EFactory annotation not a class file", element)
        return null
    }
}
