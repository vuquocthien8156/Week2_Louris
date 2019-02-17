package com.example.qthien.articlesearching


import com.example.qthien.articlesearching.Modal.Article
import com.example.qthien.articlesearching.Modal.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GetData {
    @GET("articlesearch.json?api-key=hARaNLTfRuMEQO0mBFP3QS6CoOWus5Im")
        fun getDataSearch(@QueryMap query : Map<String , String>,
                          @Query("fq[]") news_desk: ArrayList<String>  ) : Call<Result>
}