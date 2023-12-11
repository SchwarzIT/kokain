package com.schwarz.kokain.ksp

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

    private fun compileKotlin(vararg sourceFiles: SourceFile): KotlinCompilation.Result {
        return KotlinCompilation().apply {
            sources = sourceFiles.toMutableList()
            symbolProcessorProviders = listOf(KokainProcessorProvider())
            jvmTarget = "17"

            inheritClassPath = true
            messageOutputStream = System.out // see diagnostics in real time
        }.compile()
    }
}
