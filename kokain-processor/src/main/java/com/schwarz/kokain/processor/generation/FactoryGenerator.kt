package com.schwarz.kokain.processor.generation

import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.processor.model.EBeanModel
import com.schwarz.kokain.processor.util.TypeUtil
import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass

class FactoryGenerator{

    fun generateModel(packageName: String, beans: List<EBeanModel>): FileSpec{
        val typeBuilder = TypeSpec.classBuilder("GeneratedFactory").addModifiers(KModifier.PUBLIC).addSuperinterface(TypeUtil.kdiFactory())
                .addFunction(create(beans))

        return FileSpec.get(packageName, typeBuilder.build())
    }

    private fun create(beans: List<EBeanModel>) : FunSpec{

        var builder = FunSpec.builder("createInstance").addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE).addParameter("clazz", TypeUtil.classStar()).returns(TypeUtil.any())

        builder = builder.beginControlFlow("return when(clazz)")

        for (bean in beans) {

            if(bean.scope == EBean.Scope.Singleton){
                builder.addStatement("%1T::class -> %1T()", ClassName(bean.`package`, bean.sourceClazzSimpleName))
            }else{
                builder.addStatement("%T::class -> %T()", ClassName(bean.`package`, bean.sourceClazzSimpleName), ClassName(bean.`package`, bean.generatedClazzSimpleName))
            }

        }

        builder.addStatement("else -> throw %T()", ClassName("java.lang", "RuntimeException"))
        builder.endControlFlow()
        return builder.build()
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
