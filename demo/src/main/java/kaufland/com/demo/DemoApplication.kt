package kaufland.com.demo

import android.app.Application
import com.schwarz.kokain.api.EFactory
import com.schwarz.kokaindi.Kokain
import com.schwarz.kokaindi.KokainInstance

@EFactory
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KokainInstance.start(Kokain.create(GeneratedFactory(), this))
    }
}