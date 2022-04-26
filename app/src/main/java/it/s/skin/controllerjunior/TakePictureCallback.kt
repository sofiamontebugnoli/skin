package it.s.skin.controllerjunior

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import it.s.skin.view.ResultActivity
import it.s.skin.view.utils.CameraManager

class TakePictureCallback(private val context:AppCompatActivity) : ImageCapture.OnImageSavedCallback {
    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
        outputFileResults.savedUri?.let {
            Intent(this.context, ResultActivity::class.java).apply {
                putExtra("URI", it)
            }.also {
                //CameraManager.undoCamera(context)
                this.context.runOnUiThread{
                    CameraManager.undoCamera(context)
                    this.context.startActivity(it)
                    this.context.finish()
                }

            }
        }
    }

    override fun onError(exception: ImageCaptureException) {
        TODO("Not yet implemented")
    }

}