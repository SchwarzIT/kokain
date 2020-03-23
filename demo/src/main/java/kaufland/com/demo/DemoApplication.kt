package kaufland.com.demo

import android.app.Application
import com.schwarz.kokain.api.EFactory
import com.schwarz.kokaindi.CustomCurator
import com.schwarz.kokaindi.Kokain
import com.schwarz.kokaindi.KokainInstance
import kotlin.random.Random
import kotlin.reflect.KClass

@EFactory(additionalFactories = [AdditionalFactory::class])
class DemoApplication : Application(), CustomCurator {

    override fun onCreate() {
        super.onCreate()
        KokainInstance.start(Kokain.create(GeneratedFactory(), this, this))
    }

    override fun <V : Any> getInstance(clazz: KClass<*>): V? {

        return when (clazz) {
            ClassFromAnotherLibrary::class -> ClassFromAnotherLibrary(this)
            else -> null
        } as V?
    }
}