package com.example.drinkfoodapp.main.ui.wine.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkfoodapp.R


class ViewSearchAdapter(private val onSearchClick: () -> Unit) :
    RecyclerView.Adapter<ViewSearchAdapter.ViewSearchViewHolder>() {
    inner class ViewSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvHeader: TextView = view.findViewById(R.id.tvHeaderTitle)

        init {
            tvHeader.setOnClickListener { onSearchClick() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewSearchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_all, parent, false)
        return ViewSearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewSearchViewHolder, position: Int) {}
    override fun getItemCount(): Int = 1
}
