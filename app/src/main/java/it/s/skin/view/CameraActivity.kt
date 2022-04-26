package it.s.skin.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.net.toFile
import androidx.lifecycle.LifecycleOwner
import it.s.skin.R
import it.s.skin.controllerjunior.TakePictureCallback
import it.s.skin.view.utils.CameraManager
import it.s.skin.view.utils.PermissionUtils
import java.io.File
import java.util.concurrent.Executors


class CameraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)
    }

    override fun onResume() {
        super.onResume()
        if (PermissionUtils.permissionGranted(this, arrayOf(Manifest.permission.CAMERA))) {
            CameraManager.provideCamera(this, this::bindPreview)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE)

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                var checkPermissions = true
                permissions.forEachIndexed { i, _ ->
                    checkPermissions = grantResults[i] == PackageManager.PERMISSION_GRANTED
                }
                if (checkPermissions) {
                    CameraManager.provideCamera(this, this::bindPreview)
                } else {
                    Log.d(
                        "Permissions",
                        "Permissions has been denied , please give me permissionsssssss"
                    )
                    Toast.makeText(this, R.string.permissions_denied, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * This method removes all camera uses and closes the camera
     */
    private fun bindPreview(cameraProvider: ProcessCameraProvider) {

        val previewView = this.findViewById<PreviewView>(R.id.preview_view_camera_analyzer)

        val cameraSelected = CameraSelector.LENS_FACING_FRONT

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(cameraSelected)
            .build()

        val preview = Preview.Builder().build()
        preview.setSurfaceProvider(previewView.surfaceProvider)

        val imageCapture = ImageCapture.Builder()
            .build()

        findViewById<Button>(R.id.take_picture).apply {
            setOnClickListener {
                val outputFileOptions = ImageCapture.OutputFileOptions.Builder(File("${this@CameraActivity.filesDir}${File.separator}${System.currentTimeMillis()}")).build()
                imageCapture.takePicture(outputFileOptions, Executors.newSingleThreadExecutor(), TakePictureCallback(this@CameraActivity))
            }
        }


        cameraProvider.unbindAll()

        cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview, imageCapture)
    }

    companion object {
        private const val REQUEST_CODE = 1
    }
}