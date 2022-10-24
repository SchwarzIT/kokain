package com.schwarz.kokain.ksp.model

import com.google.devtools.ksp.symbol.KSFile
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.kokaingeneratorlib.model.IEBeanModel
import com.squareup.kotlinpoet.KModifier

data class EBeanModel(
    override val scope: EBean.Scope,
    override val sourceClazzSimpleName: String,
    override val `package`: String,
    override val classVisibility: KModifier,
    val containingFile: KSFile?
) : IEBeanModel
