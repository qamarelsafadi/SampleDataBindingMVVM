package net.qamar.sampledatabindingmvvm.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.qamar.sampledatabindingmvvm.apiNetworking.RetrofitClientInstance
import net.qamar.sampledatabindingmvvm.apiNetworking.interfaceAPI.GetDataService
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SampleRepository() {

    val name = MutableLiveData<String>()
    val showProgress = MutableLiveData<Boolean>()
    val albumList = MutableLiveData<ArrayList<RetroPhoto>>()


    init {
        name.value = "Jacob"
        showProgress.value = true
    }


    fun onClickTextView() {
        showProgress.value = false
        name.value = "Qamar"
    }

    fun getAllAlbums(): LiveData<ArrayList<RetroPhoto>> {
        showProgress.value = true
        RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
            .getAllPhotos()
            .enqueue(object : Callback<List<RetroPhoto>> {
                override fun onFailure(call: Call<List<RetroPhoto>>, t: Throwable) {
                    showProgress.value = false
                    Log.e("qmrFailure", t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<List<RetroPhoto>>,
                    response: Response<List<RetroPhoto>>
                ) {
                    showProgress.value = false

                    albumList.value = response.body() as ArrayList<RetroPhoto>?
                }
            })

        return albumList

    }


}