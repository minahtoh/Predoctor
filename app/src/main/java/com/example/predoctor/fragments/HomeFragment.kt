package com.example.predoctor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.predoctor.R
import com.example.predoctor.adapters.HomeRecycler
import com.example.predoctor.databinding.FragmentHomeBinding
import com.example.predoctor.viewmodel.LoadingState
import com.example.predoctor.viewmodel.PredoctorViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar

class HomeFragment: Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private val theViewModel: PredoctorViewModel by activityViewModels()
    private lateinit var homeRecycler: HomeRecycler
    lateinit var shimmer:ShimmerFrameLayout
    private var timesTillRestart = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeRecycler = HomeRecycler()
        homeRecycler.setOnItemClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPredictionDetailsFragment(it)
            findNavController().navigate(action)
        }

        theViewModel.loadingStatePrediction.observe(viewLifecycleOwner){
            when(it){
                LoadingState.LOADING -> applyShimmer()
                LoadingState.SUCCESSFUL -> setupRecyclerView()
                LoadingState.ERROR -> loadErrorScreen()
            }
        }
        showPerformanceData()
        binding.viewNextCv.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }

    }

    private fun loadErrorScreen() {
       if (timesTillRestart <=3 ){
            timesTillRestart++
            Snackbar.make(requireView(),"Error Retrieving Predictions, Try Again!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry"
                ) {
                    theViewModel.getPredictions()
                }.show()
        }else{
            Snackbar.make(requireView(),"Network Unavailable",Snackbar.LENGTH_INDEFINITE)
                .setAction("Restart App"){
                //TODO "Add function to restart app"
                }.show()
        }
    }

    private fun applyShimmer() {
        shimmer = binding.shimmerLayout
        shimmer.startShimmer()
    }

    private fun setupRecyclerView(){
        shimmer = binding.shimmerLayout
        shimmer.stopShimmer()
        shimmer.visibility = View.INVISIBLE
        binding.apply {
            predictionsRv.apply {
                adapter = homeRecycler
                layoutManager = LinearLayoutManager(activity)
                visibility = View.VISIBLE
                theViewModel.predictionData.observe(viewLifecycleOwner){
                    (adapter as HomeRecycler).submitList(it.data)
                }
            }
        }
    }

    private fun showPerformanceData(){
        theViewModel.performanceData.observe(viewLifecycleOwner){
            val ydayPerformance = it.data.details.yesterday
            val sevenPerformance = it.data.details.last_7_days
            binding.apply {
                yesterdayWon.append(ydayPerformance.won.toString())
                yesterdayLost.append(ydayPerformance.lost.toString())
                yesterdayPostponed.append(ydayPerformance.postponed.toString())
                yesterdayTotal.append(ydayPerformance.total.toString())
                sevenDayWon.append(sevenPerformance.won.toString())
                sevenDayLost.append(sevenPerformance.lost.toString())
                sevenDayPostponed.append(sevenPerformance.postponed.toString())
                sevenDayTotal.append(sevenPerformance.total.toString())
            }
        }
    }
}