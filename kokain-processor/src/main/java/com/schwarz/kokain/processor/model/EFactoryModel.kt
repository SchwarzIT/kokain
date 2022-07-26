package com.schwarz.kokain.processor.model

import com.schwarz.kokain.api.EFactory
import com.schwarz.kokain.kokaingeneratorlib.model.IEFactoryModel
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import com.sun.tools.javac.code.Symbol
import javax.lang.model.element.Element
import javax.lang.model.type.MirroredTypesException
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements

class EFactoryModel(sourceElement: Element, util: Elements) : IEFactoryModel {

    var additionalFactoriesTypeMirror: Array<TypeMirror> = sourceElement.getAnnotation(EFactory::class.java).let {
        try {
            it.additionalFactories.map { util.getTypeElement(it.qualifiedName).asType() }
                .toTypedArray() // this should throw
        } catch (mte: MirroredTypesException) {
            mte.typeMirrors.toTypedArray()
        }
    }

    val sourceElement: Element? = sourceElement
    override val additionalFactories: List<TypeName> = additionalFactoriesTypeMirror.map { it.asTypeName() }.toList()

    override val sourceClazzSimpleName: String
        get() = (sourceElement as Symbol.ClassSymbol).simpleName.toString()

    override val `package`: String
        get() = (sourceElement as Symbol.ClassSymbol).packge().toString()
}
