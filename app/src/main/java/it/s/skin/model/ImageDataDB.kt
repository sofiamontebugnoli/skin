package it.s.skin.model
import com.google.gson.annotations.Expose
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.util.ArrayList

class ImageDataDB : Serializable{
    @Expose
    var imageDataList: MutableList<ImageData> = ArrayList()

    fun moveToPosition(img : ImageData, to : Int) {
        if(to < 0) throw IllegalArgumentException("Error: to[$to] can not be negative")
        if(to > this.imageDataList.size-1) throw IllegalArgumentException("Error: to[$to] can not be greater than max size")

        if(img.position != to) {
            if(img.position < to) {
                for (i in this.imageDataList) {
                    if(i.position > img.position && i.position <= to) {
                        i.position -= 1
                    }
                }
            } else {
                for (i in this.imageDataList) {
                    if(i.position >= to && i.position < img.position) {
                        i.position += 1
                    }
                }
            }

            img.position = to
        }
    }
}