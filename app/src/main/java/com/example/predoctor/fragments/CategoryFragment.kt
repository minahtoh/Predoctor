package com.example.predoctor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.predoctor.adapters.CategoryRecycler
import com.example.predoctor.databinding.FragmentCategoryBinding
import com.example.predoctor.viewmodel.LoadingState
import com.example.predoctor.viewmodel.PredoctorViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar

class CategoryFragment:Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private val theViewModel : PredoctorViewModel by activityViewModels()
    private val categoryRecycler = CategoryRecycler()
    private lateinit var shimmer: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryRecycler.setOnItemClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToPredictionDetailsFragment(it)
            findNavController().navigate(action)
        }
        theViewModel.loadingStatePrediction.observe(viewLifecycleOwner){
            when(it){
                LoadingState.LOADING -> applyLoadingScreen()
                LoadingState.SUCCESSFUL -> setupRecyclerView()
                LoadingState.ERROR -> applyErrorScreen()
            }
        }

    }

    private fun applyErrorScreen() {
        view?.let {
            Snackbar.make(it,"Error Retrieving Predictions, Try Again!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry"
                ) {
                    theViewModel.getPredictions()
                }.show()
        }
    }

    private fun setupRecyclerView() {
        shimmer = binding.shimmerLayout
        shimmer.stopShimmer()
        shimmer.visibility = View.INVISIBLE
        binding.categoryRv.apply {
            adapter = categoryRecycler
            layoutManager = LinearLayoutManager(activity)
            visibility = View.VISIBLE
            theViewModel.predictionData.observe(viewLifecycleOwner){
                (adapter as CategoryRecycler).submitList(it.data)
            }
        }
    }

    private fun applyLoadingScreen() {
        shimmer = binding.shimmerLayout
        shimmer.startShimmer()
    }
}