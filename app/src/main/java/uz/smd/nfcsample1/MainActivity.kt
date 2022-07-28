package uz.smd.nfcsample1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import uz.smd.nfcsample1.nfcdemo.MainDemoActivity
import uz.smd.nfcsample1.nfctextbeam.TextToBeamActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.demoSend).setOnClickListener {
            startActivity(Intent(this, MainDemoActivity::class.java))
        }
        findViewById<View>(R.id.sendByNFC).setOnClickListener {
            startActivity(Intent(this, SendNFC::class.java))
        }
        findViewById<View>(R.id.tagData).setOnClickListener {
            startActivity(Intent(this, BeamActivity::class.java))
        }
        findViewById<View>(R.id.sendPlainTextByNFC).setOnClickListener {
            startActivity(Intent(this, TextToBeamActivity::class.java))
        }

    }
}