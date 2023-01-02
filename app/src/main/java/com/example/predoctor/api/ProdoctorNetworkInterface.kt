package com.example.predoctor.api

import com.example.predoctor.models.PerformanceResponse
import com.example.predoctor.models.headtohead.HeadToHeadResponse
import com.example.predoctor.models.predictionresponse.PredictionResponse
import com.example.predoctor.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProdoctorNetworkInterface {
    @GET("api/v2/predictions")
    suspend fun getPredictions(
    @Query("rapidapi-key")
        apikey:String = API_KEY
    ): PredictionResponse

    @GET("api/v2/performance-stats")
    suspend fun getPerformance(
        @Query("rapidapi-key")
        apikey:String = API_KEY
    ): PerformanceResponse

    @GET("api/v2/head-to-head/{Id}")
    suspend fun getHeadtoHead(
        @Path("Id")
        matchId: Int,
        @Query("rapidapi-key")
        apikey: String = API_KEY
    ): HeadToHeadResponse

}