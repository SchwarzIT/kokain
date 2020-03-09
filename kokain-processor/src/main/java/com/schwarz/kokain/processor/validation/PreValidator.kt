package com.schwarz.kokain.processor.validation

import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.processor.Logger
import com.sun.tools.javac.code.Symbol
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier

class PreValidator {


    @Throws(ClassNotFoundException::class)
    fun validate(entityElement: Element, logger: Logger) {

        if (entityElement.modifiers.contains(Modifier.PRIVATE)) {
            logger.error(EBean::class.java.simpleName + " can not be private", entityElement)
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
}
