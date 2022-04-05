package kaufland.com.demo

import android.app.Application
import com.schwarz.kokain.api.EFactory
import com.schwarz.kokain.coredi.CustomCurator
import com.schwarz.kokain.coredi.KokainInstance
import com.schwarz.kokain.di.Kokain
import com.schwarz.kokain.di.get
import kotlin.reflect.KClass

@EFactory(additionalFactories = [AdditionalFactory::class, com.example.demolibrary.GeneratedFactory::class])
class DemoApplication : Application(), CustomCurator {

    override fun onCreate() {
        super.onCreate()
        KokainInstance.start(Kokain.create(GeneratedFactory(), this, this))
        val test: ClassFromAnotherLibrary? = get()
    }

    override fun <V : Any> getInstance(clazz: KClass<*>): V? {

        return when (clazz) {
            ClassFromAnotherLibrary::class -> ClassFromAnotherLibrary(this)
            else -> null
        } as V?
    }
}
