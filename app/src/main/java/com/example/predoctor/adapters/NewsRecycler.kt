package com.example.predoctor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.predoctor.databinding.NewsListViewBinding
import com.example.predoctor.models.newsresponse.NewsResponseItem

class NewsRecycler:ListAdapter<NewsResponseItem,NewsRecycler.ViewHolder>(DiffCallback) {
    inner class ViewHolder(val binding: NewsListViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(news:NewsResponseItem){
            binding.apply {
                newsTitleText.text = news.news.title
                newsSourceText.text = news.news.link
                Glide.with(itemView).load(news.news.image).into(newsImage)
            }
        }
    }
    companion object DiffCallback:DiffUtil.ItemCallback<NewsResponseItem>(){
        override fun areItemsTheSame(oldItem: NewsResponseItem, newItem: NewsResponseItem): Boolean {
            return oldItem.news == newItem.news
        }

        override fun areContentsTheSame(oldItem: NewsResponseItem, newItem: NewsResponseItem): Boolean {
            return oldItem.news.link == newItem.news.link
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRecycler.ViewHolder {
        return ViewHolder(NewsListViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsRecycler.ViewHolder, position: Int) {
        val abc = getItem(position)
        holder.bind(abc)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(abc) }
        }
    }

    private var onItemClickListener: ((NewsResponseItem) -> Unit)? = null
    fun setOnItemClickListener(listener:((NewsResponseItem) -> Unit)){
        onItemClickListener = listener
    }
}