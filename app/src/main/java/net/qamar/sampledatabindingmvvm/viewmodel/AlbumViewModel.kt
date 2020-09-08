package net.qamar.sampledatabindingmvvm.viewmodel

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.repository.SampleRepository
import net.qamar.sampledatabindingmvvm.util.Event
import net.qamar.sampledatabindingmvvm.viewmodel.datasource.AlbumsDataSource

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class AlbumViewModel : ViewModel() {

    private val repository = SampleRepository()
    val name: LiveData<String>
    var tosatMsg: LiveData<Event<String>>
    var showProgress: LiveData<Boolean>
    lateinit var data: LiveData<RetroPhoto>

    lateinit var albumList: LiveData<ArrayList<RetroPhoto>>

    private var postsLiveData  :LiveData<PagedList<RetroPhoto>>

    init {
        name = repository.name
        tosatMsg = repository.toastMsg
        showProgress = repository.showProgress
        albumList = repository.albumList

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
        AlbumsDataSource(viewModelScope).invalidate()

            }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun showDialog(view:View){
        repository.showDialog(view)
        tosatMsg = repository.toastMsg

    }


    fun dataDetails(dataDetails:RetroPhoto){
        data = repository.dataDetails(dataDetails)
    }

}