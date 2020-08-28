package net.qamar.sampledatabindingmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.repository.SampleRepository
import net.qamar.sampledatabindingmvvm.apiNetworking.Resource
import net.qamar.sampledatabindingmvvm.apiNetworking.RetrofitClientInstance
import net.qamar.sampledatabindingmvvm.apiNetworking.interfaceAPI.GetDataService

class SampleViewModel() : ViewModel() {

    private val repository = SampleRepository()
    val name: LiveData<String>
    var showProgress: LiveData<Boolean>
    lateinit var albumList: LiveData<Resource<List<RetroPhoto>>>


    init {
        name = repository.name
        showProgress = repository.showProgress
        albumList = repository.getAllAlbums()
    }

    fun refresh() {
        Log.e("qmr", "refresh")
        showProgress = repository.showProgress
        albumList = repository.getAllAlbums()
    }

    fun changeTextView() {
        repository.onClickTextView()
    }

}