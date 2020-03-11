package kaufland.com.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.schwarz.kokaindi.inject
//import com.schwarz.kokaindi.inject
//import com.schwarz.kokain.api.viewmodel
import kaufland.com.demo.bean.FooBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity2 : AppCompatActivity() {

    private val mFooBean: FooBean by inject()

//    private val mBarViewModel: BarViewModel by viewmodel(this@MainActivity)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = mFooBean.saysomething()
        btnSwitch.text = "${resources.getString(R.string.switchTo)} ${localClassName}"
        btnSwitch.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        //Log.e("test", test)

    }

    override fun onResume() {
        super.onResume()
        mFooBean.countUp()
        title = mFooBean.saysomething()
    }
}
