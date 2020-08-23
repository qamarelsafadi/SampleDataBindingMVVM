package net.qamar.sampledatabindingmvvm.util

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("textChangeColor")
fun textChangeColor(textTitle: TextView, id:Int) {
    if(id% 2 == 0){
        textTitle.setTextColor(Color.RED)
    }
    else{
        textTitle.setTextColor(Color.BLACK)
    }
}

