package net.qamar.sampledatabindingmvvm.util

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso


@BindingAdapter("textChangeColor")
fun textChangeColor(textTitle: TextView, id: Int) {
    if (id % 2 == 0) {
        textTitle.setTextColor(Color.RED)
    } else {
        textTitle.setTextColor(Color.BLACK)
    }
}

@BindingAdapter("changeVisibility")
fun changeVisibility(view: ProgressBar, isVisible: LiveData<Boolean>) {
    if (isVisible.value!!)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE


}
@BindingAdapter("imageUrl")
fun imageUrl(view: ImageView, poserPath: String) {
    Picasso.get().load(poserPath).into(view)
}



