package net.qamar.sampledatabindingmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.repository.SampleRepository

class SampleViewModel() : ViewModel() {

    private val repository = SampleRepository()
    val name: LiveData<String>
    val showProgress: LiveData<Boolean>
    lateinit var albumList: LiveData<ArrayList<RetroPhoto>>

    init {
        name = repository.name
        showProgress = repository.showProgress
        albumList = repository.getAllAlbums()

    }

    fun changeTextView() {
        repository.onClickTextView()
    }

}