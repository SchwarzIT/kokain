package kaufland.com.demo

import com.schwarz.kokain.api.KDiFactory
import kotlin.reflect.KClass

class AdditionalFactory :KDiFactory{
    override fun createInstance(clazz: KClass<*>): Any? {
        return null
    }

}