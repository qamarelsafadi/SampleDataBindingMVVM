package net.qamar.sampledatabindingmvvm.apiNetworking.interfaceAPI

import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface GetDataService {
    @GET("photos")
    suspend fun getAllPhotos(
        @Query("_start") start:Int,
        @Query("_limit") limit:Int

    ): Response<List<RetroPhoto>>


    @FormUrlEncoded
    @POST("photos/")
     fun addPhoto(
        @Field("albumId") albumId: Int,
        @Field("title") title: String,
        @Field("url") url: String,
        @Field("thumbnailUrl") thumbnailUrl: String
    ): Call<RetroPhoto>

}
