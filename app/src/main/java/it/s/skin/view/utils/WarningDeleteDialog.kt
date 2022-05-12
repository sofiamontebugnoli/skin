package it.s.skin.view.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import it.s.skin.R

class WarningDeleteDialog : DialogFragment() {

    var positiveListener: DialogInterface.OnClickListener =
        DialogInterface.OnClickListener { _: DialogInterface?, _: Int -> }
    private var negativeListener: DialogInterface.OnClickListener =
        DialogInterface.OnClickListener { _: DialogInterface?, _: Int -> }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.activity)
        builder.setTitle(R.string.warning_delete_title)
        builder.setMessage(R.string.warning_delete_message)
        builder.setPositiveButton(R.string.warning_delete_positive_button, this.positiveListener)
        builder.setNegativeButton(R.string.warning_delete_negative_button, this.negativeListener)
        return builder.create()
    }

}