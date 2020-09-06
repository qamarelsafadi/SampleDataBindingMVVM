package net.qamar.sampledatabindingmvvm.repository

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.add_dialog.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.qamar.sampledatabindingmvvm.R
import net.qamar.sampledatabindingmvvm.apiNetworking.Resource
import net.qamar.sampledatabindingmvvm.apiNetworking.RetrofitClientInstance
import net.qamar.sampledatabindingmvvm.apiNetworking.interfaceAPI.GetDataService
import net.qamar.sampledatabindingmvvm.databinding.ActivityDetailsBinding.inflate
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SampleRepository() {

    val name = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val url = MutableLiveData<String>()
    val thumUrl = MutableLiveData<String>()
    val tosatMsg = MutableLiveData<Event<String>>()
    val showProgress = MutableLiveData<Boolean>()
    val color = MutableLiveData<Int>()
    lateinit var resultLiveData: MutableLiveData<Resource<RetroPhoto>>

    val data = MutableLiveData<RetroPhoto>()


    init {
        name.value = "Qamar A. Safadi"
        showProgress.value = true
        color.value = Color.BLUE
        tosatMsg.value = Event("")

    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun showDialog(view: View) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(view.context)
        dialog.setTitle("Add new Photo")
        val v: View = LayoutInflater.from(view.context).inflate(R.layout.add_dialog, null)
        dialog.setView(v)
        val addDialog = dialog.create()
        v.btnAdd.setOnClickListener {

            title.value = v.etTitle.text.toString()
            url.value = v.etUrl.text.toString()
            thumUrl.value = v.etThumUrl.text.toString()

            addPhoto()
            addDialog.dismiss()
        }

        addDialog.show()


    }


    private fun addPhoto(): Boolean {
        var done = false
        RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
            .addPhoto(1, title.value!!, url.value!!, thumUrl.value!!)
            .enqueue(object : Callback<RetroPhoto> {


                override fun onFailure(call: Call<RetroPhoto>, t: Throwable) {
                    Log.e("qmrFail", t.message!!)
                    done = false
                    tosatMsg.value = Event("Failure")

                }

                override fun onResponse(call: Call<RetroPhoto>, response: Response<RetroPhoto>) {
                    done = true
                    tosatMsg.value =
                        Event("photo with ${response.body()!!.id} is added successfully")

                }
            })

        return done


    }

//    fun getAllAlbums() = liveData(Dispatchers.IO) {
//        emit(Resource.loading())
//        val api = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
//        val response = api.getAllPhotos()
//        if (response.isSuccessful) {
//            emit(Resource.success(response.body()))
//        } else {
//            emit(Resource.error(response.body().toString()))
//
//        }
//    }

    fun dataDetails(dataDetails: RetroPhoto): MutableLiveData<RetroPhoto> {

        data.value = dataDetails

        return data
    }

}