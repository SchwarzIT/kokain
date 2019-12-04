package kaufland.com.demo.bean

import android.content.Context
import com.schwarz.kokain.api.EBean
import com.schwarz.kokaindi.context
import kaufland.com.demo.R


@EBean
open class FooBean {

    private val mContext: Context by context()

    fun saysomething(): String {
        return mContext.getString(R.string.title)
    }
}
