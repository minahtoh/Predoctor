package com.example.predoctor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.predoctor.databinding.HeadToHeadResultViewBinding
import com.example.predoctor.models.headtohead.Encounter


class HeadToHeadRecycler:ListAdapter<Encounter,HeadToHeadRecycler.HeadViewHolder>(DiffCallback) {
    inner class HeadViewHolder(private val binding: HeadToHeadResultViewBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(data: Encounter){
                binding.apply {
                    homeTeamNameTv.text = data.home_team
                    awayTeamNameTv.text = data.away_team
                    fulltimeScores.text = data.fulltime_result
                    firstHalfScores.text = data.first_half_result
                }
            }
        }

    companion object DiffCallback:DiffUtil.ItemCallback<Encounter>(){
        override fun areItemsTheSame(oldItem: Encounter, newItem: Encounter): Boolean {
            return oldItem.start_date == newItem.start_date
        }

        override fun areContentsTheSame(oldItem: Encounter, newItem: Encounter): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadViewHolder {
        return HeadViewHolder(HeadToHeadResultViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: HeadViewHolder, position: Int) {
        val abc = getItem(position)
        holder.bind(abc)
    }
}