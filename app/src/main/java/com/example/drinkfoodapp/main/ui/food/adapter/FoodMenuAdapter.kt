package com.example.drinkfoodapp.main.ui.food.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.databinding.ItemMenuBinding
import com.example.drinkfoodapp.main.data.domain.entities.MenuItem

class FoodMenuAdapter(
    private val onItemClick: (MenuItem) -> Unit,
    private val onEditClick: (MenuItem) -> Unit,
    private val onDeleteClick: (MenuItem) -> Unit,
    private val onFavoriteClick: (MenuItem) -> Unit
) : ListAdapter<MenuItem, FoodMenuAdapter.FoodViewHolder>(FoodDiffCallback()) {

    inner class FoodViewHolder(val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MenuItem) {
            binding.ivThumb.setImageResource(item.imageResId)
            binding.tvName.text = item.name
            binding.tvItemPrice.text =
                binding.root.context.getString(R.string.item_price, item.price)

            updateFavoriteState(item.isFavorite)

            binding.root.setOnClickListener { onItemClick(item) }
            binding.btnEdit.setOnClickListener { onEditClick(item) }
            binding.btnDelete.setOnClickListener { onDeleteClick(item) }
            binding.btnFavorite.setOnClickListener { onFavoriteClick(item) }
        }

        fun updateFavoriteState(isFavorite: Boolean) {
            val icon = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            val color = if (isFavorite) Color.RED else Color.GRAY

            binding.btnFavorite.setImageResource(icon)
            binding.btnFavorite.setColorFilter(color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int, payloads: List<Any?>) {
        if (payloads.isEmpty()) {
            holder.bind(getItem(position))
        } else {
            val payload = payloads[0]
            if (payload == true) {
                holder.bind(getItem(position))
            }
        }
    }

    class FoodDiffCallback : DiffUtil.ItemCallback<MenuItem>() {
        override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem.isFavorite != newItem.isFavorite
                    || oldItem.name != newItem.name
                    || oldItem.price != newItem.price
                    || oldItem.description != newItem.description
                    || oldItem.category != newItem.category
        }
    }
}
