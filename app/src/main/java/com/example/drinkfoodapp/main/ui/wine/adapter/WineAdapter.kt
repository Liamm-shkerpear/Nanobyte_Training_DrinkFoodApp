package com.example.drinkfoodapp.main.ui.wine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.databinding.ItemWineBinding
import com.example.drinkfoodapp.main.data.domain.entities.WineItem


class WineAdapter(
    private val onItemClick: (WineItem) -> Unit,
    private val onAddClick: (WineItem) -> Unit
) : ListAdapter<WineItem, WineAdapter.WineViewHolder>(WineDiffCallback()) {
    inner class WineViewHolder(val binding: ItemWineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WineItem) {
            binding.tvWine.text = item.wine
            binding.tvWinery.text = item.winery
            Glide.with(binding.root.context)
                .load(item.image)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.ivWineThumb)

            if (item.isSaved) {
                binding.btnAdd.setImageResource(R.drawable.ic_minus_wine)
            } else {
                binding.btnAdd.setImageResource(R.drawable.ic_add_wine)
            }

            binding.root.setOnClickListener { onItemClick(item) }
            binding.btnAdd.setOnClickListener { onAddClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WineViewHolder {
        val binding = ItemWineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WineDiffCallback : DiffUtil.ItemCallback<WineItem>() {
        override fun areItemsTheSame(oldItem: WineItem, newItem: WineItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: WineItem, newItem: WineItem): Boolean {
            return oldItem == newItem
        }
    }
}
