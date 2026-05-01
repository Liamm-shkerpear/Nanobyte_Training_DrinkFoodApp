package com.example.drinkfoodapp.main.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.drinkfoodapp.R
import com.example.drinkfoodapp.databinding.ActivityMainBinding
import com.example.drinkfoodapp.main.ui.home.adapter.ViewPagerAdapter
import com.example.drinkfoodapp.main.utils.bottomsheet.AddItemBottomSheet


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar icons to dark
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        setUpView()
    }

    private fun setUpView() {
        binding.apply {
            viewPager.adapter = ViewPagerAdapter(this@MainActivity)

            bottomNav.setOnItemSelectedListener { item ->
                val position = when (item.itemId) {
                    R.id.nav_drink -> TAB_DRINK_POSITION
                    R.id.nav_food -> TAB_FOOD_POSITION
                    else -> return@setOnItemSelectedListener false
                }
                viewPager.setCurrentItem(position, true)
                true
            }

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val menuIndex = if (position == TAB_DRINK_POSITION) 0 else 2
                    bottomNav.menu[menuIndex].isChecked = true
                }
            })

            fabCreate.setOnClickListener {
                val isDrinkSelected = binding.viewPager.currentItem == TAB_DRINK_POSITION
                val bottomSheet = AddItemBottomSheet.newInstance(isDrinkSelected)
                bottomSheet.show(supportFragmentManager, "AddBottomSheet")
            }

            bottomNav.menu[1].isEnabled = false

        }
    }



    companion object {
        private const val TAB_DRINK_POSITION = 0
        private const val TAB_FOOD_POSITION = 2
    }

}
