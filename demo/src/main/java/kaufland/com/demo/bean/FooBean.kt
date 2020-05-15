package kaufland.com.demo.bean

import android.app.Activity
import android.content.Context
import android.os.Vibrator
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.di.context
import com.schwarz.kokain.di.inject
import com.schwarz.kokain.di.systemService
import kaufland.com.demo.R


@EBean
open class FooBean {

    private val mContext: Context by context()

    private val mFooSingletonyBean : FooSingletonBean by inject()

    private val vibrator : Vibrator? by systemService()

    var value : Int = 1

    fun countUp(){
        this.value = value.plus(1)
    }

    fun saySomething(): String {
        vibrator?.vibrate(100)
        if(!(mContext is Activity)){
            throw RuntimeException("That should not happen in a SingletonBean")
        }

        return "${mContext.getString(R.string.title)} ${value.toString()}"
    }
}
