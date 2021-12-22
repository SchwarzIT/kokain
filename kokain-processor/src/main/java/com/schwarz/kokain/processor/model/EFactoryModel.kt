package com.schwarz.kokain.processor.model

import com.schwarz.kokain.api.EFactory
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.sun.tools.javac.code.Symbol
import javax.lang.model.element.Element
import javax.lang.model.type.MirroredTypesException
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements

class EFactoryModel(sourceElement: Element, util: Elements) {

    var additionalFactories: Array<TypeMirror>

    val sourceElement: Element? = sourceElement

    val sourceClazzSimpleName: String
        get() = (sourceElement as Symbol.ClassSymbol).simpleName.toString()

    val generatedClazzSimpleName: String
        get() = sourceClazzSimpleName + "Shadow"

    val `package`: String
        get() = (sourceElement as Symbol.ClassSymbol).packge().toString()

    val generatedClazzTypeName: TypeName
        get() = ClassName(`package`, generatedClazzSimpleName)

    init {
        val annotation = sourceElement.getAnnotation(EFactory::class.java)
        try {
            additionalFactories = annotation.additionalFactories.map { util.getTypeElement(it.qualifiedName).asType() }.toTypedArray() // this should throw
        } catch (mte: MirroredTypesException) {
            additionalFactories = mte.typeMirrors.toTypedArray()
        }
    }
}
