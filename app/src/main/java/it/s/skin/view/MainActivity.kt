package it.s.skin.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import it.s.skin.R

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
    }

}