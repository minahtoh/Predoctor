package com.example.predoctor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.predoctor.R
import com.example.predoctor.adapters.ListRecycler
import com.example.predoctor.databinding.FragmentListBinding
import com.example.predoctor.models.predictionresponse.Odds
import com.example.predoctor.viewmodel.PredoctorViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.math.RoundingMode
import java.text.DecimalFormat


class ListFragment: Fragment() {
    private lateinit var binding: FragmentListBinding
    private val theViewModel : PredoctorViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listRv.apply {
            adapter = ListRecycler()
            layoutManager = LinearLayoutManager(activity)
            theViewModel.getPredictionsList().observe(viewLifecycleOwner){
                (adapter as ListRecycler).submitList(it)

        }
    }
//        Display Total Odds for selected fixtures
        theViewModel.apply {
           getTotalOdds().observe(viewLifecycleOwner){ oddsList ->
               if (oddsList.isNotEmpty()){
                val totalOdds=oddsList.reduce{acc: Double?, d: Double? -> acc!! * d!! }
                val totalOddsRounded = roundOffDecimal(totalOdds!!)
                   binding.totalOddsText.text = totalOddsRounded.toString()
               }
               else{
                   val text = 0
                   binding.totalOddsText.text = text.toString()
               }
            }
            getPredictionCount().observe(viewLifecycleOwner){
                binding.totalSelectionsText.text = it.toString()
            }
        }

            // Implement slide to delete feature on list
        val touchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean { return true }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val prediction = (binding.listRv.adapter as ListRecycler).getPredicted(position)
                theViewModel.deletePrediction(prediction)
                Snackbar.make(view,"Prediction successfully deleted",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        theViewModel.savePrediction(prediction)
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(touchHelperCallback).apply {
            attachToRecyclerView(binding.listRv) }

//        Implement Delete all inside list feature
        theViewModel.getTotalOdds().observe(viewLifecycleOwner){
            val oddsList = it
            binding.clearListText.setOnClickListener {
                if (oddsList.isNotEmpty()) {
                    showConfirmationDialog()
                }else{
                    Snackbar.make(view,"List is already empty!",Snackbar.LENGTH_SHORT).show()
                }
            }
            binding.deleteIcon.setOnClickListener {
                if (oddsList.isNotEmpty()) {
                    showConfirmationDialog()
                }else{
                    Snackbar.make(view,"List is already empty!",Snackbar.LENGTH_SHORT).show()
                }
            }

        }

    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Attention")
            .setMessage("Delete all predictions?(This cannot be undone)")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ ->
                theViewModel.clearList()
                Snackbar.make(requireView(),"Prediction List Cleared!", Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }

}