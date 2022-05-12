package it.s.skin.view.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.s.skin.R
import it.s.skin.model.ImageDataDB
import java.io.File


class ScreenSlidePagerAdapter(private val ctx: Context, private val imagesDB: ImageDataDB, private val handlerDelete: (Int)->Any) :
    RecyclerView.Adapter<ScreenSlidePagerAdapter.ViewHolder>() {
    // Array of images
    // Adding images from drawable folder

    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.fragment_blank, parent, false)

        return ViewHolder(view)
    }

    // This method binds the screen with the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        val bitmap =
            BitmapFactory.decodeFile("${this.ctx.filesDir.path}${File.separator}${this.imagesDB.imageDataList[position].filename}")
        holder.images.setImageBitmap(bitmap)
        holder.textViewTitle.apply {
            text = imagesDB.imageDataList[position].title
        }
        holder.textViewBody.apply {
            text = imagesDB.imageDataList[position].label
        }
        holder.dataView.apply {
            text = imagesDB.imageDataList[position].uploadDate
        }
        holder.deleteButton.apply{
            setOnClickListener{
                handlerDelete(position)
                notifyItemRemoved(position)

            }
        }
    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return this.imagesDB.imageDataList.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val images: ImageView = itemView.findViewById(R.id.imageView_analysis)
        val textViewTitle: TextView = itemView.findViewById(R.id.textView_title)
        val textViewBody: TextView = itemView.findViewById(R.id.textView_diagnosis)
        val dataView: TextView = itemView.findViewById(R.id.textView_date)
        val deleteButton : ImageButton = itemView.findViewById(R.id.imageButton_delete)
    }
}
