package net.qamar.sampledatabindingmvvm.model

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
import net.qamar.sampledatabindingmvvm.view.DetailsActivity


class RetroPhoto(
    @field:SerializedName("albumId") var albumId: Int,
    @field:SerializedName("id") var id: Int,
    @field:SerializedName("title") var title: String,
    @field:SerializedName("url") var url: String,
    @field:SerializedName("thumbnailUrl") var thumbnailUrl: String

){
    fun navigateToNewActivity(view: ConstraintLayout){
        val context: Context = view.context
        val intent = Intent(context, DetailsActivity::class.java)
        context.startActivity(intent)
    }
}
