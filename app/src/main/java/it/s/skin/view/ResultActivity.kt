package it.s.skin.view

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toFile
import it.s.skin.R
import it.s.skin.controllerjunior.ImageAnalyzer
import org.tensorflow.lite.support.label.Category
import java.io.File
import java.util.concurrent.Executors

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.result_activity)

        val uri = this.intent.getParcelableExtra<Uri>("URI")
        //Log.d("URI r", "$uri")

        ImageAnalyzer(this, BitmapFactory.decodeStream(uri?.toFile()?.inputStream())).apply {
            onSuccessAnalyze += {
                this@ResultActivity.runOnUiThread {
                    findViewById<TextView>(R.id.textResult).apply {
                        text = it.maxByOrNull { category -> category.score }!!.label
                    }
                }
            }
        }.startAnalyze()

        findViewById<ConstraintLayout>(R.id.resultImageLayout).apply {
            addView(ImageView(this@ResultActivity).apply {
                setImageURI(uri)

            })
        }
    }
}