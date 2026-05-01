package com.example.drinkfoodapp.main.ui.home.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.drinkfoodapp.main.ui.drink.DrinkFragment
import com.example.drinkfoodapp.main.ui.food.FoodFragment

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
