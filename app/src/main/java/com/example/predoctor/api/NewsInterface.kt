package com.example.predoctor.api

import com.example.predoctor.models.newsresponse.NewsResponse
import com.example.predoctor.utils.Constants.NEWS_API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {
    @GET("football/")
    suspend fun getAllnews(
        @Query("rapidapi-key")
        key:String = NEWS_API_KEY
    ):NewsResponse
}