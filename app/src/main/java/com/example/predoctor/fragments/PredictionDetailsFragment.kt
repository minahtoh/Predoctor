package com.example.predoctor.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.predoctor.adapters.HeadToHeadRecycler
import com.example.predoctor.databinding.FragmentPredictionDetailsBinding
import com.example.predoctor.models.predoctorresults.Prediction
import com.example.predoctor.viewmodel.LoadingState
import com.example.predoctor.viewmodel.PredoctorViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import java.lang.reflect.Array.get


class PredictionDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPredictionDetailsBinding
    private val theViewModel: PredoctorViewModel by activityViewModels()
    private val args: PredictionDetailsFragmentArgs by navArgs()
    private lateinit var shimmer: ShimmerFrameLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPredictionDetailsBinding.inflate(inflater, container, false)
        val matchId = args.data.id
        theViewModel.getHeadtoHead(matchId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataReceived = args.data

        //Helper function to get odds for prediction string
        fun getPredoctorOdds(): Double? {
            val odds = dataReceived.odds
            val abc = mapOf(
                "1" to odds.HomeW,
                "2" to odds.AwayW,
                "1X" to odds.HomeWD,
                "X2" to odds.AwayWD,
                "12" to odds.AnybodyW,
                "X" to odds.Draw
            )
            val predoctorSays = dataReceived.prediction
            return abc[predoctorSays]
        }
        // Odds for predicted
        val predoctorOdds = getPredoctorOdds()

        //Save prediction to local database
        fun savePrediction() {
            val predictionValue = args.data.prediction
            val homeName = args.data.home_team
            val awayName = args.data.away_team
            val compName = args.data.competition_name
            val predictionOdds = getPredoctorOdds()
            val date = args.data.start_date
            val prediction = Prediction(
                away_team = awayName,
                home_team = homeName,
                prediction = predictionValue,
                competition_name = compName,
                odds = predictionOdds,
                start_date = date
            )
            theViewModel.savePrediction(prediction)
        }

        //Display prediction and match data
        binding.apply {
            savePredictionBtn.setOnClickListener {
                savePrediction()
                Snackbar.make(view, "Prediction added to your Lists!", Snackbar.LENGTH_SHORT).show()
            }
            homeTeamNameTv.text = dataReceived.home_team
            awayTeamNameTv.text = dataReceived.away_team
            nameOfLeagueTv.text = dataReceived.competition_name
            matchDayTv.text = dataReceived.competition_cluster
            homeTeamOdds.text = dataReceived.odds.HomeW.toString()
            awayTeamOdds.text = dataReceived.odds.AwayW.toString()
            matchDrawOdds.text = dataReceived.odds.Draw.toString()
            predoctorSaysTv.append(dataReceived.prediction)
            predoctorOddsTv.append(predoctorOdds.toString())

        }
        //Show Head to Head Data
        theViewModel.loadingStateH2H.observe(viewLifecycleOwner) {
            when (it) {
                LoadingState.LOADING -> applyLoadingScreen()
                LoadingState.ERROR -> showErrorScreen()
                LoadingState.SUCCESSFUL -> displayH2HData()
                null -> showErrorScreen()
            }
        }
        //back button
        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun applyLoadingScreen() {
        shimmer = binding.shimmerLayout
        shimmer.startShimmer()
        shimmer.visibility = View.VISIBLE
    }

    private fun showErrorScreen() {
        view?.let {
            Snackbar.make(it, "Error loading Head to Head data", Snackbar.LENGTH_INDEFINITE)
                .setAction(
                    "Retry!"
                ) { theViewModel.getHeadtoHead(args.data.id) }.show()
        }
    }

    private fun displayH2HData() {
        shimmer = binding.shimmerLayout
        shimmer.stopShimmer()
        shimmer.visibility = View.INVISIBLE
        binding.apply {
            headToHeadRv.apply {
                adapter = HeadToHeadRecycler()
                layoutManager = LinearLayoutManager(activity)
                visibility = View.VISIBLE
                theViewModel.headToHeadData.observe(viewLifecycleOwner) {
                    (adapter as HeadToHeadRecycler).submitList(it?.data?.encounters)
                }
            }
        }
        theViewModel.headToHeadData.observe(viewLifecycleOwner) { response ->
            val goalStats = response?.data?.stats

            binding.apply {
                if (goalStats!=null){
                    last10CleanSheetsHomeText.text = goalStats.home_team.clean_sheet.toString()
                    last10CleanSheetsAwayText.text = goalStats.away_team.clean_sheet.toString()
                    last10GoalsConcededHomeText.text = goalStats.home_team.goals_conceived.toString()
                    last10GoalsConcededAwayText.text = goalStats.away_team.goals_conceived.toString()
                    last10GoalsScoredHomeText.text = goalStats.home_team.goals_scored.toString()
                    last10GoalsScoredAwayText.text = goalStats.away_team.goals_scored.toString()
                }
            }
        }

    }

    override fun onDestroy() {
        theViewModel.headToHeadData.postValue(null)
        super.onDestroy()
    }

}
