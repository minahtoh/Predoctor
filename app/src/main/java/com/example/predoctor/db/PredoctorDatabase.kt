package com.example.predoctor.db

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.predoctor.models.predictionresponse.Data
import com.example.predoctor.models.predoctorresults.Prediction

@Database(entities = [Prediction::class], version = 1, exportSchema = false)
abstract class PredoctorDatabase: RoomDatabase() {
    abstract fun getDatabase() : PredoctorDao

    companion object{
        @Volatile
        private var INSTANCE : PredoctorDatabase? = null

        fun getDatabase(context: Context): PredoctorDatabase{
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PredoctorDatabase::class.java,
                    "predoctor_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance

                return instance
            }
        }
    }
}