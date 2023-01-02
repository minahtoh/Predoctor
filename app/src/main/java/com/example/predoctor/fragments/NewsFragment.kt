package com.example.predoctor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.predoctor.R
import com.example.predoctor.adapters.HomeRecycler
import com.example.predoctor.adapters.NewsRecycler
import com.example.predoctor.databinding.FragmentNewsBinding
import com.example.predoctor.viewmodel.LoadingState
import com.example.predoctor.viewmodel.PredoctorViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {
    private lateinit var binding : FragmentNewsBinding
    private val theViewModel: PredoctorViewModel by activityViewModels()
    private lateinit var newsRecycler: NewsRecycler
    private lateinit var shimmer:ShimmerFrameLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(layoutInflater,container,false)

        theViewModel.getNews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        theViewModel.loadingStateNews.observe(viewLifecycleOwner){
            when(it){
                LoadingState.LOADING -> showShimmer()
                LoadingState.SUCCESSFUL -> setupRecyclerView()
                LoadingState.ERROR -> showError()
            }
        }

    }

    private fun showError() {
        shimmer = binding.shimmerFrame
        shimmer.startShimmer()
        Snackbar.make(requireView(),"Error retrieving news, Try Again!",Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry"){
                theViewModel.getNews()
            }.show()
    }

    private fun showShimmer() {
        shimmer = binding.shimmerFrame
        shimmer.startShimmer()
    }

    private fun setupRecyclerView(){
        shimmer = binding.shimmerFrame
        shimmer.apply {
            stopShimmer()
            visibility = View.INVISIBLE
        }

        newsRecycler = NewsRecycler()
        binding.newsRecyclerView.apply {
            adapter = newsRecycler
            layoutManager = LinearLayoutManager(activity)
            visibility = View.VISIBLE
            theViewModel.newsData.observe(viewLifecycleOwner){
                (adapter as NewsRecycler).submitList(it)
            }

        }
        newsRecycler.setOnItemClickListener {
            val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(it)
            findNavController().navigate(action)
        }
    }


}