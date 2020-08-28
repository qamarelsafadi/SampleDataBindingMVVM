package net.qamar.sampledatabindingmvvm.util

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData


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


