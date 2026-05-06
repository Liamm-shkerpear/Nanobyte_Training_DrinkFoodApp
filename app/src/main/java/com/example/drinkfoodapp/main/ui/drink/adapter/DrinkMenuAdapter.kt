package com.example.drinkfoodapp.main.ui.drink.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.databinding.ItemMenuBinding
import com.example.drinkfoodapp.main.data.domain.entities.MenuItem

class DrinkMenuAdapter(
    private val onItemClick: (MenuItem) -> Unit = {},
    private val onEditClick: (MenuItem) -> Unit = {},
    private val onDeleteClick: (MenuItem) -> Unit = {},
    private val onFavoriteClick: (MenuItem) -> Unit = {}
) : ListAdapter<MenuItem, DrinkMenuAdapter.DrinkViewHolder>(DrinkDiffCallback()) {

    inner class DrinkViewHolder(val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuItem) {
            binding.ivThumb.setImageResource(item.imageResId)
            binding.tvName.text = item.name

            updateFavoriteState(item.isFavorite)

            binding.root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(bindingAdapterPosition))
                }
            }
            binding.btnEdit.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onEditClick(getItem(bindingAdapterPosition))
                }
            }
            binding.btnDelete.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onDeleteClick(getItem(bindingAdapterPosition))
                }
            }
            binding.btnFavorite.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onFavoriteClick(getItem(bindingAdapterPosition))
                }
            }
        }

        fun updateFavoriteState(isFavorite: Boolean) {
            val icon = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            val color = if (isFavorite) Color.RED else Color.GRAY

            binding.btnFavorite.setImageResource(icon)
            binding.btnFavorite.setColorFilter(color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.contains(PAYLOAD_FAVORITE)) {
            val item = getItem(position)
            holder.updateFavoriteState(item.isFavorite)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    class DrinkDiffCallback : DiffUtil.ItemCallback<MenuItem>() {
        override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem) = oldItem == newItem
        override fun getChangePayload(oldItem: MenuItem, newItem: MenuItem): Any? {
            if (oldItem.isFavorite != newItem.isFavorite) {
                return PAYLOAD_FAVORITE
            }
            return super.getChangePayload(oldItem, newItem)
        }
    }

    companion object {
        private const val PAYLOAD_FAVORITE = "PAYLOAD_FAVORITE"
    }
}
