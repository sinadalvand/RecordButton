package ir.sinadalvand.recordbuttonapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.sinadalvand.recordbutton.RecordButtonListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // button listener
        recordButton.setOnRecordListener(object : RecordButtonListener {

            // after holing until end this will call
            override fun onFinish() {

            }

            // if wanna start Progressing return True else false (some where is useful ex.getting permission)
            override fun onStartProgress(): Boolean {


//                return false //==> make it lock
                return true
            }

            // return progressing percent
            override fun onProgressing(percent: Float?) {

            }

            // this will call if user release button before end on progress
            override fun onCancell() {

            }

        })


        // button front color
        recordButton.setButtonColor(Color.WHITE)

        // button back color
        recordButton.setBackColor(Color.WHITE)

        // button progress color
        recordButton.setProgressColor(Color.parseColor("#FFC107"))

        // button progress percent (0-100) float
        recordButton.setPercent(0f)

        // button progress width pixel
        recordButton.setProgressWidth(5)

        // button finish time
        recordButton.setProgressTime(5000)

    }
}
