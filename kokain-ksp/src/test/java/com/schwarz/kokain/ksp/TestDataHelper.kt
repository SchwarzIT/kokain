import com.tschuchort.compiletesting.SourceFile
import java.io.File

object TestDataHelper {

    fun clazzAsJavaFileObjects(clazz: String): SourceFile {
        val className = "$clazz.kt"
        return SourceFile.kotlin(className, getFileByClassName(className)?.readText() ?: "")
    }

    fun getFileByClassName(clazzName: String): File? {
        return javaClass.classLoader.getResource(clazzName)?.file?.let { File(it) }
    }
}
