package com.schwarz.kokain.processor.validation

import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.EFactory
import com.schwarz.kokain.processor.Logger
import com.schwarz.kokain.processor.model.EBeanModel
import com.schwarz.kokain.processor.model.EFactoryModel
import com.schwarz.kokain.processor.util.TypeUtil
import com.sun.tools.javac.code.Symbol
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

class PreValidator(logger: Logger, types: Types, elements: Elements) {

    private val logger = logger

    private val types = types

    private val elements = elements

    @Throws(ClassNotFoundException::class)
    fun validate(model: EBeanModel) {

        val entityElement = model.sourceElement

        if (entityElement.modifiers.contains(Modifier.PRIVATE)) {
            logger.error(EBean::class.java.simpleName + " can not be private", entityElement)
        }
        if (entityElement.modifiers.contains(Modifier.PROTECTED)) {
            logger.error(EBean::class.java.simpleName + " can not be protected", entityElement)
        }
        if (entityElement.modifiers.contains(Modifier.FINAL)) {
            logger.error(EBean::class.java.simpleName + " can not be final", entityElement)
        }

        for (member in entityElement.enclosedElements) {

            if (member.kind == ElementKind.CONSTRUCTOR) {

                val constructor = member as Symbol.MethodSymbol

                if (constructor.parameters.size != 0) {
                    logger.error(EBean::class.java.simpleName + " should not have a contructor", member)
                }
            }
        }
    }

    @Throws(ClassNotFoundException::class)
    fun validateFactory(model: EFactoryModel) {

        for (factory in model.additionalFactories) {

            if (!types.isSameType(factory, elements.getTypeElement("java.lang.Void").asType()) && !types.isAssignable(factory, elements.getTypeElement("${TypeUtil.KOKAIN_API_BASE_PACKAGE}.KDiFactory").asType())) {
                logger.error(EFactory::class.java.simpleName + " additionalFactories have to implement KdiFactory", model.sourceElement)
            }
        }
    }
}
