package it.s.skin.controllerjunior

import android.content.Context
import android.graphics.Bitmap
import it.s.skin.ml.Model
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.Category
import java.util.concurrent.Executors

class ImageAnalyzer(private val context: Context, private val image: Bitmap) {

    val onSuccessAnalyze : MutableList<(List<Category>)->Any> = ArrayList() //lista di funzioni che restituisce qulasiasi cosa

    fun startAnalyze(){
        Executors.newSingleThreadExecutor().also {

            val model = Model.newInstance(this.context)

            // Creates inputs for reference.
            val image = TensorImage.fromBitmap(this.image)

            // Runs model inference and gets result.
            val outputs = model.process(image)
            val probability = outputs.probabilityAsCategoryList

            // Releases model resources if no longer used.
            model.close()

            this.onSuccessAnalyze.forEach { it(probability) }
        }
    }
}