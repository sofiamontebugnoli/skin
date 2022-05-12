package it.s.skin.view

import ZoomOutPageTransformer
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.GsonBuilder
import it.s.skin.R
import it.s.skin.model.ImageData
import it.s.skin.model.ImageDataDB
import it.s.skin.view.utils.ScreenSlidePagerAdapter
import it.s.skin.view.utils.WarningDeleteDialog
import java.io.*
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewPager: ViewPager2
    private var imagesData = ImageDataDB()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button_quiz).apply {
            setOnClickListener {
                val intent = Intent(this@MainActivity, QuizActivity::class.java)
                this@MainActivity.startActivity(intent)
            }
        }

        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        try {
            BufferedReader(
                FileReader(
                    this.filesDir.path + File.separator + this.resources.getString(
                        R.string.default_image_db_name
                    )
                )
            ).use {
                this.imagesData = gson.fromJson(it, ImageDataDB::class.java)
            }
        } catch (e: FileNotFoundException) {
            Log.e("ERROR_IO", "Error while reading db image file")
            this.imagesData = ImageDataDB()
        }

        this.imagesData.imageDataList.sortBy { it.position }
        this.intent.getStringExtra("filename")?.let { filen ->
            this.intent.getStringExtra("diagnosis_result")?.let { diagnosis ->
                this.intent.getStringExtra("diagnosis_title")?.let { titleResult ->
                    this.buildNewImageData().apply {
                        filename = filen
                        label = diagnosis
                        title = titleResult
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            uploadDate = LocalDate.now().toString()
                        }
                    }
                }
                this.storeDB()
            }
        }


        this.mainViewPager = findViewById(R.id.mainViewPager)

        var updateAdapter = {}

        val handlerDelete: (Int) -> Any = {
            val warningDeleteDialog = WarningDeleteDialog().apply {
                positiveListener = DialogInterface.OnClickListener { _, _ ->
                    if (this@MainActivity.imagesData.imageDataList.isNotEmpty()) {
                        this@MainActivity.mainViewPager.adapter = null
                        File(this@MainActivity.imagesData.imageDataList[it].filename!!).delete()
                        this@MainActivity.imagesData.imageDataList.removeAt(it)
                        this@MainActivity.storeDB()
                        updateAdapter()
                    }
                }
            }
            warningDeleteDialog.show(
                this.supportFragmentManager,
                "DeleteWarning"
            )

        }


        val pagerAdapter = ScreenSlidePagerAdapter(
            this,
            this.imagesData,
            handlerDelete
        ) //change id imageView and imageLayout with R.id.
        updateAdapter = {
            this.mainViewPager.adapter = pagerAdapter
        }
        this.mainViewPager.adapter = pagerAdapter


        mainViewPager.setPageTransformer(ZoomOutPageTransformer())

        this.intent.getStringExtra("filename")?.let {
            this.intent.getStringExtra("diagnosis_result")?.let {
                this.intent.getStringExtra("diagnosis_title")?.let {
                    this.mainViewPager.currentItem = imagesData.imageDataList.last().position
                }
            }
        }

    }

    private fun buildNewImageData(): ImageData {

        val result = ImageData().apply {
            //filename = intent.getStringExtra("filename")
            position = this@MainActivity.imagesData.imageDataList.size
            val imageView = ImageView(this@MainActivity).apply {
                setImageResource(R.drawable.ic_baseline_image_not_supported_24)
            }
            //label= intent.getStringExtra("diagnosis_result")!!
            view = imageView
        }

        this.imagesData.imageDataList += result
        return result
    }

    private fun storeDB() {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

        try {
            PrintWriter(this.filesDir.path + File.separator + this.resources.getString(R.string.default_image_db_name)).use {
                it.println(gson.toJson(this.imagesData))
            }

        } catch (e: IOException) {
            Log.e("ERROR_IO", "Error while saving db file")
        }
    }


}



