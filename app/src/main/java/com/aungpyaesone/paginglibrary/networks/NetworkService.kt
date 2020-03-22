package com.aungpyaesone.paginglibrary.networks

import com.aungpyaesone.paginglibrary.networks.responses.Response
import com.aungpyaesone.paginglibrary.utils.BASE_URL
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface NetworkService {

    @GET("everything?q=sports&apiKey=0eb7df2a952649ef88f56b6bbc617a65")
    fun getNews(@Query("page") page:Int, @Query("pageSize") pageSize:Int): Single<Response>

    companion object{
            fun getService(): NetworkService{
            val mOkHttpClient = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            // val headerInterceptor =

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build()

            return retrofit.create(NetworkService::class.java)
        }
    }

}