package com.example.predoctor.repository

import android.app.Application
import com.example.predoctor.db.PredoctorDatabase

class BaseApplication: Application() {
    val database : PredoctorDatabase by lazy { PredoctorDatabase.getDatabase(this) }
}