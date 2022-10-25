package com.schwarz.kokain.ksp.factory

import com.google.devtools.ksp.KSTypesNotPresentException
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.schwarz.kokain.api.EFactory
import com.schwarz.kokain.ksp.model.EFactoryModel
import com.schwarz.kokain.ksp.validation.PreValidator
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toTypeName

class EFactoryModelFactory(private val logger: KSPLogger, resolver: Resolver) {

    private val preValidator = PreValidator(logger, resolver)

    @OptIn(KspExperimental::class)
    fun create(element: KSAnnotated): EFactoryModel? {
        (element as? KSClassDeclaration)?.let { it ->
            if (preValidator.validateFactory(it)) {

                val factories = extractTypesNamesFromAdditionalFactoriesField(
                    element.getAnnotationsByType(EFactory::class).first()
                )
                val simpleName = element.simpleName.asString()
                val sPackage = element.packageName.asString()
                return EFactoryModel(factories, simpleName, sPackage, element.containingFile)
            }
        } ?: logger.error("failed to process EFactory annotation not a class file", element)
        return null
    }

    @OptIn(KspExperimental::class)
    private fun extractTypesNamesFromAdditionalFactoriesField(factory: EFactory): List<TypeName> {
        try {
            return factory.additionalFactories.map { it.asTypeName() }
        } catch (e: KSTypesNotPresentException) {
            return e.ksTypes.map { it.toTypeName() }
        }
    }
}
