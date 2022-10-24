package com.schwarz.kokain.processor.util

import com.schwarz.kokain.processor.KokainProcessor
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.junit.Assert
import org.junit.Test

class KokainProcessorKotlinTest {

    @Test
    fun testKotlinBasicGeneration() {

        val subEntity = SourceFile.kotlin(
            "FooBean.kt",
            HEADER +
                    "@EBean\n" +
                    "@EFactory\n" +
                    "open class FooBean {\n" +
                    "}"
        )

        val compilation = compileKotlin(subEntity)

        Assert.assertEquals(compilation.exitCode, KotlinCompilation.ExitCode.OK)
    }

    @Test
    fun testKotlinInternalBasicGeneration() {

        val subEntity = SourceFile.kotlin(
            "FooBean.kt",
            HEADER +
                    "@EBean\n" +
                    "@EFactory\n" +
                    "open internal class FooBean {\n" +
                    "}"
        )

        val compilation = compileKotlin(subEntity)

        Assert.assertEquals(compilation.exitCode, KotlinCompilation.ExitCode.OK)
    }

    private fun compileKotlin(vararg sourceFiles: SourceFile): KotlinCompilation.Result {
        return KotlinCompilation().apply {
            sources = sourceFiles.toMutableList()

            // pass your own instance of an annotation processor
            annotationProcessors = listOf(KokainProcessor())

            inheritClassPath = true
            messageOutputStream = System.out // see diagnostics in real time
        }.compile()
    }

    companion object {
        const val HEADER: String =
            "package com.kaufland.test\n" +
                    "\n" +
                    "import com.schwarz.kokain.api.EBean\n" +
                    "import com.schwarz.kokain.api.EFactory\n"
    }
}
