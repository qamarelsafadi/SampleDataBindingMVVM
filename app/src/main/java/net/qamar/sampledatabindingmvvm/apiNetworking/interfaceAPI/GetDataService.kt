
package net.qamar.sampledatabindingmvvm.apiNetworking.interfaceAPI

import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


interface GetDataService {
    @GET("photos")
    suspend fun getAllPhotos(): Response<List<RetroPhoto>>

}
