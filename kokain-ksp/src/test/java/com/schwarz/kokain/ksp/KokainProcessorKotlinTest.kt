package com.schwarz.kokain.ksp

import TestDataHelper
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.junit.Assert
import org.junit.Test

class KokainProcessorKotlinTest {

    @Test
    fun testKotlinBasicGeneration() {
        val factory = TestDataHelper.clazzAsJavaFileObjects("DummyFactory")
        val beanToTest = TestDataHelper.clazzAsJavaFileObjects("SimpleInternalBean")
        val compilation = compileKotlin(factory, beanToTest)
        Assert.assertEquals(KotlinCompilation.ExitCode.OK, compilation.exitCode)
    }

    @Test
    fun testKotlinInternalBasicGeneration() {

        val factory = TestDataHelper.clazzAsJavaFileObjects("DummyFactory")
        val beanToTest = TestDataHelper.clazzAsJavaFileObjects("SimpleInternalBean")
        val compilation = compileKotlin(factory, beanToTest)
        Assert.assertEquals(KotlinCompilation.ExitCode.OK, compilation.exitCode)
    }

    @Test
    fun `test kotlin find internal class modifier`() {

        val simpleInternalBeanFile = TestDataHelper.getFileByClassName("SimpleInternalBean")
        if (simpleInternalBeanFile?.exists() == true) {
            val classDeclaration =
                simpleInternalBeanFile.readLines()
                    .find { CLASS_IS_INTERNAL_REG_EX.find(it) != null }
            Assert.assertTrue(classDeclaration != null)
        }
    }

    private fun compileKotlin(vararg sourceFiles: SourceFile): KotlinCompilation.Result {
        return KotlinCompilation().apply {
            sources = sourceFiles.toMutableList()
            symbolProcessorProviders = listOf(KokainProcessorProvider())
            inheritClassPath = true
            messageOutputStream = System.out // see diagnostics in real time
        }.compile()
    }
}

private val CLASS_IS_INTERNAL_REG_EX =
    Regex("\\s*((internal)\\s*(?:data|open|abstract)?\\s*class\\s+)(\\w+)\\s*([({])?.*")
