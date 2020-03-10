package kaufland.com.demo.bean

import android.content.Context
import com.schwarz.kokain.api.EBean
import com.schwarz.kokaindi.context
import com.schwarz.kokaindi.inject
import kaufland.com.demo.R


@EBean
open class FooBean {

    private val mContext: Context by context()

    private val mFooActivityBean : FooActivityBean by inject()

    var value : Int = 1

    fun countUp(){
        this.value = value.plus(1)
    }

    fun saysomething(): String {
        return "${mContext.getString(R.string.title)} ${value.toString()}"
    }
}
