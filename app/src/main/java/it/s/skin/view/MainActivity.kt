package it.s.skin.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import it.s.skin.R
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button_quiz).apply {
            setOnClickListener {
                val intent= Intent(this@MainActivity, QuizActivity::class.java)
                this@MainActivity.startActivity(intent)
            }
        }
        findViewById<LinearLayout>(R.id.scroll_bar_images).apply {
            File("${this@MainActivity.filesDir}").listFiles()?.forEach {
                addView(ImageView(this@MainActivity).apply {
                    setImageURI(it.toUri())

                })
            }

        }
    }


}