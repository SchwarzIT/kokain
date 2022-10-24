package kaufland.com.demo.bean

import android.app.Application
import android.content.Context
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.di.context

@EBean
internal class FooInternalBean {

    val context: Context by context()

    val startTime = System.currentTimeMillis()

    fun calculateRunningTime(): Long {
        if (!(context is Application)) {
            throw RuntimeException("That should not happen in a SingletonBean")
        }

        return System.currentTimeMillis().minus(startTime)
    }
}
