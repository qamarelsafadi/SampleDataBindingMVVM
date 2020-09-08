package net.qamar.sampledatabindingmvvm.repository

import android.app.AlertDialog
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.paperdb.Paper
import kotlinx.android.synthetic.main.add_dialog.view.*
import net.qamar.sampledatabindingmvvm.R
import net.qamar.sampledatabindingmvvm.apinetworking.RetrofitClientInstance
import net.qamar.sampledatabindingmvvm.apinetworking.interfaceAPI.GetDataService
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.util.Event
import net.qamar.sampledatabindingmvvm.util.MyApplication
import net.qamar.sampledatabindingmvvm.util.ToolsUtil
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class SampleRepository {

    val name = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val id = MutableLiveData<Int>()
    private val url = MutableLiveData<String>()
    private val thumUrl = MutableLiveData<String>()
    val toastMsg = MutableLiveData<Event<String>>()
    val showProgress = MutableLiveData<Boolean>()
    private val color = MutableLiveData<Int>()
    val item = MutableLiveData<RetroPhoto>()
    val data = MutableLiveData<RetroPhoto>()
    val albumList = MutableLiveData<ArrayList<RetroPhoto>>()
    private val list: ArrayList<RetroPhoto> = Paper.book().read("list", ArrayList())

    init {
        name.value = "Qamar A. Safadi"
        showProgress.value = true
        color.value = Color.BLUE
        toastMsg.value = Event("")

    }

    fun getItem(viewModelItem: RetroPhoto): LiveData<RetroPhoto> {
        item.value = viewModelItem

        return item


    }

    fun deleteItem(view: View, itemId: Int,deletedItem: RetroPhoto) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(view.context)
        dialog.setTitle("Delete")
        dialog.setCancelable(false)
        dialog.setMessage("Are you sure you want to delete this album?")

        dialog.setPositiveButton(
            "Yes"
        ) { _, _ ->

            item.value = deletedItem
            id.value = itemId
            deletePhoto()
        }

        dialog.setNegativeButton("No") { _, _ ->

        }

        dialog.show()

    }


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

    fun showDialog(view: View, item: RetroPhoto) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(view.context)
        dialog.setTitle("Edit Photo")
        val v: View = LayoutInflater.from(view.context).inflate(R.layout.add_dialog, null)
        dialog.setView(v)
        v.etTitle.setText(item.title)
        v.etUrl.setText(item.url)
        v.etThumUrl.setText(item.thumbnailUrl)
        val addDialog = dialog.create()
        v.btnAdd.setOnClickListener {
            id.value = item.id

            title.value = v.etTitle.text.toString()
            url.value = v.etUrl.text.toString()
            thumUrl.value = v.etThumUrl.text.toString()

            editPhoto()
            albumList.value = list

            addDialog.dismiss()
        }

        addDialog.show()


    }

    private fun addPhoto() {

        if (ToolsUtil.isNetworkConnected(MyApplication.context)) {
            RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
                .addPhoto(1, title.value!!, url.value!!, thumUrl.value!!)
                .enqueue(object : Callback<RetroPhoto> {


                    override fun onFailure(call: Call<RetroPhoto>, t: Throwable) {
                        Log.e("qmrFail", t.message!!)
                        toastMsg.value = Event("Failure")

                    }

                    override fun onResponse(
                        call: Call<RetroPhoto>,
                        response: Response<RetroPhoto>
                    ) {

                        if (response.code() == 201 || response.code() == 200)
                            toastMsg.value =
                                Event("photo with ${response.body()!!.id} is added successfully")

                    }
                })

        } else {
            Paper.book().write("id", Paper.book().read("id", 5000) + 1)

            val retroPhoto = RetroPhoto(
                Paper.book().read("id", 5000),
                title.value!!,
                url.value!!,
                thumUrl.value!!
            )
            list.add(retroPhoto)
            toastMsg.value = Event("photo with ${retroPhoto.id} is added successfully")

            list.reverse()

            albumList.value = list
            Paper.book().write("list", list as List<RetroPhoto>)


        }


    }

    private fun editPhoto() {
        if (ToolsUtil.isNetworkConnected(MyApplication.context)) {

            RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
                .editPhoto(id.value!!, 1, title.value!!, url.value!!, thumUrl.value!!)
                .enqueue(object : Callback<RetroPhoto> {


                    override fun onFailure(call: Call<RetroPhoto>, t: Throwable) {
                        Log.e("qmrFail", t.message!!)
                        toastMsg.value = Event("Failure")
                    }

                    override fun onResponse(
                        call: Call<RetroPhoto>,
                        response: Response<RetroPhoto>
                    ) {
                        toastMsg.value =
                            Event("photo with ${response.body()!!.id} is edited successfully")

                    }
                })
        } else {


            for (i in 0 until list.size) {
                if (id.value == list[i].id) {
                    val retroPhoto =
                        RetroPhoto(id.value!!, title.value!!, url.value!!, thumUrl.value!!)

                    list[i] = retroPhoto
                    albumList.value = list
                    Log.e("qmrID", "${albumList.value!![0].title} ")

                    Paper.book().write("list", list as List<RetroPhoto>)

                }
            }


        }

    }

    private fun deletePhoto() {
        if (ToolsUtil.isNetworkConnected(MyApplication.context)) {

            RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
                .deletePhoto(id.value!!)
                .enqueue(object : Callback<ResponseBody> {


                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("qmrFail", t.message!!)
                        toastMsg.value = Event("Failure")

                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        toastMsg.value = Event("photo with ${id.value} is deleted successfully")
                    }


                })
        } else {
            if(id.value == item.value!!.id)
                list.remove(item.value!!)
                albumList.value = list
        }


    }

    fun dataDetails(dataDetails: RetroPhoto): MutableLiveData<RetroPhoto> {

        data.value = dataDetails

        return data
    }

}