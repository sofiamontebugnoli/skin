package it.s.skin.view

import android.content.Intent
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

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.result_activity)

        val uri = this.intent.getParcelableExtra<Uri>("URI")

        if (uri != null) {
            val path = uri.toFile().name
            ImageAnalyzer(this, BitmapFactory.decodeStream(uri.toFile().inputStream())).apply {
                onSuccessAnalyze += {
                    this@ResultActivity.runOnUiThread {
                        findViewById<TextView>(R.id.textResult).apply {
                            val level = it.maxByOrNull { category -> category.score }!!.label
                            val result = textFinalAnalysis(level)
                            text = result
                            Intent(this@ResultActivity, MainActivity::class.java).apply {
                                putExtra("diagnosis_title", textResultQuiz())
                                putExtra("diagnosis_result", text)
                                putExtra("filename", path)
                            }.also { i ->
                                this@ResultActivity.startActivity(i)
                                this@ResultActivity.finish()
                            }
                        }
                    }
                }
            }.startAnalyze()

            findViewById<ConstraintLayout>(R.id.resultImageLayout).apply {
                addView(ImageView(this@ResultActivity).apply {
                    setImageURI(uri)
                })
            }
        } else {
            Log.e("err_null_image_uri", "error in getting uri from camera activity")
            Intent(this@ResultActivity, MainActivity::class.java).also { i ->
                this@ResultActivity.startActivity(i)
                this@ResultActivity.finish()
            }
        }
    }
    private fun textResultQuiz():String{
        var result = ""
        findViewById<TextView>(R.id.textResult).apply {
            val txt = intent.getStringExtra("quiz_result")
            result += txt.toString()
        }
        return result
    }
    private fun textFinalAnalysis(level: String): String {
        var answer: Int
        var result = ""
        findViewById<TextView>(R.id.textResult).apply {
            val txt = intent.getStringExtra("quiz_result")
            text = txt
            result += text.toString()
            answer = skinType(text.toString())
        }

        if (level.contains("0")) {
            when (answer) {
                normal -> result += ", pimples and blackheads are small or absent"
                oily -> result += ", even though there are no relevant signs of acne"
                dry -> result += ", there are very small redness or small pimples"
                sensitive -> result += ",there are very small redness, irritations or pimples"
            }
        }
        if (level.contains("1")) {
            when (answer) {
                normal -> result += ", there are some pimples and blackheads"
                oily -> result += ", there are some pimples and blackheads"
                dry -> result += ", there are some redness or pimples"
                sensitive -> result += ", there are some redness, irritations or pimples"
            }
        }
        if (level.contains("2")) {
            when (answer) {
                normal -> result += ", there are many pimples and blackheads"
                oily -> result += ", there are many pimples and blackheads, your acne is severe"
                dry -> result += ", there are many redness and pimples"
                sensitive -> result += ", there are many redness and irritations"
            }
        }
        return result
    }

    private fun skinType(str: String): Int {
        if (str.contains("normal")) {
            return normal
        }
        if (str.contains("oily")) {
            return oily
        }
        if (str.contains("dry")) {
            return dry
        }
        if (str.contains("sensitive")) {
            return sensitive
        }
        return normal
    }

    companion object {
        private const val normal = 0
        private const val oily = 1
        private const val dry = 2
        private const val sensitive = 3
    }
}
