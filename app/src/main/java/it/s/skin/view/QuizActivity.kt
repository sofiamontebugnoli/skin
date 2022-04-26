package it.s.skin.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import it.s.skin.R

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)

        findViewById<Button>(R.id.button_camera).apply {
            setOnClickListener {
                SelectImageSourceDialog().apply {
                    onSingleChoiceItemListener = {
                        when(it){
                            SelectImageSourceDialog.Input.CAMERA->{
                                val intent=Intent(this@QuizActivity,CameraActivity::class.java)
                                this.startActivity(intent)
                                this@QuizActivity.finish()
                            }
                            SelectImageSourceDialog.Input.STORAGE->{
                                Intent(this@QuizActivity,StorageActivity::class.java).also { i ->
                                    this@QuizActivity.startActivity(i)
                                }
                            }
                        }
                    }
                }.show(this@QuizActivity.supportFragmentManager,"Ask import source")
            }
        }


    }
}