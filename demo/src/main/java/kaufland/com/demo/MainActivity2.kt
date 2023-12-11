package kaufland.com.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.schwarz.kokain.di.inject
import kaufland.com.demo.bean.FooBean
import kaufland.com.demo.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {

    private val mFooBean: FooBean by inject()

    private lateinit var binding: ActivityMainBinding

//    private val mBarViewModel: BarViewModel by viewmodel(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = mFooBean.saySomething()
        binding.btnSwitch.text = "${resources.getString(R.string.switchTo)} $localClassName"
        binding.btnSwitch.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        mFooBean.countUp()
        title = mFooBean.saySomething()
    }
}
