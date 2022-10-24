package com.schwarz.kokain.ksp

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
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
                    "internal class FooBean {\n" +
                    "}"
        )

        val compilation = compileKotlin(subEntity)
        Assert.assertEquals(compilation.exitCode, KotlinCompilation.ExitCode.OK)
    }

    @Test
    fun `test kotlin find internal class modifier`() {

        val content = HEADER +
                "@EBean\n" +
                "@EFactory\n" +
                "internal class FooBean {\n" +
                "}"

        val tempFileFooBean = org.jetbrains.kotlin.konan.file.createTempFile("FooBean.kt").apply {
            writeText(content)
        }

        if (tempFileFooBean.exists) {
            val classDeclaration =
                tempFileFooBean.readStrings().find { CLASS_IS_INTERNAL_REG_EX.find(it) != null }
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

    companion object {

        private val CLASS_IS_INTERNAL_REG_EX =
            Regex("\\s*((internal)\\s*(?:data|open|abstract)?\\s*class\\s+)(\\w+)\\s*([({])?.*")

        const val HEADER: String =
            "package com.kaufland.test\n" +
                    "\n" +
                    "import com.schwarz.kokain.api.EBean\n" +
                    "import com.schwarz.kokain.api.EFactory\n"
    }
}
