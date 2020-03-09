package com.schwarz.kokain.processor.util

import com.schwarz.kokain.processor.CoachBaseBinderProcessor
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.junit.Assert
import org.junit.Test

class CouchbaseBaseBinderProcessorKotlinTest {


    @Test
    fun testKotlinAbstractGeneration() {

        val subEntity = SourceFile.kotlin("Sub.kt",
                ENTITY_HEADER +
                        "@Entity\n" +
                        "@Fields(\n" +
                        "Field(name = \"test\", type = String::class),\n" +
                        "Field(name = \"type\", type = String::class, defaultValue = Sub.TYPE, readonly = true)\n" +
                        ")\n" +
                        "abstract class Sub {\n" +
                        "\n" +
                        " companion object {\n" +
                        "        const val TYPE: String = \"DWG\"" +
                        "}\n" +
                        " abstract var test : String?\n" +
                        "}")


        val compilation = compileKotlin(subEntity)


        Assert.assertEquals(compilation.exitCode, KotlinCompilation.ExitCode.OK)
    }

    @Test
    fun testKotlinAbstractGenerationWithLongFields() {

        val subEntity = SourceFile.kotlin("Sub.kt",
                ENTITY_HEADER +
                        "@Entity\n" +
                        "@Fields(\n" +
                        "Field(name = \"test_test_test\", type = String::class),\n" +
                        "Field(name = \"type\", type = String::class, defaultValue = Sub.TYPE, readonly = true)\n" +
                        ")\n" +
                        "abstract class Sub {\n" +
                        "\n" +
                        " companion object {\n" +
                        "        const val TYPE: String = \"DWG\"" +
                        "}\n" +
                        " abstract var testTestTest : String?\n" +
                        "}")


        val compilation = compileKotlin(subEntity)


        Assert.assertEquals(compilation.exitCode, KotlinCompilation.ExitCode.OK)
    }

    @Test
    fun testKotlinPrivateGeneration() {

        val subEntity = SourceFile.kotlin("Sub.kt",
                ENTITY_HEADER +
                        "@Entity\n" +
                        "@Fields(\n" +
                        "Field(name = \"test\", type = String::class),\n" +
                        "Field(name = \"type\", type = String::class, defaultValue = Sub.TYPE, readonly = true)\n" +
                        ")\n" +
                        "class Sub {\n" +
                        "\n" +
                        " companion object {\n" +
                        "        const val TYPE: String = \"DWG\"" +
                        "}\n" +
                        "}")


        val compilation = compileKotlin(subEntity)


        Assert.assertEquals(compilation.exitCode, KotlinCompilation.ExitCode.COMPILATION_ERROR)
        Assert.assertTrue(compilation.messages.contains("Entity can not be final"))
    }

    @Test
    fun testKotlinConstructorFailGeneration() {

        val subEntity = SourceFile.kotlin("Sub.kt",
                ENTITY_HEADER +
                        "@Entity\n" +
                        "@Fields(\n" +
                        "Field(name = \"test\", type = String::class),\n" +
                        "Field(name = \"type\", type = String::class, defaultValue = Sub.TYPE, readonly = true)\n" +
                        ")\n" +
                        "open class Sub(a : String) {\n" +
                        "\n" +
                        " companion object {\n" +
                        "        const val TYPE: String = \"DWG\"" +
                        "}\n" +
                        "}")


        val compilation = compileKotlin(subEntity)


        Assert.assertEquals(compilation.exitCode, KotlinCompilation.ExitCode.COMPILATION_ERROR)
        Assert.assertTrue(compilation.messages.contains("Entity should not have a contructor"))
    }

    private fun compileKotlin(vararg sourceFiles: SourceFile): KotlinCompilation.Result {
        return KotlinCompilation().apply {
            sources = sourceFiles.toList()

            // pass your own instance of an annotation processor
            annotationProcessors = listOf(CoachBaseBinderProcessor())

            inheritClassPath = true
            messageOutputStream = System.out // see diagnostics in real time
        }.compile()
    }

    companion object {
        const val ENTITY_HEADER: String =
                "package com.kaufland.testModels\n" +
                        "\n" +
                        "import kaufland.com.coachbasebinderapi.Entity\n" +
                        "import kaufland.com.coachbasebinderapi.Field\n" +
                        "import kaufland.com.coachbasebinderapi.Fields\n"
    }

}
