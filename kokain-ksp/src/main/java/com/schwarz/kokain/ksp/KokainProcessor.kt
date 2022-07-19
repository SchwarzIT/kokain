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

class KokainProcessor(codeGenerator: CodeGenerator, val logger: KSPLogger) : SymbolProcessor{

    private val shadowBeanGenerator = ShadowBeanGenerator()

    private val factoryGenerator = FactoryGenerator()

    private val codeGenerator = KokainCodeGenerator(codeGenerator)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        var beanModel = resolver.getSymbolsWithAnnotation(EBean::class.qualifiedName!!).mapNotNull { EBeanModelFactory.create(logger, it) }.toList()

        var factory = resolver.getSymbolsWithAnnotation(EFactory::class.qualifiedName!!).firstOrNull()?.let {
            EFactoryModelFactory.create(logger, it)
        }

        beanModel.forEach {
            codeGenerator.generate(shadowBeanGenerator.generateModel(it))
        }

        factory?.let {
            codeGenerator.generate(factoryGenerator.generateModel(it, beanModel))
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