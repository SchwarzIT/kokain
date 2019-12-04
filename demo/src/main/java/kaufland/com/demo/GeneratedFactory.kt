package kaufland.com.demo

import com.schwarz.kokaindi.KDiFactory
import kaufland.com.demo.bean.FooBean
import kaufland.com.demo.bean.GeneratedFooBean
import kotlin.reflect.KClass

class GeneratedFactory : com.schwarz.kokaindi.KDiFactory {


    override fun createLazy(kClass: KClass<*>): Any {
        return when (kClass) {
            FooBean::class -> GeneratedFooBean()
            else -> throw RuntimeException()
        }
    }


}
