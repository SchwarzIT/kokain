package com.schwarz.kokain.processor.model

import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.kokaingeneratorlib.model.IEBeanModel
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.sun.tools.javac.code.Symbol
import kotlin.metadata.KmClass
import kotlin.metadata.Visibility
import kotlin.metadata.jvm.KotlinClassMetadata
import kotlin.metadata.visibility
import javax.lang.model.element.Element

class EBeanModel(scope: EBean.Scope, sourceElement: Element) : IEBeanModel {

    private val kotlinClassMetadata: KmClass?

    init {
        kotlinClassMetadata = sourceElement?.getAnnotation(Metadata::class.java)?.let {
            (KotlinClassMetadata.readStrict(it) as? KotlinClassMetadata.Class)?.kmClass
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
            if (kotlinClassMetadata?.visibility == Visibility.INTERNAL) {
                return KModifier.INTERNAL
            }
            return KModifier.PUBLIC
        }
}
