package com.example.predoctor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.predoctor.databinding.ListViewBinding
import com.example.predoctor.models.predoctorresults.Prediction

class ListRecycler:ListAdapter<Prediction,ListRecycler.ViewHolder>(DiffCallback) {
   inner class ViewHolder(private val binding: ListViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(prediction: Prediction){
            binding.apply {
                awayTeam.text = prediction.away_team
                homeTeam.text = prediction.home_team
                predictedOdds.text = prediction.odds.toString()
                predictedOption.text = prediction.prediction
            }
        }

    }


    companion object DiffCallback:DiffUtil.ItemCallback<Prediction>(){
        override fun areItemsTheSame(oldItem: Prediction, newItem: Prediction): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Prediction, newItem: Prediction): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecycler.ViewHolder {
        return ViewHolder(ListViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ListRecycler.ViewHolder, position: Int) {
       val abc = getItem(position)
        holder.bind(abc)
    }

    fun getPredicted(position: Int):Prediction{
        return getItem(position)
    }
}