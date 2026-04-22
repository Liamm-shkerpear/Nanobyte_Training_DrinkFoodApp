package com.example.drinkfoodapp.main.ui.mainscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.databinding.ItemMenuBinding
import com.example.drinkfoodapp.main.models.DrinkItem
import com.example.drinkfoodapp.main.models.FoodItem


class MenuAdapter(
    private val onItemClick: (Any) -> Unit,
    private val onEditClick: (Any) -> Unit,
    private val onDeleteClick: (Any) -> Unit
) :
    ListAdapter<Any, MenuAdapter.MenuViewHolder>(MenuDiffCallback()) {
    private var currentSelectedId: Int = -1

    inner class MenuViewHolder(val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Any) {
            val itemId: Int
            val itemName: String
            val itemImage: Int

            when (item) {
                is DrinkItem -> {
                    itemId = item.id
                    itemName = item.name
                    itemImage = item.imageResId
                }
                is FoodItem -> {
                    itemId = item.id
                    itemName = item.name
                    itemImage = item.imageResId
                }
                else -> return
            }

            binding.ivThumb.setImageResource(itemImage)
            binding.tvName.text = itemName

            updateSelectionState(itemId)

            binding.root.setOnClickListener {
                val oldPos = currentList.indexOfFirst { item ->
                    when (item) {
                        is DrinkItem -> item.id == currentSelectedId
                        is FoodItem -> item.id == currentSelectedId
                        else -> false
                    }
                }
                currentSelectedId = itemId

                if (oldPos != -1) notifyItemChanged(oldPos)
                notifyItemChanged(bindingAdapterPosition)
                onItemClick(item)
            }

            binding.btnEdit.setOnClickListener { onEditClick(item) }
            binding.btnDelete.setOnClickListener { onDeleteClick(item) }
        }

        fun updateSelectionState(itemId: Int) {
            if (itemId == currentSelectedId) {
                binding.root.setBackgroundResource(R.drawable.bg_item_selected)
            } else {
                binding.root.setBackgroundResource(R.drawable.bg_item_normal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int, payloads: List<Any?>) {
        if (payloads.isNotEmpty()) {
            for (payload in payloads) {
                if (payload == PAYLOAD_SELECTION_CHANGED) {
                    val itemId = when (val currentItem = getItem(position)) {
                        is DrinkItem -> {
                            currentItem.id
                        }
                        is FoodItem -> {
                            currentItem.id
                        }
                        else -> {
                            return
                        }
                    }
                    holder.updateSelectionState(itemId)
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    class MenuDiffCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is DrinkItem && newItem is DrinkItem) {
                return oldItem.id == newItem.id
            }
            if (oldItem is FoodItem && newItem is FoodItem) {
                return oldItem.id == newItem.id
            }
            return false
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is DrinkItem && newItem is DrinkItem) {
                return oldItem == newItem
            }
            if (oldItem is FoodItem && newItem is FoodItem) {
                return oldItem == newItem
            }
            return false
        }
    }

    fun clearHighlight() {
        val oldPos = currentList.indexOfFirst { item ->
            when (item) {
                is DrinkItem -> item.id == currentSelectedId
                is FoodItem -> item.id == currentSelectedId
                else -> false
            }
        }
        currentSelectedId = -1
        if (oldPos != -1) notifyItemChanged(oldPos, PAYLOAD_SELECTION_CHANGED)
    }


    companion object {
        private const val PAYLOAD_SELECTION_CHANGED = "PAYLOAD_SELECTION_CHANGED"
    }
}


