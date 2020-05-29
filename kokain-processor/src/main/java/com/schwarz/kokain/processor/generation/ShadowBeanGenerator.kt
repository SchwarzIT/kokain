package com.schwarz.kokain.processor.generation

import com.schwarz.kokain.processor.model.EBeanModel
import com.schwarz.kokain.processor.util.TypeUtil
import com.squareup.kotlinpoet.*

class ShadowBeanGenerator{

    fun generateModel(holder: EBeanModel): FileSpec{
        val typeBuilder = TypeSpec.classBuilder(holder.generatedClazzSimpleName).addModifiers(holder.classVisibility).superclass(ClassName(holder.`package`, holder.sourceClazzSimpleName)).addSuperinterface(TypeUtil.activityRefered()).addSuperinterface(TypeUtil.beanScope())
                .primaryConstructor(FunSpec.constructorBuilder().addParameter("pscope", TypeUtil.scope()).build())
                .addProperty(PropertySpec.builder("activityRef", TypeUtil.nullableString(), KModifier.PUBLIC, KModifier.OVERRIDE).mutable().initializer("null").build())
                .addProperty(PropertySpec.builder("scope", TypeUtil.scope(), KModifier.PUBLIC, KModifier.OVERRIDE).mutable().initializer("pscope").build())

        return FileSpec.get(holder.`package`, typeBuilder.build())
    }


}
