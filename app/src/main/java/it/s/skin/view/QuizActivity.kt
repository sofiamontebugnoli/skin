package it.s.skin.view

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import it.s.skin.R
import it.s.skin.model.SkinType


class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)


        findViewById<ExtendedFloatingActionButton>(R.id.next_step_button).apply {

            setOnClickListener {
                if (answerAllBool()) {
                    SelectImageSourceDialog().apply {
                        onSingleChoiceItemListener = {
                            when (it) {
                                SelectImageSourceDialog.Input.CAMERA -> {
                                    Intent(this@QuizActivity, CameraActivity::class.java).apply {
                                        putExtra("quiz_result", textFinalAnalysis())
                                    }.also { i ->
                                        this@QuizActivity.startActivity(i)
                                        this@QuizActivity.finish()
                                    }
                                }
                                SelectImageSourceDialog.Input.STORAGE -> {
                                    Intent(this@QuizActivity, StorageActivity::class.java).apply {
                                        putExtra("quiz_result", textFinalAnalysis())
                                    }.also { i ->
                                        this@QuizActivity.startActivity(i)
                                    }
                                }
                            }
                        }
                    }.show(this@QuizActivity.supportFragmentManager, "Ask import source")
                } else {
                    Toast.makeText(this@QuizActivity, R.string.finish_the_quiz, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    private fun answerAllBool(): Boolean {
        var completed = true

        findViewById<LinearLayout>(R.id.linear_quiz).children.forEach {
            if (it is RadioGroup) {
                if (it.checkedRadioButtonId == -1)
                    completed = false
            }
        }
        return completed
    }

    private fun textFinalAnalysis(): String {
        return "${this.resources.getString(R.string.skin_declaration)} ${
            this.resources.getString(
                answers().referenceString
            )
        }"
    }

    private fun answers(): SkinType {
        var completed = true
        findViewById<LinearLayout>(R.id.linear_quiz).children.iterator().forEach {
            if (it is RadioGroup) {
                if (it.checkedRadioButtonId == -1)
                    completed = false
            }
        }
        val skinType = arrayListOf(0, 0, 0, 0)


        if (completed) {
            //normal skin
            if (findViewById<RadioButton>(R.id.radioButton1_2).isChecked) skinType[SkinType.NORMAL.value]++
            if (findViewById<RadioButton>(R.id.radioButton2_2).isChecked) skinType[SkinType.NORMAL.value]++
            if (findViewById<RadioButton>(R.id.radioButton3_2).isChecked) skinType[SkinType.NORMAL.value]++
            if (findViewById<RadioButton>(R.id.radioButton4_2).isChecked) skinType[SkinType.NORMAL.value]++
            if (findViewById<RadioButton>(R.id.radioButton5_2).isChecked) skinType[SkinType.NORMAL.value]++
            if (findViewById<RadioButton>(R.id.radioButton7_2).isChecked) skinType[SkinType.NORMAL.value]++
            if (findViewById<RadioButton>(R.id.radioButton8_1).isChecked) skinType[SkinType.NORMAL.value]++

            //oily
            if (findViewById<RadioButton>(R.id.radioButton1_1).isChecked) skinType[SkinType.OILY.value]++
            if (findViewById<RadioButton>(R.id.radioButton2_1).isChecked) skinType[SkinType.OILY.value]++
            if (findViewById<RadioButton>(R.id.radioButton3_3).isChecked) skinType[SkinType.OILY.value]++
            if (findViewById<RadioButton>(R.id.radioButton4_1).isChecked) skinType[SkinType.OILY.value]++
            if (findViewById<RadioButton>(R.id.radioButton5_1).isChecked) skinType[SkinType.OILY.value]++
            if (findViewById<RadioButton>(R.id.radioButton7_2).isChecked) skinType[SkinType.OILY.value]++
            if (findViewById<RadioButton>(R.id.radioButton8_1).isChecked) skinType[SkinType.OILY.value]++


            //dry
            if (findViewById<RadioButton>(R.id.radioButton1_3).isChecked) skinType[SkinType.DRY.value]++
            if (findViewById<RadioButton>(R.id.radioButton3_1).isChecked) skinType[SkinType.DRY.value]++
            if (findViewById<RadioButton>(R.id.radioButton4_3).isChecked) skinType[SkinType.DRY.value]++
            if (findViewById<RadioButton>(R.id.radioButton5_3).isChecked) skinType[SkinType.DRY.value]++
            if (findViewById<RadioButton>(R.id.radioButton6_1).isChecked) skinType[SkinType.DRY.value]++
            if (findViewById<RadioButton>(R.id.radioButton7_1).isChecked) skinType[SkinType.DRY.value]++
            if (findViewById<RadioButton>(R.id.radioButton8_1).isChecked) skinType[SkinType.DRY.value]++

            //sensitive

            if (findViewById<RadioButton>(R.id.radioButton1_2).isChecked ||
                findViewById<RadioButton>(R.id.radioButton1_3).isChecked
            ) skinType[SkinType.SENSITIVE.value]++
            if (findViewById<RadioButton>(R.id.radioButton2_2).isChecked) skinType[SkinType.SENSITIVE.value]++
            if (findViewById<RadioButton>(R.id.radioButton3_1).isChecked ||
                findViewById<RadioButton>(R.id.radioButton3_2).isChecked
            ) skinType[SkinType.SENSITIVE.value]++
            if (findViewById<RadioButton>(R.id.radioButton4_2).isChecked ||
                findViewById<RadioButton>(R.id.radioButton4_3).isChecked
            ) skinType[SkinType.SENSITIVE.value]++
            if (findViewById<RadioButton>(R.id.radioButton5_2).isChecked ||
                findViewById<RadioButton>(R.id.radioButton5_3).isChecked
            ) skinType[SkinType.SENSITIVE.value]++
            if (findViewById<RadioButton>(R.id.radioButton6_1).isChecked) skinType[SkinType.SENSITIVE.value]++
            if (findViewById<RadioButton>(R.id.radioButton7_1).isChecked) skinType[SkinType.SENSITIVE.value]++

        }
        val maxIdx = skinType.indices.maxByOrNull { skinType[it] } ?: -1
        return SkinType.getByValue(maxIdx)

    }


}