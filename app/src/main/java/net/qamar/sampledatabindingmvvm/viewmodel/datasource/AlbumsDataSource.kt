package net.qamar.sampledatabindingmvvm.viewmodel.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import io.paperdb.Paper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import net.qamar.sampledatabindingmvvm.apinetworking.RetrofitClientInstance
import net.qamar.sampledatabindingmvvm.apinetworking.interfaceAPI.GetDataService
import net.qamar.sampledatabindingmvvm.model.RetroPhoto

class AlbumsDataSource(private val scope: CoroutineScope) :
    PageKeyedDataSource<String, RetroPhoto>() {
    private val apiService =
        RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, RetroPhoto>
    ) {
        scope.launch {
            try {

                val response = apiService.getAllPhotos(START, LIMIT)
                when {
                    response.isSuccessful -> {

                        val listing = response.body()

                        START = 0

                        START = (START + 1) * LIMIT
                        val key = START
                        Paper.book().write("list", listing)

                        callback.onResult(listing!!, null, key.toString())
                        Log.e("qmrSize","${    Paper.book().read<PagedList<RetroPhoto>>("list").size}")

                    }
                }


            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }

        }

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RetroPhoto>) {
        scope.launch {
            try {
                val response = apiService.getAllPhotos(params.key.toInt(), LIMIT)
                when {
                    response.isSuccessful -> {
                        val listing = response.body()

                        val key: Int = if (response.body()!!.size < params.key.toInt()) {
                            params.key.toInt() + 1 * LIMIT
                        } else {
                            params.key.toInt() + 1 * LIMIT
                        }
                        Log.e("qmrKey", key.toString())
                        Paper.book().write("list", listing)

                        callback.onResult(listing!!, key.toString())

                    }
                }

            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, RetroPhoto>
    ) {
        scope.launch {
            try {
                val response = apiService.getAllPhotos(params.key.toInt(), LIMIT)
                when {
                    response.isSuccessful -> {
                        val listing = response.body()

                        val key: Int = if (response.body()!!.size < params.key.toInt()) {
                            params.key.toInt() - 1 * LIMIT
                        } else {
                            params.key.toInt() - 1 * LIMIT
                        }
                        Paper.book().write("list", listing)


                        callback.onResult(listing!!, key.toString())

                    }
                }

            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

    companion object {

        //the size of a page that we want
        var LIMIT = 10

        //we will start from the first page which is 0
        var START = 0

    }
}