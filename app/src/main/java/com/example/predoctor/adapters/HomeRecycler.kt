package com.example.predoctor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.predoctor.databinding.MatchDisplayCardBinding
import com.example.predoctor.models.predictionresponse.Data
import com.example.predoctor.models.predictionresponse.formattedTime

class HomeRecycler:ListAdapter<Data,HomeRecycler.HomeViewHolder>(DiffCallback) {
    inner class HomeViewHolder(var binding: MatchDisplayCardBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(data: Data){
                    binding.apply {
                        homeTeamNameTv.text = data.home_team
                        awayTeamNameTv.text = data.away_team
                        federationNameTv.text = data.competition_cluster
                        competitionNameTv.text = data.competition_name
                        startTimeTv.text = data.formattedTime()
                        matchDrawOdds.text = data.odds.Draw.toString()
                    }
                }
            }

    companion object DiffCallback: DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecycler.HomeViewHolder {
        return HomeViewHolder(MatchDisplayCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: HomeRecycler.HomeViewHolder, position: Int) {
        val abc = getItem(position)
        holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(abc) }
        }
        holder.bind(abc)
    }

    private var onItemClickListener:((Data)->Unit)? = null
    fun setOnItemClickListener(listener:(Data) -> Unit){
        onItemClickListener = listener
    }
}