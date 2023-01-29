package com.example.paging.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paging.R
import com.example.paging.models.TvShow

class MoviePagingAdapter: PagingDataAdapter<TvShow, MoviePagingAdapter.MovieViewHolder>(COMPARATOR) {

    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val movieName = itemView.findViewById<TextView>(R.id.name)
    }

    companion object{
        private val COMPARATOR = object: DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null){
            holder.movieName.text = item.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item_layout,parent,false)
        return MovieViewHolder(view)
    }
}