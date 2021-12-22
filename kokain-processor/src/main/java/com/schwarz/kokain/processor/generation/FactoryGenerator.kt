package com.schwarz.kokain.processor.generation

import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.processor.model.EBeanModel
import com.schwarz.kokain.processor.model.EFactoryModel
import com.schwarz.kokain.processor.util.TypeUtil
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import java.lang.StringBuilder
import java.util.StringJoiner
import kotlin.collections.ArrayList

class FactoryGenerator {

    private final val ADDITIONAL_FACTORY_PROPERTY_NAME = "additonalFactories"

    fun generateModel(factory: EFactoryModel, beans: List<EBeanModel>): FileSpec {
        val typeBuilder = TypeSpec.classBuilder("GeneratedFactory").addModifiers(KModifier.PUBLIC).addSuperinterface(TypeUtil.kdiFactory())
            .addProperty(propAdditionalFactories(factory))
            .addFunction(create(beans))

        return FileSpec.get(factory.`package`, typeBuilder.build())
    }

    private fun propAdditionalFactories(factory: EFactoryModel): PropertySpec {

        val builder = StringBuilder("arrayOf<%T>")
        val types = ArrayList<Any>()
        val joiner = StringJoiner(",", "(", ")")
        types.add(TypeUtil.kdiFactory())
        for (factory in factory.additionalFactories) {
            if (factory.asTypeName() != TypeUtil.void()) {
                joiner.add("%T()")
                types.add(factory.asTypeName())
            }
        }
        builder.append(joiner)

        return PropertySpec.builder(ADDITIONAL_FACTORY_PROPERTY_NAME, TypeUtil.arrayKdiFactories()).initializer(builder.toString(), *types.toTypedArray()).build()
    }

    private fun create(beans: List<EBeanModel>): FunSpec {

        var builder = FunSpec.builder("createInstance").addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE).addParameter("clazz", TypeUtil.classStar()).returns(TypeUtil.any().copy(true))

        builder = builder.beginControlFlow("when(clazz)")

        for (bean in beans) {
            builder.addStatement("%T::class -> return·%T(%M)", ClassName(bean.`package`, bean.sourceClazzSimpleName), ClassName(bean.`package`, bean.generatedClazzSimpleName), mapScope(bean.scope))
        }

        builder.endControlFlow()

        builder.beginControlFlow("for(factory in $ADDITIONAL_FACTORY_PROPERTY_NAME)")
        builder.addStatement("val bean = factory.createInstance(clazz)")
        builder.beginControlFlow("if(bean != null)")
        builder.addStatement("return bean")
        builder.endControlFlow()
        builder.endControlFlow()

        builder.addStatement("return null")

        return builder.build()
    }

    private fun mapScope(scope: EBean.Scope): MemberName {

        return MemberName(TypeUtil.scope() as ClassName, scope.toString())
    }

//    class GeneratedFactory : com.schwarz.kokaindi.KDiFactory {
//
//
//        override fun createLazy(kClass: KClass<*>): Any {
//            return when (kClass) {
//                FooBean::class -> GeneratedFooBean()
//                else -> throw RuntimeException()
//            }
//        }
//
//
//    }
}
