package com.schwarz.kokain.processor.validation

import com.schwarz.kokain.processor.Logger
//import com.schwarz.kokain.processor.util.FieldExtractionUtil
//import com.sun.tools.javac.code.Symbol
//import com.schwarz.kokain.api.EBean
//import kaufland.com.coachbasebinderapi.Fields
import java.util.*
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier

class PreValidator {


    @Throws(ClassNotFoundException::class)
    fun validate(entityElement: Element, logger: Logger) {
//
//        if (entityElement.modifiers.contains(Modifier.PRIVATE)) {
//            logger.error(EBean::class.java.simpleName + " can not be private", entityElement)
//        }
//        if (entityElement.modifiers.contains(Modifier.FINAL)) {
//            logger.error(EBean::class.java.simpleName + " can not be final", entityElement)
//        }
//
//        val fields = entityElement.getAnnotation(Fields::class.java)
//
//        val names = ArrayList<String>()
//
//        for (fieldAnnotation in fields.value) {
//
//            if (fieldAnnotation != null) {
//
//                if (names.contains(fieldAnnotation!!.name)) {
//                    logger.warn("duplicated field name", entityElement)
//                }
//
//                if (!fieldAnnotation!!.defaultValue.isEmpty()) {
//                    if (fieldAnnotation!!.list || !Arrays.asList(String::class.java.canonicalName, Long::class.java.canonicalName, Double::class.java.canonicalName, Int::class.java.canonicalName, Integer::class.java.canonicalName, Boolean::class.java.canonicalName).contains(FieldExtractionUtil.typeMirror(fieldAnnotation!!)!!.toString())) {
//                        logger.error("defaultValue must be must be String.class, Long.class, Double.class or Integer.class but was ${FieldExtractionUtil.typeMirror(fieldAnnotation!!)}", entityElement)
//                    }
//                }
//
//                if (fieldAnnotation!!.readonly && fieldAnnotation!!.defaultValue.isEmpty()) {
//                    logger.warn("defaultValue should not be empty for readonly fields", entityElement)
//                }
//                names.add(fieldAnnotation!!.name)
//
//            }
//
//        }
//
//        for (member in entityElement.enclosedElements) {
//
//            if (member.kind == ElementKind.CONSTRUCTOR) {
//
//                val constructor = member as Symbol.MethodSymbol
//
//                if (constructor.parameters.size != 0) {
//                    logger.error(EBean::class.java.simpleName + " should not have a contructor", member)
//                }
//            }
//        }


    }
}
