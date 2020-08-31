package net.qamar.sampledatabindingmvvm.repository

import android.graphics.Color
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import net.qamar.sampledatabindingmvvm.apiNetworking.RetrofitClientInstance
import net.qamar.sampledatabindingmvvm.apiNetworking.interfaceAPI.GetDataService
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.apiNetworking.Resource
import retrofit2.awaitResponse

class SampleRepository()  {

    val name = MutableLiveData<String>()
    val showProgress = MutableLiveData<Boolean>()
    val albumList = MutableLiveData<ArrayList<RetroPhoto>>()
    val color = MutableLiveData<Int>()

    val data = MutableLiveData<RetroPhoto>()


    init {
        name.value = "Jacob"
        showProgress.value = true
        color.value = Color.BLUE

    }


    fun changeTextColor(view: TextView) {

        showProgress.value
        color.value = Color.BLUE

        view.setTextColor(color.value!!)
    }

    fun onClickTextView() {
        showProgress.value = false
        name.value = "Qamar"



    }

    //    fun getAllAlbums(): LiveData<ArrayList<RetroPhoto>> {
//
//
//        showProgress.value = true
//       val reponse =  RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java).getAllPhotos()
//
//            .enqueue(object : Callback<List<RetroPhoto>> {
//                override fun onFailure(call: Call<List<RetroPhoto>>, t: Throwable) {
//                    showProgress.value = false
//                    Log.e("qmrFailure", t.localizedMessage)
//                }
//
//                override fun onResponse(
//                    call: Call<List<RetroPhoto>>,
//                    response: Response<List<RetroPhoto>>
//                ) {
//                    showProgress.value = false
//
//                    albumList.value = response.body() as ArrayList<RetroPhoto>?
//                }
//            })
//
//        return albumList
//
//    }


    fun getAllAlbums() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val response = api.getAllPhotos()
        if (response.isSuccessful) {
            emit(Resource.success(response.body()))
        }
        else{
            emit(Resource.error(response.body().toString()))

        }
    }

    fun dataDetails(dataDetails:RetroPhoto) : MutableLiveData<RetroPhoto> {

        data.value = dataDetails

        return data
    }

}