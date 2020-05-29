package com.schwarz.kokain.processor.model

import com.schwarz.kokain.api.EBean
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.sun.org.apache.xpath.internal.operations.Mod
import com.sun.tools.javac.code.Symbol
import kotlinx.metadata.Flag
import kotlinx.metadata.Flags
import kotlinx.metadata.KmClass
import kotlinx.metadata.jvm.KotlinClassHeader
import kotlinx.metadata.jvm.KotlinClassMetadata
import sun.tools.util.ModifierFilter
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier

class EBeanModel(scope: EBean.Scope, sourceElement: Element) {

    val kotlinClassMetadata: KmClass?

    init {
        var meta = sourceElement?.getAnnotation(Metadata::class.java)
        kotlinClassMetadata = (KotlinClassMetadata.read(KotlinClassHeader(meta.kind, meta.metadataVersion, meta.bytecodeVersion, meta.data1, meta.data2, meta.extraString, meta.packageName, meta.extraInt)) as? KotlinClassMetadata.Class)?.toKmClass()
    }

    val scope: EBean.Scope = scope

    val sourceElement: Element = sourceElement

    val sourceClazzSimpleName: String
        get() = (sourceElement as Symbol.ClassSymbol).simpleName.toString()

    val generatedClazzSimpleName: String
        get() = sourceClazzSimpleName + "Shadow"

    val `package`: String
        get() = (sourceElement as Symbol.ClassSymbol).packge().toString()

    val generatedClazzTypeName: TypeName
        get() = ClassName(`package`, generatedClazzSimpleName)

    val classVisibility : KModifier
    get() {
        // we are not interested in other visibilities since they're not injectable
        kotlinClassMetadata?.let {
            if(Flag.IS_INTERNAL(kotlinClassMetadata?.flags)){
                return KModifier.INTERNAL
            }
        }
        return KModifier.PUBLIC
    }


}