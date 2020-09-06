package net.qamar.sampledatabindingmvvm.viewmodel

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.repository.SampleRepository
import net.qamar.sampledatabindingmvvm.apiNetworking.Resource
import net.qamar.sampledatabindingmvvm.apiNetworking.RetrofitClientInstance
import net.qamar.sampledatabindingmvvm.apiNetworking.interfaceAPI.GetDataService
import net.qamar.sampledatabindingmvvm.util.Event
import net.qamar.sampledatabindingmvvm.util.MyApplication
import net.qamar.sampledatabindingmvvm.viewmodel.datasource.AlbumsDataSource
import java.util.concurrent.Executor

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class AlbumViewModel() : ViewModel() {

    private val repository = SampleRepository()
    val name: LiveData<String>
    var tosatMsg: LiveData<Event<String>>
    var showProgress: LiveData<Boolean>
    lateinit var data: LiveData<RetroPhoto>

    lateinit var albumList: LiveData<Resource<PagedList<RetroPhoto>>>
     var addPhoto: LiveData<Resource<RetroPhoto>>? = null

    var postsLiveData  :LiveData<PagedList<RetroPhoto>>

    init {
        name = repository.name
        tosatMsg = repository.tosatMsg
        showProgress = repository.showProgress
      //  albumList = repository.getAllAlbums()

        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData  = initializedPagedListBuilder(config).build()
    }

    fun getAlbums():LiveData<PagedList<RetroPhoto>> = postsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<String, RetroPhoto> {

        val dataSourceFactory = object : DataSource.Factory<String, RetroPhoto>() {
            override fun create(): DataSource<String, RetroPhoto> {
                return AlbumsDataSource(viewModelScope)
            }
        }
        return LivePagedListBuilder<String, RetroPhoto>(dataSourceFactory, config)
    }



    fun refresh() {
        Log.e("qmr", "refresh")
        showProgress = repository.showProgress
     //   albumList = repository.getAllAlbums()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun showDialog(view:View){
        repository.showDialog(view)
        tosatMsg = repository.tosatMsg

    }


    fun dataDetails(dataDetails:RetroPhoto){
        data = repository.dataDetails(dataDetails)
    }

}