package com.example.predoctor.repository

import com.example.predoctor.db.PredoctorDatabase
import com.example.predoctor.models.predictionresponse.Data
import com.example.predoctor.models.predoctorresults.Prediction

class PredoctorRepository(val db: PredoctorDatabase) {

    suspend fun savePrediction(prediction: Prediction){
        db.getDatabase().update(prediction)
    }

    suspend fun deletePrediction(prediction: Prediction){
        db.getDatabase().delete(prediction)
    }

    fun getPrediction() = db.getDatabase().getList()

    fun getPredictionCount() = db.getDatabase().getCount()

    fun getAllOdds() = db.getDatabase().getOdds()

    suspend fun deleteAll() = db.getDatabase().deleteAll()

}