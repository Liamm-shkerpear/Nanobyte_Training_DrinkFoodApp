package com.example.drinkfoodapp.main.ui.wine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkfoodapp.databinding.ItemViewAllBinding


class ViewSearchAdapter(private val onSearchClick: () -> Unit) :
    RecyclerView.Adapter<ViewSearchAdapter.ViewSearchViewHolder>() {
    inner class ViewSearchViewHolder(val binding: ItemViewAllBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.tvHeaderTitle.setOnClickListener { onSearchClick() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewSearchViewHolder {
        val binding = ItemViewAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewSearchViewHolder, position: Int) {}
    override fun getItemCount(): Int = 1
}
