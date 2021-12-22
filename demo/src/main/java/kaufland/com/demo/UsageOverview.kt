package kaufland.com.demo

import android.content.Context
import android.view.LayoutInflater
import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.di.context
import com.schwarz.kokain.di.get
import com.schwarz.kokain.di.inject
import com.schwarz.kokain.di.systemService
import kaufland.com.demo.bean.FooBean
import kaufland.com.demo.bean.FooBeanInterface
import kaufland.com.demo.bean.FooSingletonBean

@EBean
open class UsageOverview {

    // no difference where the component comes from always use "by inject()"
    private val mFooBean: FooBean by inject()

    private val mSingletonBean: FooSingletonBean by inject()

    private val mClassFromAnotherLibrary: ClassFromAnotherLibrary by inject()

    // inject as Interface
    private val mFooBeanInterface: FooBeanInterface by inject(FooBean::class)

    // inject all sorts systemservices
    private val layoutInflater: LayoutInflater? by systemService()

    // inject context (kokain injects activity context if it's save to do so otherwise it injects application context)
    private val context: Context by context()

    private fun doSomething() {
        val bean = get<FooBean>()
        bean.saySomething()
    }
}
