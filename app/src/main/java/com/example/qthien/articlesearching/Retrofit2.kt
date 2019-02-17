package com.example.qthien.articlesearching

import com.facebook.stetho.okhttp3.StethoInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient



object Retrofit2 {
    private var retrofit : Retrofit? = null
    private val baseUrl = "https://api.nytimes.com/svc/search/v2/"

    val getRetrofit : GetData
        get(){
            if(retrofit == null){
                val client = OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(GetData::class.java)
        }
}