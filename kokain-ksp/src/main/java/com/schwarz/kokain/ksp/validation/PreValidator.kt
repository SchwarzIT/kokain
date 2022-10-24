package com.schwarz.kokain.ksp.validation

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getJavaClassByName
import com.google.devtools.ksp.getKotlinClassByName
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.Modifier
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.EFactory
import com.schwarz.kokain.api.KDiFactory
import com.schwarz.kokain.ksp.util.getKSAnnotationsByType
import com.schwarz.kokain.ksp.util.getKSValueArgumentByName
import com.squareup.kotlinpoet.ksp.toTypeName

class PreValidator(logger: KSPLogger, val resolver: Resolver) {

    private val logger = logger

    @Throws(ClassNotFoundException::class)
    fun validateEbean(model: KSClassDeclaration): Boolean {

        val entityElement = model
        var result = true

        if (entityElement.modifiers.contains(Modifier.PRIVATE)) {
            logger.error(EBean::class.java.simpleName + " can not be private", entityElement)
            result = false
        }
        if (entityElement.modifiers.contains(Modifier.PROTECTED)) {
            logger.error(EBean::class.java.simpleName + " can not be protected", entityElement)
            result = false
        }
        if (entityElement.modifiers.contains(Modifier.FINAL)) {
            logger.error(EBean::class.java.simpleName + " can not be final", entityElement)
            result = false
        }

        entityElement.primaryConstructor?.let {
            if (it.parameters.isNotEmpty()) {
                logger.error(EBean::class.java.simpleName + " should not have a contructor", it)
                result = false
            }
        }
        return result
    }

    @KspExperimental
    @Throws(ClassNotFoundException::class)
    fun validateFactory(model: KSClassDeclaration): Boolean {

        var result = true
        val additionalFactories = model.getKSAnnotationsByType(EFactory::class)
            .first()
            .getKSValueArgumentByName("additionalFactories")?.value as List<KSType>

        val kdiFactoryKsType = resolver.getKotlinClassByName(KDiFactory::class.qualifiedName!!)?.asType(
            emptyList()
        )
        val voidKsType = resolver.getJavaClassByName("java.lang.Void")?.asType(listOf())

        if (additionalFactories.isEmpty()) {
            logger.error("additionalFactories should never be empty this can only happen by a ksp bug. See also https://github.com/google/ksp/issues/888")
            logger.error("add this annotation to a java class to workaround this issue.", model)
        }
        additionalFactories.forEach {

            val containsKdiFactorySupertype: Boolean = (it.declaration as? KSClassDeclaration)?.let {
                it.superTypes.any { it.toTypeName() == kdiFactoryKsType!!.toTypeName() }
            } == true

            if (containsKdiFactorySupertype.not() && it != voidKsType!!) {
                logger.error(
                    EFactory::class.java.simpleName + " additionalFactories have to implement KdiFactory",
                    model
                )
                result = false
            }
        }
        return result
    }
}
