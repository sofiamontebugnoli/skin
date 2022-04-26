package it.s.skin.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toFile
import it.s.skin.R
import it.s.skin.view.utils.PermissionUtils

class StorageActivity : AppCompatActivity() {

    private lateinit var activityStorageResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setForRegisterForActivityResult { }
    }

    override fun onResume() {
        super.onResume()
        if (PermissionUtils.permissionGranted(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            )
        ) {
            this.askForAPhoto()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE
            )

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
                    this.askForAPhoto()
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

    private fun askForAPhoto() {
        var chooseFile = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
        chooseFile =
            Intent.createChooser(chooseFile, this.resources.getString(R.string.choose_from_storage))

        this.activityStorageResultLauncher.launch(chooseFile)
    }

    private fun setForRegisterForActivityResult(onSuccess: () -> Unit) {
        this.activityStorageResultLauncher =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { x ->
                if (x.resultCode == RESULT_OK) {
                    x?.data?.data?.let {
                        Intent(this, ResultActivity::class.java).apply {
                            putExtra("URI", it)
                        }.also {
                            this.startActivity(it)
                            //this.finish()
                        }
                    }
                }
            }
    }


    companion object {
        private const val REQUEST_CODE = 2
    }
}