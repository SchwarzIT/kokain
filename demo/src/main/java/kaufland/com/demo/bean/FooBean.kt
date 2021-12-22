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
open class FooBean : FooBeanInterface {

    private val mContext: Context by context()

    private val mFooSingletonyBean: FooSingletonBean by inject()

    private val vibrator: Vibrator? by systemService()

    var value: Int = 1

    fun countUp() {
        this.value = value.plus(1)
    }

    override fun saySomething(): String {
        vibrator?.vibrate(100)
        if (!(mContext is Activity)) {
            throw RuntimeException("That should not happen in a non SingletonBean which is ui related")
        }

        return "${mContext.getString(R.string.title)} $value"
    }
}
