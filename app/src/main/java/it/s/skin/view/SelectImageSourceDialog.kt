package it.s.skin.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import it.s.skin.R

class SelectImageSourceDialog : DialogFragment() {

    var onSingleChoiceItemListener : (Input) -> Unit  = {}
    var onCreateDialog : () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.onCreateDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.activity)

        builder.setTitle(R.string.ask_image_input_title)
        val inputPossibilities = arrayOf(R.string.ask_image_input_source_camera, R.string.ask_image_input_source_storage)
        builder.setSingleChoiceItems(inputPossibilities.map { input -> this.resources.getString(input) }.toTypedArray(),-1) {_, pos ->
            for(input in Input.values()) {
                if (input.displayedName == inputPossibilities[pos]) {
                    this.dismiss()
                    this.onSingleChoiceItemListener(input)
                }
            }
        }
        builder.setNeutralButton(R.string.ask_image_input_button_neutral) { _, _ ->}

        return builder.create()
    }

    enum class Input(val displayedName : Int) {
        CAMERA(R.string.ask_image_input_source_camera),STORAGE(R.string.ask_image_input_source_storage)
    }
}