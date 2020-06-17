package kaufland.com.demo

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.schwarz.kokain.di.inject
import kaufland.com.demo.bean.FooBean
import java.util.jar.Attributes

class CustomView(context: Context, attributes: AttributeSet?) : AppCompatTextView(context, attributes){

    private val fooBean : FooBean by inject()

    init {
        setBackgroundColor(Color.BLUE)
    }


    fun doTest(){
        text = fooBean.saySomething()
    }
}