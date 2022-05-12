package it.s.skin.model

import android.widget.ImageView
import com.google.gson.annotations.Expose
import java.io.Serializable

class ImageData : Serializable{
    @Expose
    var filename: String? = null

    var view: ImageView? = null

    @Expose
    var position = 0

    @Expose
    var label = ""

    @Expose
    var title = ""

    @Expose
    var uploadDate = ""

}