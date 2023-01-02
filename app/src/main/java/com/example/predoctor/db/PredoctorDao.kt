package com.example.predoctor.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.predoctor.models.predoctorresults.Prediction

@Dao
interface PredoctorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(prediction:Prediction)

    @Delete
    suspend fun delete(prediction: Prediction)

    @Query("Select * from Predictions Order by start_date Asc" )
    fun getList():LiveData<List<Prediction>>

    @Query("SELECT COUNT(id) FROM Predictions")
    fun getCount():LiveData<Int>

    @Query("SELECT odds FROM Predictions")
    fun getOdds():LiveData<List<Double?>>

    @Query("DELETE FROM Predictions")
     suspend fun deleteAll()
}