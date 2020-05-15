package com.schwarz.kokain.processor

import com.google.auto.service.AutoService
import com.kaufland.generation.CodeGenerator
import com.schwarz.kokain.processor.validation.PreValidator
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.EFactory
import com.schwarz.kokain.processor.generation.FactoryGenerator
import com.schwarz.kokain.processor.generation.ShadowBeanGenerator
import com.schwarz.kokain.processor.model.EBeanModel
import com.schwarz.kokain.processor.model.EFactoryModel
import com.schwarz.kokain.processor.util.TypeUtil
import java.lang.RuntimeException
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types


@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor::class)
class KokainProcessor : AbstractProcessor() {

    private lateinit var mLogger: Logger

    private lateinit var mCodeGenerator: CodeGenerator

    private val shadowBeanGenerator = ShadowBeanGenerator()

    private val factoryGenerator = FactoryGenerator()

    private lateinit var elementUtils : Elements

    private lateinit var typeUtils : Types

    private lateinit var validator : PreValidator

    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        mLogger = Logger(processingEnvironment)
        mCodeGenerator = CodeGenerator(processingEnvironment.filer)
        elementUtils = processingEnvironment.elementUtils
        typeUtils = processingEnvironment.typeUtils
        validator = PreValidator(mLogger, typeUtils, elementUtils)
        super.init(processingEnvironment)
    }

    override fun process(set: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        if(set.isEmpty()){
            //no annotation we should take care off
            return false
        }

        var beanModel = roundEnv.getElementsAnnotatedWith(EBean::class.java).map { EBeanModel(it.getAnnotation(EBean::class.java).scope, it) }

        var factory = roundEnv.getElementsAnnotatedWith(EFactory::class.java)

        if (factory.isEmpty()) {
            mLogger!!.error("no factory annotation found")
            return false
        }

        for (bean in beanModel) {
            try {
                validator.validate(bean.sourceElement!!)

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


        val factoryModel = EFactoryModel(factory.first(), elementUtils!!)

        validator.validateFactory(factoryModel)

        if (!mLogger!!.hasErrors()) {
            mCodeGenerator!!.generate(factoryGenerator.generateModel(factoryModel, beanModel))
        }
        return true // no further processing of this annotation type
    }


    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return setOf(EBean::class.java.canonicalName, EFactory::class.java.canonicalName).toMutableSet()
    }
}
