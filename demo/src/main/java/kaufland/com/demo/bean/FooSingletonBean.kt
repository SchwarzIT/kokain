package kaufland.com.demo.bean

import android.app.Application
import android.content.Context
import com.schwarz.kokain.api.EBean
import com.schwarz.kokaindi.context
import java.lang.RuntimeException

import java.util.Timer

@EBean(scope = EBean.Scope.Singleton)
open class FooSingletonBean{

    val context : Context by context()

    val startTime = System.currentTimeMillis()

    fun calculateRunningTime() : Long{
        if(!(context is Application)){
            throw RuntimeException("That should not happen in a SingletonBean")
        }

        return System.currentTimeMillis().minus(startTime)
    }
}
