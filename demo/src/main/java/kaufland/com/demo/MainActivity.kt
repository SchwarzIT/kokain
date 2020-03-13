package kaufland.com.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.schwarz.kokaindi.inject
import kaufland.com.demo.bean.FooBean
import kaufland.com.demo.bean.FooSingletonBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mFooBean: FooBean by inject()

    private val mSingletonBean: FooSingletonBean by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSwitch.text = "${resources.getString(R.string.switchTo)} ${localClassName}"
        btnSwitch.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }


        title = mFooBean.saySomething()
        //Log.e("test", test)

    }

    override fun onResume() {
        super.onResume()
        runningSinceLbl.text = "Running for ${mSingletonBean.calculateRunningTime()}"
        mFooBean.countUp()
        title = mFooBean.saySomething()
    }
}
