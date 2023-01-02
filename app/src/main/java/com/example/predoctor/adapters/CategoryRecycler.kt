package com.example.predoctor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.predoctor.databinding.CategoryDisplayCardBinding
import com.example.predoctor.models.predictionresponse.Data

class CategoryRecycler:ListAdapter<Data,CategoryRecycler.CategoryViewHolder>(DiffCallback) {
    inner class CategoryViewHolder(var binding: CategoryDisplayCardBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(data: Data){
                    val homeTeamOdds = data.odds.HomeW.toString()
                    val awayTeamOdds = data.odds.AwayW.toString()

                    binding.apply {
                        categoryAwayTeamNameTv.text = data.away_team
                        categoryHomeTeamNameTv.text = data.home_team
                        competitionNameTv.text = data.competition_name
                        startTimeTv.text = data.start_date
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
       return CategoryViewHolder(CategoryDisplayCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val prediction = getItem(position)
        holder.bind(prediction)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(prediction) }
        }
    }

    private var onItemClickListener:((Data)->Unit)? = null
    fun setOnItemClickListener(listener:(Data) -> Unit){
        onItemClickListener = listener
    }

}