package net.qamar.sampledatabindingmvvm.viewmodel

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.repository.SampleRepository
import net.qamar.sampledatabindingmvvm.util.Event

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class TaskItemViewModel() : ViewModel() {

     private val repository = SampleRepository()
    var item: LiveData<RetroPhoto>
    var toastMsg: LiveData<Event<String>>
    var albumList: LiveData<ArrayList<RetroPhoto>>? = null


    init {
        toastMsg = repository.tosatMsg
        item = repository.item
        albumList = repository.albumList

    }

    fun getItem(itemFromAdapter:RetroPhoto?){
     item = repository.getItem(itemFromAdapter!!)
    }

    fun deleteItem(view :View , id:Int ,item:RetroPhoto){
        repository.deleteItem(view,id,item)
        toastMsg = repository.tosatMsg

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun showDialog(view:View,item:RetroPhoto){
        repository.showDialog(view,item)
        toastMsg = repository.tosatMsg

    }


}