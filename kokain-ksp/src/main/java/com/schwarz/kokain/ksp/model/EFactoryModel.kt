package com.schwarz.kokain.ksp.model

import com.google.devtools.ksp.symbol.KSFile
import com.schwarz.kokain.kokaingeneratorlib.model.IEFactoryModel
import com.squareup.kotlinpoet.TypeName

data class EFactoryModel(
    override val additionalFactories: List<TypeName>,
    override val sourceClazzSimpleName: String,
    override val `package`: String,
    val containingFile: KSFile?
) : IEFactoryModel
