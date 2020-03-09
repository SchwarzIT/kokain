package com.schwarz.kokain.processor.generation

import com.schwarz.kokain.processor.model.EBeanModel
import com.schwarz.kokain.processor.util.TypeUtil
import com.squareup.kotlinpoet.*

class ShadowBeanGenerator{

    fun generateModel(holder: EBeanModel): FileSpec{
        val typeBuilder = TypeSpec.classBuilder(holder.generatedClazzSimpleName).addModifiers(KModifier.PUBLIC).addSuperinterface(TypeUtil.activityRefered())
                .addProperty(PropertySpec.builder("activityRef", TypeUtil.nullableString(), KModifier.PUBLIC, KModifier.OVERRIDE).mutable().initializer("null").build())

        return FileSpec.get(holder.`package`, typeBuilder.build())
    }


}
