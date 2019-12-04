package kaufland.com.demo

import android.app.Application
import com.schwarz.kokaindi.Kokain
import com.schwarz.kokaindi.KokainInstance

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        com.schwarz.kokaindi.KokainInstance.start(com.schwarz.kokaindi.Kokain.create(GeneratedFactory()))
    }
}