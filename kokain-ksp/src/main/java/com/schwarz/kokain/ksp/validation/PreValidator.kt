package com.schwarz.kokain.ksp.validation

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.schwarz.kokain.api.EBean

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
        return true
    }
}
