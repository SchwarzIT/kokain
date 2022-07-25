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

//    @Test
//    fun testKotlinMultiFactoryGeneration() {
//
//        val factory = SourceFile.kotlin(
//            "FooBean.kt",
//            HEADER +
//                    "@EFactory(additionalFactories = [AdditionalFactory::class])\n" +
//                    "open class FooBean {\n" +
//                    "}"
//        )
//
//        val subFactory = SourceFile.kotlin(
//            "AdditionalFactory.kt",
//            HEADER +
//                    "open class AdditionalFactory : KDiFactory {\n" +
//                    "  override fun createInstance(clazz: KClass<*>): Any? {\n" +
//                    "        return null\n" +
//                    "    }" +
//                    "}"
//        )
//
//        val compilation = compileKotlin(factory, subFactory)
//
//        Assert.assertEquals(compilation.exitCode, KotlinCompilation.ExitCode.OK)
//    }

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

            symbolProcessorProviders = listOf(KokainProcessorProvider())

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
