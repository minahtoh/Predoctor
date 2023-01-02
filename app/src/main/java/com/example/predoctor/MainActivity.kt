package com.example.predoctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.predoctor.databinding.ActivityMainBinding
import com.example.predoctor.db.PredoctorDatabase
import com.example.predoctor.repository.PredoctorRepository
import com.example.predoctor.viewmodel.PredoctorViewModel
import com.example.predoctor.viewmodel.PredoctorViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PredoctorViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       val navHostFragment = supportFragmentManager.findFragmentById(R.id.predoctorContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        val predoctorRepository = PredoctorRepository(PredoctorDatabase.getDatabase(this))
        val predoctorProvider = PredoctorViewModelFactory(predoctorRepository)
        viewModel = ViewModelProvider(this, predoctorProvider).get(PredoctorViewModel::class.java)

    }


    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(binding.root).navigateUp() || super.onSupportNavigateUp()
    }
}