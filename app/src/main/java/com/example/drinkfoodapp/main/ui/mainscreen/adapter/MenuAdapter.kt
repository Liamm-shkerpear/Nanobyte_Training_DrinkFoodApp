package com.example.drinkfoodapp.main.ui.mainscreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.main.model.MenuItem
import com.google.android.material.imageview.ShapeableImageView

class MenuAdapter(private var itemList: List<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivThumb: ShapeableImageView = itemView.findViewById(R.id.ivThumb)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = itemList[position]
        holder.ivThumb.setImageResource(item.imageResId)
        holder.tvName.text = item.name
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateData(newList: List<MenuItem>) {
        itemList = newList
        notifyDataSetChanged()
    }
}
