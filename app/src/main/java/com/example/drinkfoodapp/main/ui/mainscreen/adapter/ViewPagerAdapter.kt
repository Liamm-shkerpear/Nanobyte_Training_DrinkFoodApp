package com.example.drinkfoodapp.main.ui.mainscreen.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.drinkfoodapp.main.ui.mainscreen.fragment.DrinkFragment
import com.example.drinkfoodapp.main.ui.mainscreen.fragment.FoodFragment


class ViewPagerAdapter(activity : AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DrinkFragment()
            1 -> FoodFragment()
            else -> DrinkFragment()
        }
    }
}
