package com.schwarz.kokain.kokaingeneratorlib.model

import com.schwarz.kokain.api.EBean
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName

interface IEBeanModel{

    val scope: EBean.Scope

    val sourceClazzSimpleName: String

    val generatedClazzSimpleName: String
        get() = sourceClazzSimpleName + "Shadow"

    val `package`: String

    val generatedClazzTypeName: TypeName
        get() = ClassName(`package`, generatedClazzSimpleName)

    val classVisibility: KModifier
}
