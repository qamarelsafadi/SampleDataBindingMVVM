
package net.qamar.sampledatabindingmvvm.apiNetworking

import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClientInstance {

    private var retrofit: Retrofit? = null

    val BASE_URL = "https://jsonplaceholder.typicode.com/"
    val dispatcher = Dispatcher()
    val loggingInterceptor = HttpLoggingInterceptor()
    init {
        dispatcher.maxRequests = 100
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


    }
    var client = OkHttpClient.Builder()
        .connectTimeout(0, TimeUnit.SECONDS)
        .writeTimeout(0, TimeUnit.SECONDS)
        .readTimeout(0, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .dispatcher(dispatcher).build()


    var okClient = OkHttpClient.Builder()
        .connectTimeout(0, TimeUnit.SECONDS)
        .readTimeout(0, TimeUnit.SECONDS)
        .writeTimeout(0, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(newRequest)
        }
        .dispatcher(dispatcher).build()


    var gson = GsonBuilder()
        .setLenient()
        .create()!!
    val retrofitInstance: Retrofit?
        get() {


            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
            }
            return retrofit
        }


}