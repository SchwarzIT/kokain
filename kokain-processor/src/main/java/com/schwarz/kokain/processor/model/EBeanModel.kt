package com.schwarz.kokain.processor.model

import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.kokaingeneratorlib.model.IEBeanModel
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.metadata.KotlinPoetMetadataPreview
import com.squareup.kotlinpoet.metadata.toKmClass
import com.sun.tools.javac.code.Symbol
import kotlinx.metadata.Flag
import kotlinx.metadata.KmClass
import kotlinx.metadata.jvm.KotlinClassHeader
import kotlinx.metadata.jvm.KotlinClassMetadata
import javax.lang.model.element.Element

@OptIn(KotlinPoetMetadataPreview::class)
class EBeanModel(scope: EBean.Scope, sourceElement: Element) : IEBeanModel {

    private val kotlinClassMetadata: KmClass?

    init {
        kotlinClassMetadata = sourceElement?.getAnnotation(Metadata::class.java)?.let {
            it.toKmClass()
        }
    }

    override val scope: EBean.Scope = scope

    val sourceElement: Element = sourceElement

    override val sourceClazzSimpleName: String
        get() = (sourceElement as Symbol.ClassSymbol).simpleName.toString()

    override val `package`: String
        get() = (sourceElement as Symbol.ClassSymbol).packge().toString()

    override val generatedClazzTypeName: TypeName
        get() = ClassName(`package`, generatedClazzSimpleName)

    override val classVisibility: KModifier
        get() {
            // we are not interested in other visibilities since they're not injectable
            kotlinClassMetadata?.flags?.let {
                if (Flag.IS_INTERNAL(it)) {
                    return KModifier.INTERNAL
                }
            }
            return KModifier.PUBLIC
        }
}
