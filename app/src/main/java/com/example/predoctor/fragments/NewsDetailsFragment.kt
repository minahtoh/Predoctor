package com.example.predoctor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.predoctor.databinding.FragmentNewsDetailsBinding
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 * Use the [NewsDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDetailsFragment : Fragment() {
    lateinit var binding:FragmentNewsDetailsBinding
    private val args: NewsDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailsBinding.inflate(inflater,container,false)
        binding.newsWebView.apply {
            webViewClient = WebViewClient()
            loadUrl(args.news.news.link!!)
        }
        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

}