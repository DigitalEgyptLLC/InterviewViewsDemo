package com.intercept.viewsdemo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intercept.viewsdemo.R

class HomeAdapter(
    private val onItemClicked: () -> Unit
) : ListAdapter<HomeUIModel, HomeAdapter.HomeViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClicked.invoke()
        }
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image_view)
        private val title: TextView = itemView.findViewById(R.id.title)

        fun bind(item: HomeUIModel) {
            Glide.with(imageView)
                .load(item.imageUrl)
                .into(imageView)
            title.text = item.titlePrefix + adapterPosition
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeUIModel>() {
            override fun areItemsTheSame(oldItem: HomeUIModel, newItem: HomeUIModel): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: HomeUIModel, newItem: HomeUIModel): Boolean {
                return false
            }
        }
    }
}
