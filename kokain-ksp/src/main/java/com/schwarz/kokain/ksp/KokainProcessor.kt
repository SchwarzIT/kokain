package com.schwarz.kokain.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.api.EFactory
import com.schwarz.kokain.kokaingeneratorlib.generation.FactoryGenerator
import com.schwarz.kokain.kokaingeneratorlib.generation.ShadowBeanGenerator
import com.schwarz.kokain.ksp.factory.EBeanModelFactory
import com.schwarz.kokain.ksp.factory.EFactoryModelFactory
import com.schwarz.kokain.ksp.generation.KokainCodeGenerator

class KokainProcessor(codeGenerator: CodeGenerator, private val logger: KSPLogger) :
    SymbolProcessor {

    private val shadowBeanGenerator = ShadowBeanGenerator()

    private val factoryGenerator = FactoryGenerator()

    private val codeGenerator = KokainCodeGenerator(codeGenerator)

    override fun process(resolver: Resolver): List<KSAnnotated> {

        val eBeanModelFactory = EBeanModelFactory(logger, resolver)
        val eFactoryModelFactory = EFactoryModelFactory(logger, resolver)

        val beanModel = resolver.getSymbolsWithAnnotation(EBean::class.qualifiedName!!)
            .mapNotNull { eBeanModelFactory.create(it) }.toList()

        val factory =
            resolver.getSymbolsWithAnnotation(EFactory::class.qualifiedName!!).firstOrNull()
                ?.let {
                    eFactoryModelFactory.create(it)
                }

        beanModel.forEach {
            codeGenerator.generate(
                shadowBeanGenerator.generateModel(it),
                listOf(it.containingFile!!)
            )
        }

        factory?.let {
            val dependencies = mutableListOf(it.containingFile!!)
            dependencies.addAll(beanModel.map { it.containingFile!! })
            codeGenerator.generate(factoryGenerator.generateModel(it, beanModel), dependencies)
        }
        return emptyList()
    }
}

class KokainProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor {
        return KokainProcessor(environment.codeGenerator, environment.logger)
    }
}
