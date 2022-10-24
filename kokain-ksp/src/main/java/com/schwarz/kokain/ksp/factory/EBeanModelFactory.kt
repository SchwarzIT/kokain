package com.schwarz.kokain.ksp.factory

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getVisibility
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.Visibility
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.ksp.model.EBeanModel
import com.schwarz.kokain.ksp.validation.PreValidator
import com.squareup.kotlinpoet.KModifier
import java.io.File

class EBeanModelFactory(private val logger: KSPLogger, val resolver: Resolver) {

    private val preValidator = PreValidator(logger, resolver)

    @OptIn(KspExperimental::class)
    fun create(element: KSAnnotated): EBeanModel? {

        (element as? KSClassDeclaration)?.let {
            if (preValidator.validateEbean(it)) {

                val scope = it.getAnnotationsByType(EBean::class).first().scope
                val simpleName = it.simpleName.asString()
                val sPackage = it.packageName.asString()

                var visibility: KModifier? = null
                for (f in resolver.getNewFiles()) {
                    for (l in File(f.filePath).readLines()) {
                        if (CLASS_IS_INTERNAL_REG_EX.find(l) != null) {
                            visibility = KModifier.INTERNAL
                        }
                    }
                }

                visibility = visibility ?: when (it.getVisibility()) {
                    Visibility.INTERNAL -> KModifier.INTERNAL
                    else -> KModifier.PUBLIC
                }
                return EBeanModel(scope, simpleName, sPackage, visibility)
            }
        } ?: logger.error("failed to process EBean annotation not a class file", element)
        return null
    }
}

private val CLASS_IS_INTERNAL_REG_EX =
    Regex("\\s*((internal)\\s*(?:data|open|abstract|sealed)?\\s*class\\s+)(\\w+)\\s*([({])?.*")
