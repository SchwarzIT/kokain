package com.schwarz.kokain.processor.model

import com.schwarz.kokain.api.EBean
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.sun.tools.javac.code.Symbol
import javax.lang.model.element.Element

class EBeanModel(scope: EBean.Scope, sourceElement: Element) {

    val scope: EBean.Scope = scope

    val sourceElement: Element? = sourceElement

    val sourceClazzSimpleName: String
        get() = (sourceElement as Symbol.ClassSymbol).simpleName.toString()

    val generatedClazzSimpleName: String
        get() = sourceClazzSimpleName + "Shadow"

    val `package`: String
        get() = (sourceElement as Symbol.ClassSymbol).packge().toString()

    val generatedClazzTypeName: TypeName
        get() = ClassName(`package`, generatedClazzSimpleName)
}