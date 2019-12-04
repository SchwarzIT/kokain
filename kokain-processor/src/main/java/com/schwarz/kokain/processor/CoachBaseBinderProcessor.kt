package com.schwarz.kokain.processor

import com.google.auto.service.AutoService
import com.kaufland.generation.CodeGenerator
import com.schwarz.kokain.processor.validation.PreValidator
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.EViewModel
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement


@SupportedAnnotationTypes("com.schwarz.kokain.api.EBean", "com.schwarz.kokain.api.EViewModel")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor::class)
class CoachBaseBinderProcessor : AbstractProcessor() {

    private var mLogger: Logger? = null

    private var mCodeGenerator: CodeGenerator? = null

    private var validator: PreValidator? = null


    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        mLogger = Logger(processingEnvironment)
        mCodeGenerator = CodeGenerator(processingEnvironment.filer)
        validator = PreValidator()
        super.init(processingEnvironment)
    }

    override fun process(set: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {


        var beans = roundEnv.getElementsAnnotatedWith(EBean::class.java)
        var viewModels = roundEnv.getElementsAnnotatedWith(EViewModel::class.java)
//        var viewModels = mapWrappers.map { element -> element.toString() }
//
//        validateAndProcess(roundEnv.getElementsAnnotatedWith(EBean::class.java), object : EntityProcessor {
//            override fun process(element: Element): FileSpec {
//                val holder = EntityFactory.createEntityHolder(element, mapWrapperStrings)
//                return EntityGeneration().generateModel(holder)
//            }
//        })
//
//
//        validateAndProcess(mapWrappers, object : EntityProcessor {
//            override fun process(element: Element): FileSpec {
//                val holder = EntityFactory.createChildEntityHolder(element, mapWrapperStrings)
//                return WrapperGeneration().generateModel(holder)
//            }
//        })

        return true // no further processing of this annotation type
    }

//    private fun validateAndProcess(elements: Collection<Element>, processor: EntityProcessor) {
//        for (elem in elements) {
//            try {
//                validator!!.validate(elem, mLogger!!)
//
//                if (!mLogger!!.hasErrors()) {
//                    val entityFile = processor.process(elem)
//                    mCodeGenerator!!.generate(entityFile)
//                }
//
//            } catch (e: ClassNotFoundException) {
//                mLogger!!.abortWithError("Clazz not found", elem, e)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                mLogger!!.abortWithError("generation failed", elem, e)
//            }
//
//        }
//    }


}
