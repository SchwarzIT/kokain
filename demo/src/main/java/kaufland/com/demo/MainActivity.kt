package kaufland.com.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.schwarz.kokain.di.inject
import kaufland.com.demo.bean.FooBean
import kaufland.com.demo.bean.FooBeanInterface
import kaufland.com.demo.bean.FooSingletonBean
import kaufland.com.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mFooBean: FooBean by inject()

    private val mFooBeanInterface: FooBeanInterface by inject(FooBean::class)

    private val mSingletonBean: FooSingletonBean by inject()

    private val mClassFromAnotherLibrary: ClassFromAnotherLibrary by inject()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mClassFromAnotherLibrary.doSomething()
        binding.btnSwitch.text = "${resources.getString(R.string.switchTo)} $localClassName"
        binding.btnSwitch.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }

        title = mFooBeanInterface.saySomething()
        // Log.e("test", test)
    }

    override fun onResume() {
        super.onResume()
        binding.runningSinceLbl.text = "Running for ${mSingletonBean.calculateRunningTime()}"
        mFooBean.countUp()
        title = mFooBean.saySomething()
        binding.customView.doTest()
    }
}
