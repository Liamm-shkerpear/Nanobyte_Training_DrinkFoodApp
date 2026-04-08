package com.example.drinkfoodapp.ui.view.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.drinkfoodapp.ui.view.fragment.ItemFragment

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            ItemFragment.newInstance(isDrink = true)
        } else {
            ItemFragment.newInstance(isDrink = false)
        }
    }
}