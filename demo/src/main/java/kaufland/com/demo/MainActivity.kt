package kaufland.com.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.schwarz.kokain.di.inject
import kaufland.com.demo.bean.FooBean
import kaufland.com.demo.bean.FooBeanInterface
import kaufland.com.demo.bean.FooSingletonBean
import kotlinx.android.synthetic.main.activity_main.btnSwitch
import kotlinx.android.synthetic.main.activity_main.customView
import kotlinx.android.synthetic.main.activity_main.runningSinceLbl

class MainActivity : AppCompatActivity() {

    private val mFooBean: FooBean by inject()

    private val mFooBeanInterface: FooBeanInterface by inject(FooBean::class)

    private val mSingletonBean: FooSingletonBean by inject()

    private val mClassFromAnotherLibrary: ClassFromAnotherLibrary by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mClassFromAnotherLibrary.doSomething()
        btnSwitch.text = "${resources.getString(R.string.switchTo)} $localClassName"
        btnSwitch.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }

        title = mFooBeanInterface.saySomething()
    }

    override fun onResume() {
        super.onResume()
        runningSinceLbl.text = "Running for ${mSingletonBean.calculateRunningTime()}"
        mFooBean.countUp()
        title = mFooBean.saySomething()
        customView.doTest()
    }
}
