package com.schwarz.kokain.kokaingeneratorlib.model

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName

interface IEFactoryModel {

    val additionalFactories: List<TypeName>

    val sourceClazzSimpleName: String

    val generatedClazzSimpleName: String
        get() = sourceClazzSimpleName + "Shadow"

    val `package`: String

    val generatedClazzTypeName: TypeName
        get() = ClassName(`package`, generatedClazzSimpleName)
}
