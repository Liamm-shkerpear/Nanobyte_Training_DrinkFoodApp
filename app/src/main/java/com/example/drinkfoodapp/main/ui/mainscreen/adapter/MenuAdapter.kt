package com.example.drinkfoodapp.main.ui.mainscreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.databinding.ItemMenuBinding
import com.example.drinkfoodapp.main.model.MenuItem
import com.google.android.material.imageview.ShapeableImageView

class MenuAdapter(
    private var itemList: List<MenuItem>,
    private val onEditClick: (MenuItem) -> Unit,
    private val onDeleteClick: (MenuItem) -> Unit
) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    class MenuViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = itemList[position]
        holder.binding.ivThumb.setImageResource(item.imageResId)
        holder.binding.tvName.text = item.name
        holder.binding.btnEdit.setOnClickListener { onEditClick(item) }
        holder.binding.btnDelete.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateData(newList: List<MenuItem>) {
        itemList = newList
        notifyDataSetChanged()
    }
}
