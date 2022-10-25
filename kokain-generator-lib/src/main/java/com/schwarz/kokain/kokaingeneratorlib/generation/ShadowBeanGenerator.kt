package com.schwarz.kokain.kokaingeneratorlib.generation

import com.schwarz.kokain.kokaingeneratorlib.model.IEBeanModel
import com.schwarz.kokain.kokaingeneratorlib.util.TypeUtil
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

class ShadowBeanGenerator {

    fun generateModel(holder: IEBeanModel): FileSpec {
        val typeBuilder = TypeSpec.classBuilder(holder.generatedClazzSimpleName)
            .addModifiers(holder.classVisibility).superclass(
                ClassName(holder.`package`, holder.sourceClazzSimpleName)
            ).addSuperinterface(TypeUtil.activityRefered()).addSuperinterface(TypeUtil.beanScope())
            .primaryConstructor(
                FunSpec.constructorBuilder().addParameter("pscope", TypeUtil.scope()).build()
            )
            .addProperty(
                PropertySpec.builder(
                    "activityRef",
                    TypeUtil.nullableString(),
                    KModifier.PUBLIC,
                    KModifier.OVERRIDE
                ).mutable().initializer("null").build()
            )
            .addProperty(
                PropertySpec.builder(
                    "scope",
                    TypeUtil.scope(),
                    KModifier.PUBLIC,
                    KModifier.OVERRIDE
                ).mutable().initializer("pscope").build()
            )

        return FileSpec.get(holder.`package`, typeBuilder.build())
    }
}
