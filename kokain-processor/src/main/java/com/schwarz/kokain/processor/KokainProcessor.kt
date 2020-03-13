package com.schwarz.kokain.processor

import com.google.auto.service.AutoService
import com.kaufland.generation.CodeGenerator
import com.schwarz.kokain.processor.validation.PreValidator
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.EFactory
import com.schwarz.kokain.processor.generation.FactoryGenerator
import com.schwarz.kokain.processor.generation.ShadowBeanGenerator
import com.schwarz.kokain.processor.model.EBeanModel
import com.squareup.kotlinpoet.FileSpec
import com.sun.tools.javac.code.Symbol
import java.lang.RuntimeException
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement


@SupportedAnnotationTypes("com.schwarz.kokain.api.EBean", "com.schwarz.kokain.api.EFactory")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor::class)
class KokainProcessor : AbstractProcessor() {

    private var mLogger: Logger? = null

    private var mCodeGenerator: CodeGenerator? = null

    private var validator = PreValidator()

    private val shadowBeanGenerator = ShadowBeanGenerator()

    private val factoryGenerator = FactoryGenerator()


    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        mLogger = Logger(processingEnvironment)
        mCodeGenerator = CodeGenerator(processingEnvironment.filer)
        super.init(processingEnvironment)
    }

    override fun process(set: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        var beanModel = roundEnv.getElementsAnnotatedWith(EBean::class.java).map { EBeanModel(it.getAnnotation(EBean::class.java).scope ,it)  }

        var factory = roundEnv.getElementsAnnotatedWith(EFactory::class.java)

        //This should not happen but it does :-/
        if(beanModel.isEmpty()){
            return true
        }

        if(factory.isEmpty()){
            mLogger!!.error("no factory annotation found")
            throw RuntimeException()
        }

        for (bean in beanModel) {
            try {
                validator.validate(bean.sourceElement!!, mLogger!!)

                if (!mLogger!!.hasErrors()) {
                    val entityFile = shadowBeanGenerator.generateModel(bean)
                    mCodeGenerator!!.generate(entityFile)
                }

            } catch (e: ClassNotFoundException) {
                mLogger!!.abortWithError("Clazz not found", bean.sourceElement, e)
            } catch (e: Exception) {
                e.printStackTrace()
                mLogger!!.abortWithError("generation failed", bean.sourceElement, e)
            }

        }




        mCodeGenerator!!.generate(factoryGenerator.generateModel((factory.first() as Symbol.ClassSymbol).packge().toString(), beanModel))
        return true // no further processing of this annotation type
    }



}
